package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.socketmanage.MySocket;
import com.example.wardrobeassistant.util.StringUtils;
import com.example.wardrobeassistant.util.TimeUtils;
import com.example.wardrobeassistant.weatherhttp.GetWeatherTask;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

public class MainActivity extends BaseActivity implements GetWeatherTask.GetWeatherDetailCallBack {
    private CardView cvMyClothing;
    private CardView cvSuit;
    private CardView cv_forget_clothing;
    private CardView cv_suit_recommend;
    private TextView tv_title;
    private TextView tv_socket_state;
    private TextView weatherTemTv;
    private TextView weatherTipTv;
    private GetWeatherTask weatherTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        loadWeatherData();
        List<Clothing> clothing = DbManager.getInstance().getSession().getClothingDao().loadAll();
        if (clothing != null && clothing.size()>0){
            for (Clothing clo : clothing) {
               int time = TimeUtils.differentDays(clo.getClothingViewTime());
                if (time >= 14){
                    new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                            .setTitle("提示")
                            .setMessage("您有衣物已经超过14天没有查看了，是否查看？")
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    Intent intent = new Intent(MainActivity.this, ForgetClothingListActivity.class);
                                    intent.putExtra("DAY", 14);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    break;
                }

            }
        }

    }

    private void initView() {
        cvMyClothing = findViewById(R.id.cv_my_clothing);
        cvSuit = findViewById(R.id.cv_suit);
        tv_title = findViewById(R.id.tv_title);
        tv_socket_state = findViewById(R.id.tv_socket_state);
        weatherTemTv = findViewById(R.id.weatherTemTv);
        weatherTipTv = findViewById(R.id.weatherTipTv);
        cv_forget_clothing = findViewById(R.id.cv_forget_clothing);
        cv_suit_recommend = findViewById(R.id.cv_suit_recommend);
    }

    private void initListener() {
        cvMyClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MyClothingListActivity.class));
            }
        });

        cvSuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PresetClothingSuitListActivity.class));
            }
        });

        cv_forget_clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgetClothingListActivity.class);
                intent.putExtra("DAY", 0);
                startActivity(intent);
            }
        });
        cv_suit_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuitRecommendDetailsActivity.class);
                startActivity(intent);
            }
        });

        tv_socket_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SocketConfigActivity.class));
            }
        });

    }

    private void loadWeatherData(){
        weatherTask = new GetWeatherTask();
        weatherTask.setCallBack(this);
        weatherTask.execute();
    }

    @Override
    public void getWeatherData(String tem, String tip) {
        weatherTemTv.setText(tem);
        weatherTipTv.setText(tip);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果是退出状态就是未连接
        if (MySocket.getInstall().isExits()){
            tv_socket_state.setText("点击连接");
        }else {
            tv_socket_state.setText("已连接");
        }
    }

}
