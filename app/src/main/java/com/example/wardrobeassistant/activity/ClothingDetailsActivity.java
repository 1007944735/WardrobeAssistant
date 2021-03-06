package com.example.wardrobeassistant.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.socketmanage.MySocket;
import com.example.wardrobeassistant.socketmanage.MySocketCallBack;
import com.example.wardrobeassistant.util.CheckSocketConnect;
import com.example.wardrobeassistant.util.StringUtils;
import com.example.wardrobeassistant.util.TimeUtils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

public class ClothingDetailsActivity extends BaseActivity {
    private Clothing clothing;
    private QMUITopBarLayout topBar;
    private ImageView ivClothingImage;
    private TextView tvClothingColorSystem;
    private TextView tvClothingType;
    private TextView tvClothingOccasion;
    private TextView tvClothingWarmthLevel;
    private TextView tvClothingLocation;
    private TextView tvClothingInputTime;
    private TextView tvLastChangeTimeTitle;
    private TextView tvLastChangeTime;
    private LinearLayout llWarmthLocation;
    private QMUIButton btnSubmit;

    private String location;

    private boolean justShow;

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
        setContentView(R.layout.activity_clothing_details);
        clothing = getIntent().getParcelableExtra("clothing");
        justShow = getIntent().getBooleanExtra("justShow", true);
        clothing.setClothingViewTime(System.currentTimeMillis());
        DbManager.getInstance().getSession().getClothingDao().update(clothing);

        MySocket.getInstall().registerListener(callBack);

        initView();
        initTopBar();
        initListener();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        ivClothingImage = findViewById(R.id.iv_clothing_image);
        tvClothingColorSystem = findViewById(R.id.tv_clothing_color_system);
        tvClothingType = findViewById(R.id.tv_clothing_type);
        tvClothingOccasion = findViewById(R.id.tv_clothing_occasion);
        tvClothingWarmthLevel = findViewById(R.id.tv_clothing_warmth_level);
        tvClothingLocation = findViewById(R.id.tv_clothing_location);
        tvClothingInputTime = findViewById(R.id.tv_clothing_input_time);
        tvLastChangeTimeTitle = findViewById(R.id.tv_last_change_time_title);
        tvLastChangeTime = findViewById(R.id.tv_last_change_time);
        btnSubmit = findViewById(R.id.btn_submit);
        llWarmthLocation = findViewById(R.id.ll_warmth_location);

        Glide.with(this).load(clothing.getClothingImageUrl()).into(ivClothingImage);
        tvClothingColorSystem.setText(StringUtils.colorSystemFromDb(clothing.getClothingColorSystem()));
        tvClothingType.setText(StringUtils.clothingTypeFromDb(clothing.getClothingType()));
        tvClothingOccasion.setText(StringUtils.occasionFromDb(clothing.getClothingOccasion()));
        tvClothingWarmthLevel.setText(StringUtils.warmLevelFromDb(clothing.getClothingWarmthLevel()));
        tvClothingInputTime.setText(TimeUtils.format(clothing.getClothingInputTime()));
        tvLastChangeTimeTitle.setText(clothing.getIsTakeOut() ? "上次取出时间" : "上次放入时间");
        tvLastChangeTime.setText(TimeUtils.format(clothing.getClothingLocationChangeTime()));

        location = clothing.getClothingLocation();

        if (location.isEmpty()){
            tvClothingLocation.setHint("请选择存放位置");
        }else {
            tvClothingLocation.setText(StringUtils.clothLocatainFromDb(location));
        }

        if (justShow) {
            btnSubmit.setVisibility(View.GONE);
            llWarmthLocation.setEnabled(false);
        } else {
            btnSubmit.setVisibility(View.VISIBLE);
            btnSubmit.setText(clothing.getIsTakeOut() ? "放入" : "取出");
            llWarmthLocation.setEnabled(true);
        }
    }

    private void initTopBar() {
        topBar.setTitle(clothing.getClothingName());
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });
    }

    private void initListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExit = CheckSocketConnect.checkSocket(ClothingDetailsActivity.this);
                if (isExit){
                    return;
                }

                boolean takeOut = clothing.getIsTakeOut();

                if (takeOut){
                    location = tvClothingLocation.getText().toString();

                    if (location.isEmpty()) {
                        Toast.makeText(ClothingDetailsActivity.this, "请输入存放位置", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    clothing.setClothingLocation(StringUtils.clothLocatainToDb(location));
                    MySocket.getInstall().socketSendMessage(clothing.getClothingLocation());
                }else {
                    MySocket.getInstall().socketSendMessage(clothing.getClothingLocation());
                    clothing.setClothingLocation("");
                }
                clothing.setClothingLocationChangeTime(System.currentTimeMillis());
                clothing.setIsTakeOut(!takeOut);
                DbManager.getInstance().getSession().getClothingDao().update(clothing);
                final QMUITipDialog tipDialog = new QMUITipDialog.Builder(ClothingDetailsActivity.this)
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

        llWarmthLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSimpleBottomSheetList("收纳位置", getResources().getStringArray(R.array.clothingLocation), new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {

                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        List<Clothing> clothList = DbManager.getInstance().getSession().getClothingDao().queryBuilder().where(ClothingDao.Properties.ClothingLocation.eq(StringUtils.clothLocatainToDb(tag))).list();
                        if (clothList == null || clothList.size() <1){
                            tvClothingLocation.setText(tag);
                            dialog.dismiss();
                        }else {
                            Toast.makeText(ClothingDetailsActivity.this, "当前位置已有衣物，请重新选择", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
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
}
