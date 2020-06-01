package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.db.entity.Suit;
import com.example.wardrobeassistant.socketmanage.MySocket;
import com.example.wardrobeassistant.socketmanage.MySocketCallBack;
import com.example.wardrobeassistant.util.CheckSocketConnect;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class SuitDetailsLineActivity extends BaseActivity{

    private QMUITopBarLayout topBar;
    private QMUIRadiusImageView2 ivOvercoat;
    private QMUIRadiusImageView2 ivOuterwear;
    private QMUIRadiusImageView2 ivTrousers;
    private QMUIRadiusImageView2 ivShoes;
    private QMUIButton btnSubmit;
    private Suit suit;

    Clothing Overcoat;
    Clothing Outerwear;
    Clothing Trousers;
    Clothing Shoes;

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
        setContentView(R.layout.activity_clothing_suit_details_line);

        MySocket.getInstall().registerListener(callBack);

        suit = getIntent().getParcelableExtra("suit");
        suit.__setDaoSession(DbManager.getInstance().getSession());

        Overcoat = suit.getSuitOvercoat();
        Outerwear = suit.getSuitOuterwear();
        Trousers = suit.getSuitTrousers();
        Shoes = suit.getSuitShoes();
        initView();
        initTopBar();
        initListener();
        loadViews();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        ivOvercoat = findViewById(R.id.iv_overcoat);
        ivOuterwear = findViewById(R.id.iv_outerwear);
        ivTrousers = findViewById(R.id.iv_trousers);
        ivShoes = findViewById(R.id.iv_shoes);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setText(suit.getIsTakeOut() ? "放入" : "取出");
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
                new QMUIDialog.MessageDialogBuilder(SuitDetailsLineActivity.this)
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

                Intent intent = new Intent(SuitDetailsLineActivity.this, ClothingDetailsActivity.class);
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
                Intent intent = new Intent(SuitDetailsLineActivity.this, ClothingDetailsActivity.class);
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
                Intent intent = new Intent(SuitDetailsLineActivity.this, ClothingDetailsActivity.class);
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
                Intent intent = new Intent(SuitDetailsLineActivity.this, ClothingDetailsActivity.class);
                intent.putExtra("clothing", suit.getSuitShoes());
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExit = CheckSocketConnect.checkSocket(SuitDetailsLineActivity.this);
                if (isExit){
                    return;
                }
                boolean takeOut = suit.getIsTakeOut();
                suit.setIsTakeOut(!takeOut);
                boolean overcoatIsTake = suit.getSuitOvercoat().getIsTakeOut();
                boolean outerwearIsTake = suit.getSuitOuterwear().getIsTakeOut();
                boolean trousersIsTake = suit.getSuitTrousers().getIsTakeOut();
                boolean shoesIsTake =  suit.getSuitShoes().getIsTakeOut();
                //若果已经取出 就要放入
                if (takeOut){
                    if (!overcoatIsTake){
                        Toast.makeText(SuitDetailsLineActivity.this,"该套装的外套之前已单独放入",Toast.LENGTH_LONG).show();
                    }
                    if (!outerwearIsTake){
                        Toast.makeText(SuitDetailsLineActivity.this,"该套装的上衣之前已单独放入",Toast.LENGTH_LONG).show();
                    }
                    if (!trousersIsTake){
                        Toast.makeText(SuitDetailsLineActivity.this,"该套装的裤子之前已单独放入",Toast.LENGTH_LONG).show();
                    }
                    if (!shoesIsTake){
                        Toast.makeText(SuitDetailsLineActivity.this,"该套装的鞋子之前已单独放入",Toast.LENGTH_LONG).show();
                    }
                    suit.getSuitOvercoat().setIsTakeOut(false);
                    suit.getSuitOuterwear().setIsTakeOut(false);
                    suit.getSuitTrousers().setIsTakeOut(false);
                    suit.getSuitShoes().setIsTakeOut(false);

                }else{
                    if (overcoatIsTake){
                        Toast.makeText(SuitDetailsLineActivity.this,"该套装的外套之前已单独取出",Toast.LENGTH_LONG).show();
                    }
                    if (outerwearIsTake){
                        Toast.makeText(SuitDetailsLineActivity.this,"该套装的上衣之前已单独取出",Toast.LENGTH_LONG).show();
                    }
                    if (trousersIsTake){
                        Toast.makeText(SuitDetailsLineActivity.this,"该套装的裤子之前已单独取出",Toast.LENGTH_LONG).show();
                    }
                    if (shoesIsTake){
                        Toast.makeText(SuitDetailsLineActivity.this,"该套装的鞋子之前已单独取出",Toast.LENGTH_LONG).show();
                    }
                    suit.getSuitOvercoat().setIsTakeOut(true);
                    suit.getSuitOuterwear().setIsTakeOut(true);
                    suit.getSuitTrousers().setIsTakeOut(true);
                    suit.getSuitShoes().setIsTakeOut(true);
                }


                MySocket.getInstall().socketSendMessage(Overcoat.getClothingLocation());
                MySocket.getInstall().socketSendMessage(Outerwear.getClothingLocation());
                MySocket.getInstall().socketSendMessage(Trousers.getClothingLocation());
                MySocket.getInstall().socketSendMessage(Shoes.getClothingLocation());

                DbManager.getInstance().getSession().getSuitDao().update(suit);
                final QMUITipDialog tipDialog = new QMUITipDialog.Builder(SuitDetailsLineActivity.this)
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

    private void loadViews() {
        if (Overcoat == null){
            Toast.makeText(SuitDetailsLineActivity.this,"该套装的外套缺失，建议删除该套装",Toast.LENGTH_LONG).show();
            btnSubmit.setVisibility(View.GONE);
            Glide.with(SuitDetailsLineActivity.this).load(R.drawable.bg_suit_overcoat_stroke).into(ivOvercoat);
        }else {
            Glide.with(SuitDetailsLineActivity.this).load(suit.getSuitOvercoat().getClothingImageUrl()).into(ivOvercoat);
            Overcoat.setClothingViewTime(System.currentTimeMillis());
        }
        if (Outerwear == null){
            Toast.makeText(SuitDetailsLineActivity.this,"该套装的上衣缺失，建议删除该套装",Toast.LENGTH_LONG).show();
            btnSubmit.setVisibility(View.GONE);
            Glide.with(SuitDetailsLineActivity.this).load(R.drawable.bg_suit_outerwear_stroke).into(ivOuterwear);
        }else {
            Glide.with(SuitDetailsLineActivity.this).load(suit.getSuitOuterwear().getClothingImageUrl()).into(ivOuterwear);
            Outerwear.setClothingViewTime(System.currentTimeMillis());
        }
        if (Trousers == null){
            Toast.makeText(SuitDetailsLineActivity.this,"该套装的裤子缺失，建议删除该套装",Toast.LENGTH_LONG).show();
            btnSubmit.setVisibility(View.GONE);
            Glide.with(SuitDetailsLineActivity.this).load(R.drawable.bg_suit_trousers_stroke).into(ivTrousers);
        }else {
            Glide.with(SuitDetailsLineActivity.this).load(suit.getSuitTrousers().getClothingImageUrl()).into(ivTrousers);
            Trousers.setClothingViewTime(System.currentTimeMillis());
        }
        if (Shoes == null){
            Toast.makeText(SuitDetailsLineActivity.this,"该套装的鞋子缺失，建议删除该套装",Toast.LENGTH_LONG).show();
            btnSubmit.setVisibility(View.GONE);
            Glide.with(SuitDetailsLineActivity.this).load(R.drawable.bg_suit_shoes_stroke).into(ivShoes);
        }else {
            Glide.with(SuitDetailsLineActivity.this).load(suit.getSuitShoes().getClothingImageUrl()).into(ivShoes);
            Trousers.setClothingViewTime(System.currentTimeMillis());
        }
        suit.update();
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
