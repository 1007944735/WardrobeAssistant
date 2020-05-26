package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetBaseBuilder;

import java.io.File;

public class ClothingAddActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    private QMUITopBarLayout topBar;
    private EditText etClothingName;
    private LinearLayout llColorSystem;
    private TextView tvClothingColorSystem;
    private LinearLayout llType;
    private TextView tvClothingType;
    private LinearLayout llOccasion;
    private TextView tvClothingOccasion;
    private LinearLayout llWarmthLevel;
    private TextView tvClothingWarmthLevel;
    private EditText etClothingLocation;
    private ImageView ivClothingImage;


    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_add);
        initView();
        initTopBar();
        initListener();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        etClothingName = findViewById(R.id.et_clothing_name);
        llColorSystem = findViewById(R.id.ll_color_system);
        tvClothingColorSystem = findViewById(R.id.tv_clothing_color_system);
        llType = findViewById(R.id.ll_type);
        tvClothingType = findViewById(R.id.tv_clothing_type);
        llOccasion = findViewById(R.id.ll_occasion);
        tvClothingOccasion = findViewById(R.id.tv_clothing_occasion);
        llWarmthLevel = findViewById(R.id.ll_warmth_level);
        tvClothingWarmthLevel = findViewById(R.id.tv_clothing_warmth_level);
        etClothingLocation = findViewById(R.id.et_clothing_location);
        ivClothingImage = findViewById(R.id.iv_clothing_image);
    }

    private void initTopBar() {
        topBar.setTitle("新增服装");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.addRightTextButton("完成", R.id.clothing_add_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initListener() {
        llColorSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetList("服装色系", getResources().getStringArray(R.array.clothingColorSystem), new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {

                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        tvClothingColorSystem.setText(tag);
                        dialog.dismiss();
                    }
                });
            }
        });

        llType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetList("服装类型", getResources().getStringArray(R.array.clothingClassify), new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {

                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        tvClothingType.setText(tag);
                        dialog.dismiss();
                    }
                });
            }
        });
        llOccasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetList("服装场合", getResources().getStringArray(R.array.clothingOccasion), new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {

                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        tvClothingOccasion.setText(tag);
                        dialog.dismiss();
                    }
                });
            }
        });

        llWarmthLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetList("保暖等级", getResources().getStringArray(R.array.clothingWarmthLevel), new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {

                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        tvClothingWarmthLevel.setText(tag);
                        dialog.dismiss();
                    }
                });
            }
        });

        ivClothingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetList("获取方式", new String[]{"相册", "拍照"}, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {

                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        if (position == 0) {
                            getTakePhoto().onPickFromGallery();
                        } else if (position == 1) {
                            File file = new File(getExternalFilesDir("clothingPic").getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg");
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            getTakePhoto().onPickFromCapture(Uri.fromFile(file));
                        }
                        dialog.dismiss();
                    }
                });
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

    @Override
    public void takeSuccess(TResult result) {
        Log.d("TAG", "takeSuccess: " + result.getImage().getOriginalPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.d("TAG", "takeFail: " + msg);
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
}
