package com.sticket.app.sticket.database;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Motionticon;
import com.sticket.app.sticket.database.entity.Sticon;
import com.sticket.app.sticket.database.entity.SticonAsset;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;
import com.sticket.app.sticket.util.BitmapUtils;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.Landmark;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

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
        Resources resources = context.getResources();

        Log.d(TAG, "size : " + database.assetDao().getAllassets().size());

        if (database.assetDao().getAllassets().size() == 0) {
            List<Asset> assets = new ArrayList<>();

            for (Field field : R.drawable.class.getFields()) {
                if (field.getName().startsWith("basic_")) {
                    Log.d(TAG, "field.getName() : " + field.getName());
                    try {
                        Bitmap bitmap = BitmapFactory.decodeResource(resources, field.getInt(null));
                        String name = field.getName();
                        String landmarkStr = "";

                        if (name.startsWith("basic_eye_")) {
                            landmarkStr = Landmark.EYE_LEFT.name();
                        } else if (name.startsWith("basic_ear_")) {
                            landmarkStr = Landmark.EAR_LEFT.name();
                        } else if (name.startsWith("basic_cheek_")) {
                            landmarkStr = Landmark.CHEEK_LEFT.name();
                        } else if (name.startsWith("basic_nose_")) {
                            landmarkStr = Landmark.NOSE.name();
                        } else if (name.startsWith("basic_mouth_")) {
                            landmarkStr = Landmark.MOUTH.name();
                        } else {
                            Log.e(TAG, "setLandmark error : " + name);
                        }
                        assets.add(createAssetEntity(bitmap, name, landmarkStr));
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

    private static Asset createAssetEntity(Bitmap bitmap, String name, String landmarkStr) {

        isSuccess &= FileUtil.saveBitmapToFile(bitmap, IMAGE_ASSET_DIRECTORY_PATH, name);

        Asset asset = new Asset();
        asset.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + name + ".png");

        for (Landmark landmark : Landmark.LANDMARKS) {
            if (landmarkStr.equals(landmark.name())) {
                asset.setLandmark(landmark);
            }
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

    public static void initPurchasedAssets(Context context) {
        if (ApiClient.getInstance().isLoggedIn()) {
            ApiClient.getInstance().getAssetService()
                    .getMyPurchaseAssets()
                    .enqueue(SimpleCallbackUtil.getSimpleCallback(assets -> {
                        final SticketDatabase db = SticketDatabase.getDatabase(context);
                        for (int i = 0; i < assets.size(); i++) {
                            SimpleAssetResponse asset = assets.get(i);
                            for (Asset assetEntity : db.assetDao().getAllassets()) {
                                if (asset.getImgUrl().equals(assetEntity.getImgUrl())) {
                                    assets.remove(asset);
                                    i--;
                                    break;
                                }
                            }
                        }
                        new Thread(() -> {

                            List<Asset> assetList = new ArrayList<>();
                            for (SimpleAssetResponse asset : assets) {
                                Bitmap bitmap = BitmapUtils.getBitmapFromURL(asset.getImgUrl());
                                Asset assetEntity = createAssetEntity(bitmap, asset.getId() + "_" + asset.getName(), asset.getLandmark());
                                assetEntity.setImgUrl(asset.getImgUrl());
                                assetList.add(assetEntity);
                            }
                            db.assetDao().insertAll(assetList);
                        }).start();
                    }));
        }
    }
}