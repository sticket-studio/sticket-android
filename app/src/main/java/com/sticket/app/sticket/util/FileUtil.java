package com.sticket.app.sticket.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();
    private static final String EXTERNAL_STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String APPLICATION_DIRECTORY_PATH = EXTERNAL_STORAGE_DIRECTORY + "/sticket";

    public static final String DATA_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/data";
    public static final String THUMBNAIL_DIR_PATH = APPLICATION_DIRECTORY_PATH + "/data/thumbnail";
    public static final String THUMBNAIL_ASSET_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/thumbnail/asset";
    public static final String THUMBNAIL_STICON_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/thumbnail/sticon";
    public static final String THUMBNAIL_MOTIONTICON_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/thumbnail/motionticon";
    public static final String IMAGE_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/image";
    public static final String IMAGE_ASSET_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/cache/image/asset";
    public static final String ALBUM_DIRECTORY_PATH = APPLICATION_DIRECTORY_PATH + "/album";

    public static void structDirectories() {
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
}
