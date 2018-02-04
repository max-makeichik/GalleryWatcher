package com.mm.gallerywatcher.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mm.gallerywatcher.R;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;
import com.mm.gallerywatcher.presentation.mvp.presenter.MainPresenter;
import com.mm.gallerywatcher.presentation.mvp.view.GalleryMvpView;
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
    }

    @Override
    public void showLoading() {
        loadImagesButton.setEnabled(false);
        super.showLoading();
    }

    @SuppressLint("LogNotTimber")
    public List<GalleryImage> getGalleyImages() {
        int position = 0;
        Uri uri;
        Cursor cursor;
        int columnIndexData, columnIndexFolderName;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
        if(cursor == null) {
            showError(R.string.error_getting_images);
        }

        columnIndexData = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
        columnIndexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        List<GalleryImage> allImages = new ArrayList<>();
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(columnIndexFolderName));

            for (int i = 0; i < allImages.size(); i++) {
                if (allImages.get(i).getStr_folder().equals(cursor.getString(columnIndexFolderName))) {
                    boolean_folder = true;
                    position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }
            if (boolean_folder) {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(allImages.get(position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                allImages.get(position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                GalleryImage obj_model = new GalleryImage();
                obj_model.setStr_folder(cursor.getString(columnIndexFolderName));
                obj_model.setAl_imagepath(al_path);

                allImages.add(obj_model);
            }
        }
        for (int i = 0; i < allImages.size(); i++) {
            Log.e("FOLDER", allImages.get(i).getStr_folder());
            for (int j = 0; j < allImages.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", allImages.get(i).getAl_imagepath().get(j));
            }
        }
        cursor.close();
        return allImages;
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
}
