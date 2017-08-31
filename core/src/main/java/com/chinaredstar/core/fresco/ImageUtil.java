package com.chinaredstar.core.fresco;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.chinaredstar.core.base.BaseApplication;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.DefaultExecutorSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hairui.xiang on 2017/8/2.
 */

public class ImageUtil {
    /**
     * 从内存缓存中移除指定图片的缓存
     */
    public static void evictFromMemoryCache(String originalUrl) {
        if (!TextUtils.isEmpty(originalUrl)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            Uri uri = Uri.parse(originalUrl);
            if (imagePipeline.isInBitmapMemoryCache(uri)) {
                imagePipeline.evictFromMemoryCache(uri);
            }
        }
    }

    /***
     *从磁盘缓存中移除指定图片的缓存
     * */
    public static void evictFromDiskCache(String originalUrl) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(originalUrl);
        // 下面的操作是异步的
        if (imagePipeline.isInDiskCacheSync(uri)) {
            imagePipeline.evictFromDiskCache(uri);
        }
    }

    /**
     * 清空磁盘缓存
     **/
    public static void clearDiskCaches() {
        Fresco.getImagePipeline().clearDiskCaches();
    }

    /**
     * 清空内存缓存
     **/
    public static void clearMemoryCaches() {
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    /**
     * 清空缓存（内存缓存 + 磁盘缓存）
     **/
    public static void clearCaches() {
        Fresco.getImagePipeline().clearCaches();
    }

    /**
     * 需要暂停网络请求时调用
     **/
    public static void pause() {
        Fresco.getImagePipeline().pause();
    }

    /**
     * 需要恢复网络请求时调用
     **/
    public static void resume() {
        Fresco.getImagePipeline().resume();
    }

    /**
     * 图片保存到相册
     **/
    public static void savePicToAlbum(final String url,  final String title, final String description) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, BaseApplication.getInstance());
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(Bitmap bitmap) {
                try {
                    String url = MediaStore.Images.Media.insertImage(BaseApplication.getInstance().getContentResolver(), bitmap, title, description);
                    if (url == null) {
                        return;
                    }
                    String filePath = getFilePathByContentResolver(BaseApplication.getInstance(), Uri.parse(url));
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(new File(filePath));
                    intent.setData(uri);
                    BaseApplication.getInstance().sendBroadcast(intent);
                    //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {

            }
        }, new DefaultExecutorSupplier(1).forBackgroundTasks());
    }

    private static String getFilePathByContentResolver(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        String filePath = null;
        if (null == c) {
            throw new IllegalArgumentException(
                    "Query on " + uri + " returns null result.");
        }
        try {
            if ((c.getCount() != 1) || !c.moveToFirst()) {
            } else {
                filePath = c.getString(c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            }
        } finally {
            c.close();
        }
        return filePath;
    }

    private static String savePicToDir(Bitmap bitmap, String picName, String cachePath) {
        File f = new File(cachePath, picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return f.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
