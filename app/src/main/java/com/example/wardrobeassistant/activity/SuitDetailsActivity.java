package com.example.wardrobeassistant.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.adapter.SuitViewPagerAdapter;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Suit;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class SuitDetailsActivity extends BaseActivity {
    private Suit suit;
    private QMUITopBarLayout topBar;
    private QMUIViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suit_details);
        suit = getIntent().getParcelableExtra("suit");
        suit.__setDaoSession(DbManager.getInstance().getSession());
        initView();
        initTopBar();
        initViewPager();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        viewPager = findViewById(R.id.viewPager);
    }

    private void initTopBar() {
        topBar.setTitle(suit.getSuitName());
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.addRightTextButton(suit.getSuitPreset() ? "取消预设" : "预设", R.id.suit_preset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QMUIDialog.MessageDialogBuilder(SuitDetailsActivity.this)
                        .setTitle("提示")
                        .setMessage(suit.getSuitPreset() ? "确定要取消【" + suit.getSuitName() + "】预设？" : "确定要将【" + suit.getSuitName() + "】设为预设？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                suit.setSuitPreset(!suit.getSuitPreset());
                                ((Button) topBar.findViewById(R.id.suit_preset)).setText(suit.getSuitPreset() ? "取消预设" : "预设");
                                suit.update();
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        });
    }


    private void initViewPager() {
        SuitViewPagerAdapter adapter = new SuitViewPagerAdapter(suit);
        viewPager.setAdapter(adapter);
    }
}
