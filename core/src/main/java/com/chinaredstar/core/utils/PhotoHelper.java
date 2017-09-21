package com.chinaredstar.core.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.chinaredstar.core.base.BaseApplication;
import com.chinaredstar.core.cache.ss.SharedPreferencesHelper;
import com.chinaredstar.core.ucrop.Crop;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static com.chinaredstar.core.constant.RC.RC_ACTION_IMAGE_CAPTURE;
import static com.chinaredstar.core.constant.RC.RC_ACTION_PICK;

/**
 * Created by hairui.xiang on 2017/9/6.
 */

public class PhotoHelper {
    private static final String KEY_TAKE_PHOTOS_RESULT = "take_photos_result";

    public interface OnPhotoGetListener {
        void onGetPhotoPath(String path);
    }

    public interface IUCrop {
        boolean onStartUCrop(@NonNull Uri source, @NonNull Uri destination);
    }

    public static Crop.Options getDefaultUCropOptions() {
        Crop.Options options = new Crop.Options();
        //设置裁剪图片的保存格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);//  JPEG,PNG,WEBP;
        //设置裁剪图片的图片质量
        options.setCompressionQuality(80);//[0-100]
        //设置你想要指定的可操作的手势
//        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
//        options.setMaxScaleMultiplier(5);
//        options.setImageToCropBoundsAnimDuration(666);
//        options.setCircleDimmedLayer(true);
//        options.setDimmedLayerColor(Color.DKGRAY);
//        options.setShowCropFrame(false);
//        options.setCropGridStrokeWidth(1);
//        options.setCropGridColor(Color.WHITE);
//        options.setCropGridColumnCount(3);
//        options.setCropGridRowCount(3);
//        options.setHideBottomControls(true);
        return options;
    }

    /**
     * @param ucrop 如果为null返回原图
     * @param l     图片地址回传
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data, IUCrop ucrop, OnPhotoGetListener l) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_ACTION_PICK://data.getData().toString()
                   /* if ("content".equals(data.getScheme())) {
                    } else if ("file".equals(data.getScheme())) {
                    }*/
                    if (null != ucrop) {
                        if (!ucrop.onStartUCrop(data.getData(), Uri.fromFile(getOutputPhoto(true)))) {
                            if (null != l) {// 异常返回原图
                                l.onGetPhotoPath(queryPathByUri(data.getData()));
                            }
                        }
                    } else {
                        if (null != l) {
                            l.onGetPhotoPath(queryPathByUri(data.getData()));
                        }
                    }
                    break;
                case RC_ACTION_IMAGE_CAPTURE://SharedPreferencesHelper.getObj("take_photos_result", String.class)
                    if (null != ucrop) {
                        if (!ucrop.onStartUCrop(Uri.fromFile(new File(getPhotosPath())), Uri.fromFile(getOutputPhoto(true)))) {
                            if (null != l) {// 异常返回原图
                                l.onGetPhotoPath(queryPathByUri(Uri.fromFile(new File(getPhotosPath()))));
                            }
                        }
                    } else {
                        if (null != l) {
                            l.onGetPhotoPath(queryPathByUri(Uri.fromFile(new File(getPhotosPath()))));
                        }
                    }
                    break;
                case Crop.REQUEST_CROP:
                    if (null != l) {
                        l.onGetPhotoPath(queryPathByUri(Crop.getOutput(data)));
                    }
                    break;
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            final Throwable cropError = Crop.getError(data);
            cropError.printStackTrace();
        }
    }

    /**
     * 打开相机
     */
    public static void onTakePhotos(Activity activity) {
        Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
        File file = getOutputPhoto(false);
        if (null == file) {
            LogUtil.e("File can not be created!");
            return;
        }
        LogUtil.i("TakePhotos file: " + file.toString());
        Uri imgUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //        Uri imgUri = FileProvider.getUriForFile(activity, PathUtil.FILEPROVIDER_AUTHORITIES_VALUE, file);
            imgUri = FileProviderUtil.getUriForFile(file);
        } else {
            imgUri = Uri.fromFile(file);
        }
        if (null != imgUri) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            activity.startActivityForResult(intent, RC_ACTION_IMAGE_CAPTURE);
        } else {

        }
    }

    /**
     * 打开相册
     */
    public static void onOpenAlbum(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), RC_ACTION_PICK);

       /* Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
        activity.startActivityForResult(intent, RC_ACTION_PICK);*/
    }


    private static File getOutputPhoto(boolean isCrop) {
        File photosDir = new File(PathUtil.getAppCacheDir(), PathUtil.IMAGE_DIR);
        if (!photosDir.exists()) {
            photosDir.mkdirs();
        }
        String timestamp = TimeUtil.format("yyyy_MM_dd_HH_mm_ss");
        File photo;
        String photoPath;
        if (isCrop) {
            photoPath = photosDir.getPath() + File.separator + "IMG_" + timestamp + "_CROP" + ".jpg";
            photo = new File(photoPath);
        } else {
            photoPath = photosDir.getPath() + File.separator + "IMG_" + timestamp + ".jpg";
            photo = new File(photoPath);
        }
        recordPhotosPath(photo);
        return photo;
    }

    private static void recordPhotosPath(File photo) {
        SharedPreferencesHelper.putObj(KEY_TAKE_PHOTOS_RESULT, photo.toString());
    }

    public static String getPhotosPath() {
        return SharedPreferencesHelper.getObj(KEY_TAKE_PHOTOS_RESULT, String.class);
    }

    public static String queryPathByUri(Uri uri) {
        String path = null;
        if ("content".equals(uri.getScheme())) {
            ContentResolver mCr = BaseApplication.getInstance().getContentResolver();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                    DocumentsContract.isDocumentUri(BaseApplication.getInstance(), uri)) {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String[] column = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
                String sel = MediaStore.Images.Media._ID + "= ?";
                Cursor mCursor = mCr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id},
                        null);
                if (null != mCursor) {
                    if (mCursor.moveToFirst()) {
                        int _idColumn = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                        int image_column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                        int _id = mCursor.getInt(_idColumn);
                        String img_path = mCursor.getString(image_column_index);
                        path = img_path;
                    }
                    mCursor.close();
                }

            } else {
                String[] proj = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
                Cursor mCursor = mCr.query(uri, proj, null, null, null);
                if (null != mCursor) {
                    if (mCursor.moveToFirst()) {
                        int _idColumn = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                        int image_column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                        int _id = mCursor.getInt(_idColumn);
                        String img_path = mCursor.getString(image_column_index);
                        path = img_path;
                    }
                    mCursor.close();
                }
            }
        } else if ("file".equals(uri.getScheme())) {
            path = uri.getPath();
        }
        LogUtil.i("query path by uri: " + path);
        return path;
    }
}
