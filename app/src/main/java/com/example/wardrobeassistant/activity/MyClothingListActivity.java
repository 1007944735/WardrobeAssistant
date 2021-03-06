package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wardrobeassistant.Constant;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.adapter.ClothingClassifyAdapter;
import com.example.wardrobeassistant.adapter.ClothingClassifyDetailsAdapter;
import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;

import java.util.List;

public class MyClothingListActivity extends BaseActivity implements ClothingClassifyAdapter.OnItemClickListener {
    private QMUITopBarLayout topBar;
    private RecyclerView classifyList;
    private QMUIPullLayout pullLayout;
    private ClothingClassifyAdapter mClassifyAdapter;
    private RecyclerView classifyDetailsList;
    private ClothingClassifyDetailsAdapter mClassifyDetailsAdapter;
    private String classifyType = Constant.TYPE_OVERCOAT;

    private static final int CLOTHING_ADD_REQUEST = 1000;
    private static final int CLOTHING_DETAILS_REQUEST = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clothing_list);
        initView();
        initTopBar();
        initClassifyList();
        initClassifyDetailsList();
        loadData();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        pullLayout = findViewById(R.id.pull_layout);
        classifyList = findViewById(R.id.classify_list);
        classifyDetailsList = findViewById(R.id.classify_details_list);
    }

    private void initTopBar() {
        topBar.setTitle("我的服装");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.addRightTextButton("添加", R.id.clothing_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MyClothingListActivity.this, ClothingAddActivity.class), CLOTHING_ADD_REQUEST);
            }
        });
    }

    private void initClassifyList() {
        mClassifyAdapter = new ClothingClassifyAdapter(this);
        classifyList.setLayoutManager(new LinearLayoutManager(this));
        mClassifyAdapter.setOnItemClickListener(this);
        classifyList.setAdapter(mClassifyAdapter);
    }

    private void initClassifyDetailsList() {
        pullLayout.setActionListener(new QMUIPullLayout.ActionListener() {
            @Override
            public void onActionTriggered(@NonNull QMUIPullLayout.PullAction pullAction) {
            }
        });

        classifyDetailsList.setLayoutManager(new GridLayoutManager(this, 2));
        mClassifyDetailsAdapter = new ClothingClassifyDetailsAdapter(this);
        classifyDetailsList.setAdapter(mClassifyDetailsAdapter);
        mClassifyDetailsAdapter.setOnItemClickListener(new ClothingClassifyDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Clothing clothing) {
                Intent intent = new Intent(MyClothingListActivity.this, ClothingDetailsActivity.class);
                intent.putExtra("clothing", clothing);
                intent.putExtra("justShow", false);
                startActivityForResult(intent, CLOTHING_DETAILS_REQUEST);
            }

            @Override
            public void onLongItemClick(final Clothing clothing) {

                new QMUIDialog.MessageDialogBuilder(MyClothingListActivity.this)
                        .setTitle("提示")
                        .setMessage("是否把这件衣服从衣橱销毁？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                DbManager.getInstance().getSession().getClothingDao().delete(clothing);
                                loadData();
                                dialog.dismiss();
                            }
                        })
                        .create().show();


            }
        });
    }

    private void onRefreshData(QMUIPullLayout.PullAction pullAction) {
//        if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_TOP) {
//            onRefreshData();
//        } else if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_BOTTOM) {
//            onLoadMore();
//        }
//        mPullLayout.finishActionRun(pullAction);
    }

    private void onLoadMore(QMUIPullLayout.PullAction pullAction) {
//        if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_TOP) {
//            onRefreshData();
//        } else if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_BOTTOM) {
//            onLoadMore();
//        }
//        mPullLayout.finishActionRun(pullAction);
    }

    private void loadData() {
        List<Clothing> clothing = DbManager.getInstance().getSession().getClothingDao().queryBuilder().where(ClothingDao.Properties.ClothingType.eq(classifyType)).list();
        mClassifyDetailsAdapter.clear();
        mClassifyDetailsAdapter.addAll(clothing);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CLOTHING_ADD_REQUEST && resultCode == -1) {
            loadData();
        } else if (requestCode == CLOTHING_DETAILS_REQUEST && resultCode == -1) {
            loadData();
        }
    }

    @Override
    public void onItemClick(String classifyType) {
        this.classifyType = classifyType;
        loadData();
    }
}
