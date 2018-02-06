package com.mm.gallerywatcher.presentation.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mm.gallerywatcher.R;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;
import com.mm.gallerywatcher.presentation.mvp.presenter.MainPresenter;
import com.mm.gallerywatcher.presentation.mvp.view.GalleryMvpView;
import com.mm.gallerywatcher.presentation.ui.adapter.GalleryImagesAdapter;
import com.mm.gallerywatcher.util.PermissionUtils;
import com.mm.gallerywatcher.util.view.GridSpacingItemDecoration;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mm.gallerywatcher.util.view.GridSpacingItemDecoration.TOP_SPACING_MULTIPLIER;

/**
 * Created by Maksim Makeychik on 04.02.2018.
 */

public class MainActivity extends BaseActivity implements GalleryMvpView {

    private static final int PERMISSION_REQUEST_CODE_READ_STORAGE = 0;
    private static final int SPAN_COUNT = 2;

    @BindView(R.id.load_images_btn)
    View loadImagesButton;
    @BindView(R.id.images_recycler_view)
    RecyclerView recyclerView;

    @InjectPresenter
    MainPresenter presenter;

    @BindDimen(R.dimen.gallery_spacing)
    int gallerySpacing;

    private GalleryImagesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar(R.string.app_name);

        initRecyclerView();
        registerGalleryImagesUpdates();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT, gallerySpacing, true, TOP_SPACING_MULTIPLIER));
        adapter = new GalleryImagesAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Register broadcast receiver to get gallery update broadcasts
     */
    private void registerGalleryImagesUpdates() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        intentFilter.addDataScheme("file");
        registerReceiver(galleryScanReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.updateImages();
    }

    @Override
    public void showLoading() {
        loadImagesButton.setEnabled(false);
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        loadImagesButton.setEnabled(true);
        super.hideLoading();
    }

    @OnClick(R.id.load_images_btn)
    void onLoadImagesClick() {
        if (PermissionUtils.hasStorageReadPermissions(this)) {
            presenter.getImages();
        } else {
            PermissionUtils.requestStorageReadPermissions(this, PERMISSION_REQUEST_CODE_READ_STORAGE);
        }
    }

    @Override
    public void clearImages() {
        adapter.clear();
    }

    @Override
    public void addImage(GalleryImage image) {
        loadImagesButton.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.addItem(image);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_READ_STORAGE:
                if (PermissionUtils.hasStorageReadPermissions(this)) {
                    presenter.getImages();
                } else {
                    showError(R.string.error_permission_read_storage);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiverSafe(galleryScanReceiver);
        super.onDestroy();
    }

    /**
     * This receiver gets called when the media scanner starts up or finishes, and
     * when the sd card is unmounted.
     */
    private BroadcastReceiver galleryScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.updateImages();
        }
    };

    private void unregisterReceiverSafe(BroadcastReceiver receiver) {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException ignored) {
        }
    }
}
