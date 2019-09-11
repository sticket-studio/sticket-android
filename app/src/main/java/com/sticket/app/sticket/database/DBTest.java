package com.sticket.app.sticket.database;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Motionticon;
import com.sticket.app.sticket.database.entity.Sticon;
import com.sticket.app.sticket.database.entity.SticonAsset;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.Landmark;

import java.util.List;

import static com.sticket.app.sticket.util.FileUtil.IMAGE_ASSET_DIRECTORY_PATH;

public class DBTest {
    private static final String TAG = DBTest.class.getSimpleName();

    public static void patchAssetIfNotExist(Context context) {
        SticketDatabase database = SticketDatabase.getDatabase(context);
        Resources resources = context.getResources();

        database.clearAllTables();
        Log.e(TAG, "size : " + database.assetDao().getAllassets().size() );

        if (database.assetDao().getAllassets().size() == 0) {
            FileUtil.saveBitmapToFile(BitmapFactory.decodeResource(resources, R.drawable.heart),
                    IMAGE_ASSET_DIRECTORY_PATH, "heart");
            FileUtil.saveBitmapToFile(BitmapFactory.decodeResource(resources, R.drawable.img_logo1),
                    IMAGE_ASSET_DIRECTORY_PATH, "logo1");
            FileUtil.saveBitmapToFile(BitmapFactory.decodeResource(resources, R.drawable.img_logo2),
                    IMAGE_ASSET_DIRECTORY_PATH, "logo2");
            FileUtil.saveBitmapToFile(BitmapFactory.decodeResource(resources, R.drawable.nose),
                    IMAGE_ASSET_DIRECTORY_PATH, "nose");
            FileUtil.saveBitmapToFile(BitmapFactory.decodeResource(resources, R.drawable.mouth_bottom),
                    IMAGE_ASSET_DIRECTORY_PATH, "mouth_bottom");
            FileUtil.saveBitmapToFile(BitmapFactory.decodeResource(resources, R.drawable.cheek),
                    IMAGE_ASSET_DIRECTORY_PATH, "cheek");
            FileUtil.saveBitmapToFile(BitmapFactory.decodeResource(resources, R.drawable.nose2),
                    IMAGE_ASSET_DIRECTORY_PATH, "nose2");

            Asset asset1 = new Asset();
            asset1.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + "heart.png");
            asset1.setLandmark(Landmark.EYE_LEFT);
            Asset asset1_1 = new Asset();
            asset1_1.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + "logo1.png");
            asset1_1.setLandmark(Landmark.EYE_LEFT);
            Asset asset1_2 = new Asset();
            asset1_2.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + "logo2.png");
            asset1_2.setLandmark(Landmark.EYE_LEFT);
            Asset asset3 = new Asset();
            asset3.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + "nose.png");
            asset3.setLandmark(Landmark.NOSE);
            Asset asset4 = new Asset();
            asset4.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + "mouth_bottom.png");
            asset4.setLandmark(Landmark.MOUTH);
            Asset asset5 = new Asset();
            asset5.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + "cheek.png");
            asset5.setLandmark(Landmark.CHEEK_LEFT);
            Asset asset6 = new Asset();
            asset6.setLocalUrl(IMAGE_ASSET_DIRECTORY_PATH + "/" + "nose2.png");
            asset6.setLandmark(Landmark.NOSE);

            database.assetDao().insert(asset1, asset1_1, asset1_2, asset3, asset4, asset5, asset6);

        }
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
