package com.sticket.app.sticket.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();
    public static String EXTERNAL_STORAGE_DIRECTORY;
    public static String APPLICATION_DIRECTORY_PATH;

    public static String DATA_DIRECTORY_PATH;
    public static String THUMBNAIL_DIR_PATH;
    public static String THUMBNAIL_ASSET_DIRECTORY_PATH;
    public static String THUMBNAIL_STICON_DIRECTORY_PATH;
    public static String THUMBNAIL_MOTIONTICON_DIRECTORY_PATH;
    public static String IMAGE_DIRECTORY_PATH;
    public static String IMAGE_ASSET_DIRECTORY_PATH;
    public static String ALBUM_DIRECTORY_PATH;

    public static void buildPaths() {
        EXTERNAL_STORAGE_DIRECTORY = Preference.getInstance().getString(Preference.PREFERENCE_NAME_SAVE_LOCATION);
        APPLICATION_DIRECTORY_PATH = EXTERNAL_STORAGE_DIRECTORY + "/sticket";
        DATA_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/data";
        THUMBNAIL_DIR_PATH = APPLICATION_DIRECTORY_PATH + "/data/thumbnail";
        THUMBNAIL_ASSET_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/thumbnail/asset";
        THUMBNAIL_STICON_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/thumbnail/sticon";
        THUMBNAIL_MOTIONTICON_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/thumbnail/motionticon";
        IMAGE_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/image";
        IMAGE_ASSET_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/image/asset";
        ALBUM_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/sticket";
    }

    public static void structDirectories() {
        buildPaths();

        File dataDir = new File(DATA_DIRECTORY_PATH);
        File thumbnailDir = new File(THUMBNAIL_DIR_PATH);
        File assetThumbnailDir = new File(THUMBNAIL_ASSET_DIRECTORY_PATH);
        File sticonThumbnailDir = new File(THUMBNAIL_STICON_DIRECTORY_PATH);
        File motionticonThumbnailDir = new File(THUMBNAIL_MOTIONTICON_DIRECTORY_PATH);
        File imageDir = new File(IMAGE_DIRECTORY_PATH);
        File assetImageDir = new File(IMAGE_ASSET_DIRECTORY_PATH);
        File albumDir = new File(ALBUM_DIRECTORY_PATH);

        List<File> dirs = Arrays.asList(dataDir, thumbnailDir, assetThumbnailDir,
                sticonThumbnailDir, motionticonThumbnailDir, imageDir, assetImageDir, albumDir);

        for (File dir : dirs) {
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    Log.e(TAG, "폴더 생성 SUCCESS");
                } else {
                    Log.e(TAG, "폴더 생성 FAIL");
                }
            } else {
                Log.e(TAG, "폴더 이미 있음");
            }
        }
    }

    public static List<File> getFilesAt(String dirPath) {
        File targetDir = new File(dirPath);

        if (!targetDir.isDirectory()) {
            throw new RuntimeException("This is not a directory path: " + dirPath);
        }

        return Arrays.asList(targetDir.listFiles());
    }

    public static void saveBitmapToFile(Bitmap bitmap, String destDirPath, String destFileName) {
        File file = new File(destDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        File fileCacheItem = new File(destDirPath + "/" + destFileName + ".png");

        try (OutputStream out = new FileOutputStream(fileCacheItem)) {
            fileCacheItem.createNewFile();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    if (FileUtil.copyFile(sourceFilePath, FileUtil.IMAGE_ASSET_DIRECTORY_PATH, destFileName) {
        Log.e(TAG, "copyFile success");
    } else {
        Log.e(TAG, "copyFile fail");
    }
    */
    public static boolean copyFile(String sourceFilePath, String destDirPath, String destFileName) {
        boolean isSuccess = false;

        String ext = getFileExtension(sourceFilePath);

        File destDir = new File(destDirPath);
        String destFilePath = destDirPath + File.separatorChar + destFileName + "." + ext;

        if (!destDir.exists()) {
            if (!destDir.mkdirs()) {
                return false;
            }
        }

        try (FileInputStream fis = new FileInputStream(sourceFilePath);
             FileOutputStream fos = new FileOutputStream(destFilePath);
             FileChannel in = fis.getChannel();
             FileChannel out = fos.getChannel()) {
            in.transferTo(0, in.size(), out);
            isSuccess = true;
        } catch (IOException io) {
            io.printStackTrace();
        }

        return isSuccess;
    }

    /*
    if (FileUtil.copyFile(this, uri, FileUtil.IMAGE_ASSET_DIRECTORY_PATH, destFileName) {
        Log.e(TAG, "copyFile success");
    } else {
        Log.e(TAG, "copyFile fail");
    }
    */
    public static boolean copyFile(Context context, Uri sourceUri,
                                   String destDirPath, String destFileName) {
        return copyFile(getRealPathFromURI(context, sourceUri),
                destDirPath, destFileName);
    }

    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
    }

    public static String getFileExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        if (contentUri.getPath() != null && !contentUri.getPath().contains(":")) {
            return contentUri.getPath();
        }
        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = {MediaStore.Files.FileColumns.DATA};
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        try (Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null)) {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex);
            }
        }
        return null;
    }


}
