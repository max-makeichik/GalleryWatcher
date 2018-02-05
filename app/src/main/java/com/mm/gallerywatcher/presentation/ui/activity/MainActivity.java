package com.mm.gallerywatcher.presentation.ui.activity;

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
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT, gallerySpacing, true));
        adapter = new GalleryImagesAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        loadImagesButton.setEnabled(false);
        super.showLoading();
    }

    @OnClick(R.id.load_images_btn)
    void onLoadImagesClick() {
        if (PermissionUtils.hasStorageReadPermissions(this)) {
            presenter.loadImages();
        } else {
            PermissionUtils.requestStorageReadPermissions(this, PERMISSION_REQUEST_CODE_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_READ_STORAGE:
                if (PermissionUtils.hasStorageReadPermissions(this)) {
                    presenter.loadImages();
                } else {
                    showError(R.string.error_permission_read_storage);
                }
                break;
        }
    }

    @Override
    public void addImage(GalleryImage image) {
        loadImagesButton.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.addItem(image);
    }
}
