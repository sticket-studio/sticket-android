package com.sticket.app.sticket.activities.sticker.sticon_editor;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sticker.StickerGridFragment;
import com.sticket.app.sticket.util.ViewPagerAdapter;

public class SticonEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
