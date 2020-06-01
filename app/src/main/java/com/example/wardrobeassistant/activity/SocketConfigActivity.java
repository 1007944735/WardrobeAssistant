package com.example.wardrobeassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.socketmanage.MySocket;
import com.example.wardrobeassistant.socketmanage.MySocketCallBack;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class SocketConfigActivity extends BaseActivity {
    private Context mContext;
    private QMUITopBarLayout topBar;
    private EditText serverHostEt;
    private EditText serverPortEt;
    private Button serverStartBtn;
    private EditText serverComEt;
    private Button serverComBtn;
    private TextView serverMsgTv;

    private String serverHost;
    private int serverPort = -1;
    private MySocket socket;
    private String messageSocket;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            serverMsgTv.setText(messageSocket);
            int type = msg.what;
            switch (type){
                case -1:
                    serverStartBtn.setText("开始连接");
                    break;
                case 0:
                    break;
                case 1:
                    serverStartBtn.setText("断开连接");
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
        setContentView(R.layout.activity_socketconfig);
        mContext = this;
        initView();
        initTopBar();
        initListener();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        serverHostEt = findViewById(R.id.serverHostEt);
        serverPortEt = findViewById(R.id.serverPortEt);
        serverStartBtn = findViewById(R.id.serverStartBtn);
        serverComEt = findViewById(R.id.serverComEt);
        serverComBtn = findViewById(R.id.serverComBtn);
        serverMsgTv = findViewById(R.id.serverMsgTv);
        socket = MySocket.getInstall();
        socket.registerListener(callBack);
        if (socket.isExits()){
            serverStartBtn.setText("开始连接");
        }else {
            serverStartBtn.setText("断开连接");
        }
    }

    private void initTopBar() {
        topBar.setTitle("连接衣橱");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        serverStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverHost = serverHostEt.getText().toString().trim();
                String port = serverPortEt.getText().toString();
                if(socket.isExits()){

                    if (!TextUtils.isEmpty(port)) {
                        serverPort = Integer.valueOf(port);
                    }
                    if (TextUtils.isEmpty(serverHost)) {
                        Toast.makeText(mContext,"IP地址不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (serverPort == -1) {
                        Toast.makeText(mContext,"端口不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    socket.setContext(mContext)
                            .setHost(serverHost)
                            .setPort(Integer.valueOf(port))
                            .setTimeOut(30 * 1000)
                            .socketConnect();
                }else {
                    socket.close(true);
                }
            }
        });

        serverComBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.socketSendMessage(serverComEt.getText().toString());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.unRegisterListener(callBack);
    }

    private void changeView(int type) {
        //type -1  关闭 0正常传输 1 成功
        Message msg = new Message();
        msg.what = type;
        mHandler.sendMessage(msg);
    }

}
