package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wardrobeassistant.Constant;
import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.adapter.ClothingClassifyAdapter;
import com.example.wardrobeassistant.adapter.ClothingClassifyDetailsAdapter;
import com.example.wardrobeassistant.adapter.ClothingClassifyFotgetAdapter;
import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.util.TimeUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;

import java.util.ArrayList;
import java.util.List;

public class ForgetClothingListActivity extends BaseActivity implements ClothingClassifyAdapter.OnItemClickListener {
    private QMUITopBarLayout topBar;
    private RecyclerView classifyList;
    private QMUIPullLayout pullLayout;
    private ClothingClassifyAdapter mClassifyAdapter;
    private RecyclerView classifyDetailsList;
    private ClothingClassifyFotgetAdapter mClassifyDetailsAdapter;
    private String classifyType = Constant.TYPE_OVERCOAT;
    private int day;

    private static final int CLOTHING_ADD_REQUEST = 1000;
    private static final int CLOTHING_DETAILS_REQUEST = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clothing_list);
        day = getIntent().getIntExtra("DAY", 0);
        initView();
        initTopBar();
        initClassifyList();
        initClassifyDetailsList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        pullLayout = findViewById(R.id.pull_layout);
        classifyList = findViewById(R.id.classify_list);
        classifyDetailsList = findViewById(R.id.classify_details_list);
    }

    private void initTopBar() {
        topBar.setTitle("遗忘提醒");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        mClassifyDetailsAdapter = new ClothingClassifyFotgetAdapter(this);
        classifyDetailsList.setAdapter(mClassifyDetailsAdapter);
        mClassifyDetailsAdapter.setOnItemClickListener(new ClothingClassifyFotgetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Clothing clothing) {
                Intent intent = new Intent(ForgetClothingListActivity.this, ClothingDetailsActivity.class);
                intent.putExtra("clothing", clothing);
                intent.putExtra("justShow", false);
                startActivityForResult(intent, CLOTHING_DETAILS_REQUEST);
            }
        });
    }

    private void loadData() {
        List<Clothing> clothing = DbManager.getInstance().getSession().getClothingDao().queryBuilder().where(ClothingDao.Properties.ClothingType.eq(classifyType)).list();
        List<Clothing> forgetClothing = new ArrayList<>();
        if (clothing != null && clothing.size()>0){
            for (Clothing clo : clothing) {
                int time = TimeUtils.differentDays(clo.getClothingViewTime());
                if(time >= day){
                    forgetClothing.add(clo);
                }
            }
        }
        mClassifyDetailsAdapter.clear();
        mClassifyDetailsAdapter.addAll(forgetClothing);
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
