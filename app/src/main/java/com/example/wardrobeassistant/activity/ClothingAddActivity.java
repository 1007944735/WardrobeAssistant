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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.util.StringUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetBaseBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.File;
import java.util.List;

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
    private TextView tvClothingLocation;
    private ImageView ivClothingImage;
    private LinearLayout llWarmthLocation;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private Clothing clothing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_add);
        clothing = new Clothing();
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
        tvClothingLocation = findViewById(R.id.tv_clothing_location);
        ivClothingImage = findViewById(R.id.iv_clothing_image);
        llWarmthLocation = findViewById(R.id.ll_warmth_location);
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
                String name = etClothingName.getText().toString();
                String colorSystem = tvClothingColorSystem.getText().toString();
                String type = tvClothingType.getText().toString();
                String occasion = tvClothingOccasion.getText().toString();
                String warmthLevel = tvClothingWarmthLevel.getText().toString();
                String location = tvClothingLocation.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(ClothingAddActivity.this, "请输入服装名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (colorSystem.isEmpty()) {
                    Toast.makeText(ClothingAddActivity.this, "请选择服装色系", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type.isEmpty()) {
                    Toast.makeText(ClothingAddActivity.this, "请选择服装类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (occasion.isEmpty()) {
                    Toast.makeText(ClothingAddActivity.this, "请选择穿着场合", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (warmthLevel.isEmpty()) {
                    Toast.makeText(ClothingAddActivity.this, "请选择保暖等级", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (location.isEmpty()) {
                    Toast.makeText(ClothingAddActivity.this, "请选择存放位置", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (clothing.getClothingImageUrl().isEmpty()) {
                    Toast.makeText(ClothingAddActivity.this, "请添加存放图片", Toast.LENGTH_SHORT).show();
                    return;
                }

                clothing.setClothingName(name);
                clothing.setClothingColorSystem(StringUtils.colorSystemToDb(colorSystem));
                clothing.setClothingType(StringUtils.clothingTypeToDb(type));
                clothing.setClothingOccasion(StringUtils.occasionToDb(occasion));
                clothing.setClothingWarmthLevel(StringUtils.warmLevelToDb(warmthLevel));
                clothing.setClothingLocation(StringUtils.clothLocatainToDb(location));
                long timeMillis = System.currentTimeMillis();
                clothing.setClothingInputTime(timeMillis);
                clothing.setClothingLocationChangeTime(timeMillis);
                clothing.setIsTakeOut(false);
                clothing.setClothingViewTime(timeMillis);

                final long id = DbManager.getInstance().getSession().getClothingDao().insert(clothing);

                final QMUITipDialog tipDialog = new QMUITipDialog.Builder(ClothingAddActivity.this)
                        .setIconType(id > 0 ? QMUITipDialog.Builder.ICON_TYPE_SUCCESS : QMUITipDialog.Builder.ICON_TYPE_FAIL)
                        .setTipWord(id > 0 ? "添加成功" : "添加失败")
                        .create();
                tipDialog.show();
                if (id > 0) {
                    topBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tipDialog.dismiss();
                            setResult(-1);
                            finish();
                        }
                    }, 1000);
                }
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
                            Toast.makeText(ClothingAddActivity.this, "当前位置已有衣物，请重新选择", Toast.LENGTH_SHORT).show();
                        }
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
//                            CropOptions options = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
//                            getTakePhoto().onPickFromCaptureWithCrop(Uri.fromFile(file), options);
                            //框架裁剪有问题，暂时不用
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
        Glide.with(this).load(result.getImage().getOriginalPath()).into(ivClothingImage);
        clothing.setClothingImageUrl(result.getImage().getOriginalPath());
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
