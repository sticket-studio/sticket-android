package com.sticket.app.sticket.database;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Motionticon;
import com.sticket.app.sticket.database.entity.Sticon;
import com.sticket.app.sticket.database.entity.SticonAsset;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.Landmark;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.sticket.app.sticket.util.FileUtil.IMAGE_ASSET_DIRECTORY_PATH;

public class InitBasicAssets {
    private static final String TAG = InitBasicAssets.class.getSimpleName();
    private static boolean isSuccess;

    public static void patchAssetIfNotExist(Context context) {
        isSuccess = true;
        SticketDatabase database = SticketDatabase.getDatabase(context);

        Log.d(TAG, "size : " + database.assetDao().getAllassets().size());

        if (database.assetDao().getAllassets().size() == 0) {
            List<Asset> assets = new ArrayList<>();

            for (Field field : R.drawable.class.getFields()) {
                if(field.getName().startsWith("basic_")) {
                    Log.d(TAG, "field.getName() : " + field.getName());
                    try {
                        assets.add(initBasicAsset(context, field.getInt(null), field.getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            database.assetDao().insertAll(assets);
        }

        if (!isSuccess) {
            Log.e(TAG, "Fail to save asset, Let's clear all tables ");
            database.clearAllTables();
        }
    }

    private static Asset initBasicAsset(Context context, int resourceId, String name) {
        Resources resources = context.getResources();

        Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);
        isSuccess &= FileUtil.saveBitmapToFile(bitmap, IMAGE_ASSET_DIRECTORY_PATH, name);

        Asset asset = new Asset();
        asset.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + name + ".png");
        if(name.startsWith("basic_eye_")){
            asset.setLandmark(Landmark.EYE_LEFT);
        } else if(name.startsWith("basic_ear_")){
            asset.setLandmark(Landmark.EAR_LEFT);
        } else if(name.startsWith("basic_cheek_")){
            asset.setLandmark(Landmark.CHEEK_LEFT);
        } else if(name.startsWith("basic_nose_")){
            asset.setLandmark(Landmark.NOSE);
        } else if(name.startsWith("basic_mouth_")){
            asset.setLandmark(Landmark.MOUTH);
        }else{
            Log.e(TAG, "setLandmark error : " + name);
        }

        return asset;
    }

    public static void printInfo(Context context) {
        SticketDatabase database = SticketDatabase.getDatabase(context);
        List<Asset> assetList = database.assetDao().getAllassets();
        List<SticonAsset> sticonAssetList = database.sticonAssetDao().getAllSticon_assets();
        List<Sticon> sticonList = database.sticonDao().getAllSticon();
        List<Motionticon> motionticonList = database.motionticonDao().getAllMotionticons();

        Log.d(TAG, "Asset Count : " + assetList.size());
        Log.d(TAG, "SticonAsset Count : " + sticonAssetList.size());
        Log.d(TAG, "Sticon Count : " + sticonList.size());
        Log.d(TAG, "Motionticon Count : " + motionticonList.size());
        Log.d(TAG, "===========================================================");

        for (Asset asset : assetList) {
            Log.d(TAG, "[Asset]");
            Log.d(TAG, "idx       local_url            img_url             offset_x  offset_y  ");
            Log.d(TAG, String.format("%-10d%-20s%-20s%-10f%-10f",
                    asset.getIdx(),
                    asset.getLocalUrl(),
                    asset.getImgUrl(),
                    asset.getOffset_x(),
                    asset.getOffset_y()));
        }
        Log.d(TAG, "===========================================================");

        for (final Sticon sticon : sticonList) {
            Log.d(TAG, "[Sticon]");
            Log.d(TAG, "idx       local_url           img_url             ");
            Log.d(TAG, String.format("%-10d%-20s%-20s",
                    sticon.getIdx(),
                    sticon.getLocal_url(),
                    sticon.getImg_url()));
            Log.d(TAG, "[SticonAsset]");
            for (final SticonAsset sticonAsset : sticonAssetList) {
                if (sticonAsset.getSticonIdx() == sticon.getIdx()) {
                    Log.d(TAG, "idx       sticonIdx assetIdx  offset_x  offset_y" +
                            "  isFlipped rotate    ");
                    Log.d(TAG, String.format("%-10d%-10d%-10d%-10f%-10f%-10d%-10d"
                            , sticonAsset.getIdx()
                            , sticonAsset.getSticonIdx()
                            , sticonAsset.getAssetIdx()
                            , sticonAsset.getOffsetX()
                            , sticonAsset.getOffsetY()
                            , sticonAsset.getFlip()
                            , sticonAsset.getRotate()));
                    Log.d(TAG, "-----------------------------------------------------------");
                }
            }
        }
        Log.d(TAG, "===========================================================");
    }
}
