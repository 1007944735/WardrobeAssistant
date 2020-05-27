package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.socketmanage.MySocket;
import com.example.wardrobeassistant.weatherhttp.GetWeatherTask;

public class MainActivity extends BaseActivity implements GetWeatherTask.GetWeatherDetailCallBack {
    private CardView cvMyClothing;
    private CardView cvSuit;
    private TextView tv_title;
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
    }

    private void initView() {
        cvMyClothing = findViewById(R.id.cv_my_clothing);
        cvSuit = findViewById(R.id.cv_suit);
        tv_title = findViewById(R.id.tv_title);
        weatherTemTv = findViewById(R.id.weatherTemTv);
        weatherTipTv = findViewById(R.id.weatherTipTv);
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
                startActivity(new Intent(MainActivity.this,PresetClothingSuitActivity.class));
            }
        });
        tv_title.setOnClickListener(new View.OnClickListener() {
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
}
