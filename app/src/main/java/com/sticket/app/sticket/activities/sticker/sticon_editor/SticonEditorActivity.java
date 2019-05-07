package com.sticket.app.sticket.activities.sticker.sticon_editor;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import androidx.annotation.Nullable;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sticker.StickerGridFragment;
import com.sticket.app.sticket.util.ViewPagerAdapter;

public class SticonEditorActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable @android.support.annotation.Nullable Bundle savedInstanceState, @Nullable @android.support.annotation.Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_sticon_editor);

        initViews();
    }

    private void initViews(){
        ViewPager assetViewPager = findViewById(R.id.viewpager_sticon_editor);
        setupViewPager(assetViewPager);

        TabLayout assetTabLayout = findViewById(R.id.tab_sticon_editor);
        assetTabLayout.setupWithViewPager(assetViewPager);

    }

    public void btnFinished(View view){

    }

    public void btnGoToTakePicture(View view) {
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment
        adapter.addFrag(new StickerGridFragment(), "눈");
        adapter.addFrag(new StickerGridFragment(), "코");
        adapter.addFrag(new StickerGridFragment(), "입");
        adapter.addFrag(new StickerGridFragment(), "볼");
        adapter.addFrag(new StickerGridFragment(), "귀걸이");
        viewPager.setAdapter(adapter);
    }
}
