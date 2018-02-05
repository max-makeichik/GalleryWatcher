package com.mm.gallerywatcher.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mm.gallerywatcher.R;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;
import com.mm.gallerywatcher.presentation.mvp.presenter.MainPresenter;
import com.mm.gallerywatcher.presentation.mvp.view.GalleryMvpView;
import com.mm.gallerywatcher.presentation.ui.adapter.GalleryImagesAdapter;
import com.mm.gallerywatcher.util.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Maksim Makeychik on 04.02.2018.
 */

public class MainActivity extends BaseActivity implements GalleryMvpView {

    private static final int PERMISSION_REQUEST_CODE_READ_STORAGE = 0;
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.load_images_btn)
    View loadImagesButton;
    @BindView(R.id.images_recycler_view)
    RecyclerView recyclerView;

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar(R.string.app_name);

        List<GalleryImage> images = getGalleryImages();
        List<GalleryImage> thumbnails = getGalleryThumbnails();
        List<GalleryImage> imagesWithoutThumbnails = new ArrayList<>();
        for (GalleryImage image : images) {
            if (!thumbnails.contains(image)) {
                imagesWithoutThumbnails.add(image);
            }
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new GalleryImagesAdapter(this, images));

        catchImageBroadcasts();
    }

    private void catchImageBroadcasts() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        intentFilter.addDataScheme("file");
        registerReceiver(scanListener, intentFilter);
    }

    @Override
    public void showLoading() {
        loadImagesButton.setEnabled(false);
        super.showLoading();
    }

    @SuppressLint("LogNotTimber")
    public List<GalleryImage> getGalleryImages() {
        int position = 0;
        Uri uri;
        Cursor cursor;
        int columnIndexData, columnIndexName;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            showError(R.string.error_getting_images);
        }

        columnIndexData = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
        columnIndexName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        List<GalleryImage> allImages = new ArrayList<>();
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData);
            GalleryImage galleryImage = new GalleryImage();
            galleryImage.setImagePath(absolutePathOfImage);
            galleryImage.setName(cursor.getString(columnIndexName));
            allImages.add(galleryImage);
            Log.d(TAG, "getGalleryImages: " + galleryImage);
        }
        cursor.close();
        return allImages;
    }

    @SuppressLint("LogNotTimber")
    public List<GalleryImage> getGalleryThumbnails() {
        int position = 0;
        Uri uri;
        Cursor cursor;
        int columnIndexData, columnIndexFolderName;

        String absolutePathOfImage = null;

        uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Thumbnails.DATA};

        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            showError(R.string.error_getting_images);
        }

        columnIndexData = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
        List<GalleryImage> allImages = new ArrayList<>();
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData);
            GalleryImage galleryImage = new GalleryImage();
            galleryImage.setImagePath(absolutePathOfImage);
            allImages.add(galleryImage);
            Log.d(TAG, "getGalleryImages: " + galleryImage);
        }
        cursor.close();
        return allImages;
    }

    /*
     * This listener gets called when the media scanner starts up or finishes, and
     * when the sd card is unmounted.
     */
    private BroadcastReceiver scanListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
        }
    };

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
    public void onDestroy() {
        unregisterReceiverSafe(scanListener);
        super.onDestroy();
    }

    private void unregisterReceiverSafe(BroadcastReceiver receiver) {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException ignored) {
        }
    }
}
