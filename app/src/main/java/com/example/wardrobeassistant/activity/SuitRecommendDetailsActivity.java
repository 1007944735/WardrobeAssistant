package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.SuitDao;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.db.entity.Suit;
import com.example.wardrobeassistant.socketmanage.MySocket;
import com.example.wardrobeassistant.socketmanage.MySocketCallBack;
import com.example.wardrobeassistant.util.CheckSocketConnect;
import com.example.wardrobeassistant.util.StringUtils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuitRecommendDetailsActivity extends BaseActivity{

    private QMUITopBarLayout topBar;
    private QMUIRadiusImageView2 ivOvercoat;
    private QMUIRadiusImageView2 ivOuterwear;
    private QMUIRadiusImageView2 ivTrousers;
    private QMUIRadiusImageView2 ivShoes;

    private LinearLayout llSuitTag;
    private TextView tvSuitTag;
    private QMUIButton btnSubmit;
    private Suit suit;

    private String messageSocket;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int type = msg.what;
            switch (type){
                case -1:
                    break;
                case 0:
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    };

    private MySocketCallBack callBack = new MySocketCallBack() {
        @Override
        public void socketConnectTimeOut() {
            messageSocket = "超时";
            changeView(-1);
        }

        @Override
        public void socketConnectError(String errorMsg) {
            messageSocket = errorMsg;
            changeView(0);
        }

        @Override
        public void socketConnectSucceed() {
            messageSocket = "链接成功";
            changeView(1);
        }

        @Override
        public void sendMessageError(final String errorMsg, int type) {
            if (type == 0){
                messageSocket = "发送失败 = 数据包 = " + errorMsg;
            }else{
                messageSocket = "发送失败 = 心跳包 = " + errorMsg ;
            }
            changeView(0);
        }

        @Override
        public void sendMessageSucceed(int type) {
            if (type == 0){
                messageSocket = "发送成功 = 数据包";
            }else{
                messageSocket = "发送成功 = 心跳包" ;
            }
            changeView(0);
        }

        @Override
        public void receiveMessageSucceed(final String message) {
            messageSocket = "接收成功 = "+ message;
            changeView(0);
        }

        @Override
        public void receiveMessagError(final String message) {
            messageSocket = "接收失败 = "+ message;
            changeView(0);
        }

        @Override
        public void socketClose() {
            messageSocket = "链接关闭";
            changeView(-1);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_suit_details_recommend);
        MySocket.getInstall().registerListener(callBack);
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
        llSuitTag = findViewById(R.id.ll_suit_tag);
        tvSuitTag = findViewById(R.id.tv_suit_tag);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void initTopBar() {
        topBar.setTitle("套装推荐");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.addRightTextButton("", R.id.suit_preset)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new QMUIDialog.MessageDialogBuilder(SuitRecommendDetailsActivity.this)
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


    private void initListener() {
        ivOvercoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suit == null || suit.getSuitOvercoat() == null){
                    return;
                }
                Intent intent = new Intent(SuitRecommendDetailsActivity.this, ClothingDetailsActivity.class);
                intent.putExtra("clothing", suit.getSuitOvercoat());
                startActivity(intent);
            }
        });

        ivOuterwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suit == null || suit.getSuitOuterwear() == null){
                    return;
                }
                Intent intent = new Intent(SuitRecommendDetailsActivity.this, ClothingDetailsActivity.class);
                intent.putExtra("clothing", suit.getSuitOuterwear());
                startActivity(intent);
            }
        });

        ivTrousers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suit == null || suit.getSuitTrousers() == null){
                    return;
                }
                Intent intent = new Intent(SuitRecommendDetailsActivity.this, ClothingDetailsActivity.class);
                intent.putExtra("clothing", suit.getSuitTrousers());
                startActivity(intent);
            }
        });

        ivShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suit == null || suit.getSuitShoes() == null){
                    return;
                }
                Intent intent = new Intent(SuitRecommendDetailsActivity.this, ClothingDetailsActivity.class);
                intent.putExtra("clothing", suit.getSuitShoes());
                startActivity(intent);
            }
        });

        llSuitTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetList("套装标签", getResources().getStringArray(R.array.suitTag), new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        tvSuitTag.setText(tag);
                        String tagToDb = StringUtils.suitTagToDb(tag);
                        List<Suit> suitList = DbManager.getInstance().getSession().getSuitDao().queryBuilder().where(SuitDao.Properties.SuitTag.eq(tagToDb)).list();
                        if (suitList == null || suitList.size() <= 0){
                            topBar.setTitle("套装推荐");
                            ((Button) topBar.findViewById(R.id.suit_preset)).setVisibility(View.GONE);
                            Toast.makeText(SuitRecommendDetailsActivity.this,"当前标签下没有套装",Toast.LENGTH_SHORT).show();
                            Glide.with(SuitRecommendDetailsActivity.this)
                                    .load(R.drawable.bg_suit_overcoat_stroke)
                                    .into(ivOvercoat);
                            Glide.with(SuitRecommendDetailsActivity.this).load(R.drawable.bg_suit_outerwear_stroke).into(ivOuterwear);
                            Glide.with(SuitRecommendDetailsActivity.this).load(R.drawable.bg_suit_trousers_stroke).into(ivTrousers);
                            Glide.with(SuitRecommendDetailsActivity.this).load(R.drawable.bg_suit_shoes_stroke).into(ivShoes);
                            btnSubmit.setVisibility(View.GONE);
                            return;
                        }
                        btnSubmit.setVisibility(View.VISIBLE);
                        ((Button) topBar.findViewById(R.id.suit_preset)).setVisibility(View.VISIBLE);
                        Random random = new Random();
                        int posi = random.nextInt(suitList.size());
                        suit = suitList.get(posi);
                        btnSubmit.setText(suit.getIsTakeOut() ? "放入" : "取出");
                        Clothing Overcoat;
                        Clothing Outerwear;
                        Clothing Trousers;
                        Clothing Shoes;

                        Overcoat = suit.getSuitOvercoat();
                        Outerwear = suit.getSuitOuterwear();
                        Trousers = suit.getSuitTrousers();
                        Shoes = suit.getSuitShoes();

                        if (Overcoat == null){
                            Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的外套缺失，建议删除该套装",Toast.LENGTH_LONG).show();
                            btnSubmit.setVisibility(View.GONE);
                            Glide.with(SuitRecommendDetailsActivity.this).load(R.drawable.bg_suit_overcoat_stroke).into(ivOvercoat);
                        }else {
                            Glide.with(SuitRecommendDetailsActivity.this).load(suit.getSuitOvercoat().getClothingImageUrl()).into(ivOvercoat);
                            Overcoat.setClothingViewTime(System.currentTimeMillis());
                        }
                        if (Outerwear == null){
                            Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的上衣缺失，建议删除该套装",Toast.LENGTH_LONG).show();
                            btnSubmit.setVisibility(View.GONE);
                            Glide.with(SuitRecommendDetailsActivity.this).load(R.drawable.bg_suit_outerwear_stroke).into(ivOuterwear);
                        }else {
                            Glide.with(SuitRecommendDetailsActivity.this).load(suit.getSuitOuterwear().getClothingImageUrl()).into(ivOuterwear);
                            Outerwear.setClothingViewTime(System.currentTimeMillis());
                        }
                        if (Trousers == null){
                            Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的裤子缺失，建议删除该套装",Toast.LENGTH_LONG).show();
                            btnSubmit.setVisibility(View.GONE);
                            Glide.with(SuitRecommendDetailsActivity.this).load(R.drawable.bg_suit_trousers_stroke).into(ivTrousers);
                        }else {
                            Glide.with(SuitRecommendDetailsActivity.this).load(suit.getSuitTrousers().getClothingImageUrl()).into(ivTrousers);
                            Trousers.setClothingViewTime(System.currentTimeMillis());
                        }
                        if (Shoes == null){
                            Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的鞋子缺失，建议删除该套装",Toast.LENGTH_LONG).show();
                            btnSubmit.setVisibility(View.GONE);
                            Glide.with(SuitRecommendDetailsActivity.this).load(R.drawable.bg_suit_shoes_stroke).into(ivShoes);
                        }else {
                            Glide.with(SuitRecommendDetailsActivity.this).load(suit.getSuitShoes().getClothingImageUrl()).into(ivShoes);
                            Trousers.setClothingViewTime(System.currentTimeMillis());
                        }
                        suit.update();
                        topBarLoadRight();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExit = CheckSocketConnect.checkSocket(SuitRecommendDetailsActivity.this);
                if (isExit){
                    return;
                }
                boolean takeOut = suit.getIsTakeOut();
                suit.setIsTakeOut(!takeOut);
                boolean outerwearIsTake = suit.getSuitOuterwear().getIsTakeOut();
                boolean overcoatIsTake = suit.getSuitOvercoat().getIsTakeOut();
                boolean trousersIsTake = suit.getSuitTrousers().getIsTakeOut();
                boolean shoesIsTake =  suit.getSuitShoes().getIsTakeOut();
                //若果已经取出 就要放入
                if (takeOut){
                    if (!overcoatIsTake){
                        Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的上衣之前已单独放入",Toast.LENGTH_LONG).show();
                    }
                    if (!outerwearIsTake){
                        Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的外套之前已单独放入",Toast.LENGTH_LONG).show();
                    }
                    if (!trousersIsTake){
                        Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的裤子之前已单独放入",Toast.LENGTH_LONG).show();
                    }
                    if (!shoesIsTake){
                        Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的鞋子之前已单独放入",Toast.LENGTH_LONG).show();
                    }
                    suit.getSuitOvercoat().setIsTakeOut(false);
                    suit.getSuitOuterwear().setIsTakeOut(false);
                    suit.getSuitTrousers().setIsTakeOut(false);
                    suit.getSuitShoes().setIsTakeOut(false);

                }else{
                    if (overcoatIsTake){
                        Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的上衣之前已单独取出",Toast.LENGTH_LONG).show();
                    }
                    if (outerwearIsTake){
                        Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的外套之前已单独取出",Toast.LENGTH_LONG).show();
                    }
                    if (trousersIsTake){
                        Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的裤子之前已单独取出",Toast.LENGTH_LONG).show();
                    }
                    if (shoesIsTake){
                        Toast.makeText(SuitRecommendDetailsActivity.this,"该套装的鞋子之前已单独取出",Toast.LENGTH_LONG).show();
                    }
                    suit.getSuitOvercoat().setIsTakeOut(true);
                    suit.getSuitOuterwear().setIsTakeOut(true);
                    suit.getSuitTrousers().setIsTakeOut(true);
                    suit.getSuitShoes().setIsTakeOut(true);
                }

                MySocket.getInstall().socketSendMessage(suit.getSuitOvercoat().getClothingLocation());
                MySocket.getInstall().socketSendMessage(suit.getSuitOuterwear().getClothingLocation());
                MySocket.getInstall().socketSendMessage(suit.getSuitTrousers().getClothingLocation());
                MySocket.getInstall().socketSendMessage(suit.getSuitShoes().getClothingLocation());

                DbManager.getInstance().getSession().getSuitDao().update(suit);
                final QMUITipDialog tipDialog = new QMUITipDialog.Builder(SuitRecommendDetailsActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord(takeOut ? "放回成功" : "取出成功")
                        .create();
                tipDialog.show();
                btnSubmit.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tipDialog.dismiss();
                        setResult(-1);
                        finish();
                    }
                }, 1000);
            }
        });

    }

    private void showSimpleBottomSheetList(CharSequence title, String[] items, QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener listener) {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(this);
        builder.setGravityCenter(true)
                .setTitle(title)
                .setOnSheetItemClickListener(listener);
        for (String item : items) {
            builder.addItem(item);
        }
        builder.build().show();
    }

    private void topBarLoadRight() {
        topBar.setTitle(suit.getSuitName());
        ((Button) topBar.findViewById(R.id.suit_preset)).setText(suit.getSuitPreset() ? "取消预设" : "预设");
    }

    private void changeView(int type) {
        //type -1  关闭 0正常传输 1 成功
        Message msg = new Message();
        msg.what = type;
        mHandler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MySocket.getInstall().unRegisterListener(callBack);
    }

}
