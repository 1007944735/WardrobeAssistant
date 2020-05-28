package com.example.wardrobeassistant.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.adapter.ClothingSelectedAdapter;
import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.db.entity.Suit;
import com.example.wardrobeassistant.util.StringUtils;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

public class ClothingSuitAddActivity extends BaseActivity {
    private QMUITopBarLayout topBar;
    private QMUIRadiusImageView2 ivOvercoat;
    private QMUIRadiusImageView2 ivOuterwear;
    private QMUIRadiusImageView2 ivTrousers;
    private QMUIRadiusImageView2 ivShoes;

    private Suit suit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_suit_add);
        suit = new Suit();
        initView();
        initTopBar();
        initListener();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        ivOvercoat = findViewById(R.id.iv_overcoat);
        ivOuterwear = findViewById(R.id.iv_outerwear);
        ivTrousers = findViewById(R.id.iv_trousers);
        ivShoes = findViewById(R.id.iv_shoes);
    }

    private void initTopBar() {
        topBar.setTitle("添加套装");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.addRightTextButton("完成", R.id.clothing_add_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditTextDialog();
            }
        });
    }


    private void initListener() {
        ivOvercoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Clothing> clothing = DbManager.getInstance().getSession().getClothingDao().queryBuilder().where(ClothingDao.Properties.ClothingType.eq(StringUtils.clothingTypeToDb("外套"))).list();
                showClothingSelectDialog("外套", clothing, new ClothingSelectedAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Clothing clothing) {
                        Glide.with(ClothingSuitAddActivity.this).load(clothing.getClothingImageUrl()).into(ivOvercoat);
                        suit.setSuitOvercoatId(clothing.getId());
                    }
                });
            }
        });
        ivOuterwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Clothing> clothing = DbManager.getInstance().getSession().getClothingDao().queryBuilder().where(ClothingDao.Properties.ClothingType.eq(StringUtils.clothingTypeToDb("上衣"))).list();
                showClothingSelectDialog("上衣", clothing, new ClothingSelectedAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Clothing clothing) {
                        Glide.with(ClothingSuitAddActivity.this).load(clothing.getClothingImageUrl()).into(ivOuterwear);
                        suit.setSuitOuterwearId(clothing.getId());
                    }
                });
            }
        });
        ivTrousers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Clothing> clothing = DbManager.getInstance().getSession().getClothingDao().queryBuilder().where(ClothingDao.Properties.ClothingType.eq(StringUtils.clothingTypeToDb("裤子"))).list();
                showClothingSelectDialog("裤子", clothing, new ClothingSelectedAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Clothing clothing) {
                        Glide.with(ClothingSuitAddActivity.this).load(clothing.getClothingImageUrl()).into(ivTrousers);
                        suit.setSuitTrousersId(clothing.getId());
                    }
                });
            }
        });

        ivShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Clothing> clothing = DbManager.getInstance().getSession().getClothingDao().queryBuilder().where(ClothingDao.Properties.ClothingType.eq(StringUtils.clothingTypeToDb("鞋子"))).list();
                showClothingSelectDialog("鞋子", clothing, new ClothingSelectedAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Clothing clothing) {
                        Glide.with(ClothingSuitAddActivity.this).load(clothing.getClothingImageUrl()).into(ivShoes);
                        suit.setSuitShoesId(clothing.getId());
                    }
                });
            }
        });
    }

    private void showClothingSelectDialog(String title, List<Clothing> clothing, ClothingSelectedAdapter.OnItemClickListener listener) {
        QMUIBottomSheet dialog = new QMUIBottomSheet(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_clothing_select, null);
        dialog.addContentView(view);
        dialog.show();
        TextView tvTitle = view.findViewById(R.id.tv_title);
        RecyclerView list = view.findViewById(R.id.clothing_list);
        tvTitle.setText(title);
        list.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        ClothingSelectedAdapter adapter = new ClothingSelectedAdapter(this, clothing);
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(listener);
    }

    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle("套装名称")
                .setPlaceholder("在此输入套装名称")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        String text = builder.getEditText().getText().toString();
                        if (text != null && text.length() > 0) {
                            suit.setSuitName(text);
                            suit.setSuitPreset(false);
                            suit.setSuitCreateTime(System.currentTimeMillis());
                            final long id = DbManager.getInstance().getSession().getSuitDao().insert(suit);

                            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(ClothingSuitAddActivity.this)
                                    .setIconType(id > 0 ? QMUITipDialog.Builder.ICON_TYPE_SUCCESS : QMUITipDialog.Builder.ICON_TYPE_FAIL)
                                    .setTipWord(id > 0 ? "添加成功" : "添加失败")
                                    .create();
                            tipDialog.show();
                            if (id > 0) {
                                dialog.dismiss();
                                topBar.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tipDialog.dismiss();
                                        setResult(-1);
                                        finish();
                                    }
                                }, 1000);
                            }
                        } else {
                            Toast.makeText(ClothingSuitAddActivity.this, "请填入昵称", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create().show();
    }
}
