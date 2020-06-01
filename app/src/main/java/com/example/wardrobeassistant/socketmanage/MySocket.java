package com.example.wardrobeassistant.socketmanage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zjn on 2017/12/26.
 */

public class MySocket {
    private Context context;
    private String host;
    private int port;
    private int timeOut;
    private Socket socket;
    private List<MySocketCallBack> callBacks;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static MySocket instance;
    private boolean isClose = false;//是否手动断开
    private boolean isNeWork = true;//是否有网络
    private int timeOutCount = 0;//超时的次数
    private InputStream inputStream;
    private OutputStream outputStream;
    //是否退出
    private boolean isExits = true;

    public static MySocket  getInstall(){
        if (instance == null){
            synchronized (MySocket.class) {
                if (instance == null){
                    instance = new MySocket();
                }
            }
        }
        return instance;
    }

    private MySocket() {
        callBacks = new ArrayList<>();
    }

    public MySocket setContext(Context context) {
        this.context = context.getApplicationContext();
        return this;
    }

    public MySocket setHost(String host) {
        this.host = host;
        return this;
    }

    public MySocket setPort(int port) {
        this.port = port;
        return this;
    }

    public MySocket setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public boolean isExits() {
        return isExits;
    }

    //重新设置了 所以设置完成之后要先关闭连接 然后重新设置
    private void setConfig(Context context, String host, int port, int timeOut){
        this.context = context.getApplicationContext();
        this.host = host;
        this.port = port;
        this.timeOut = timeOut;
        close(false);

    }

    public void socketConnect() {
        close(false);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketAddress address = new InetSocketAddress(host, port);
                    socket = new Socket();
                    socket.setKeepAlive(true);
                    socket.connect(address, timeOut);
                    isClose = false;
                    //链接成功
                    for (MySocketCallBack listener : callBacks) {
                        listener.socketConnectSucceed();
                        isExits = false;
                    }

                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();

                    timeOutCount = 0;
                    //发送心跳包，判断是否意外断开
//                    handler.post(runnable);
                    //接受数据的线程池一直开着
                    socketReceiveMessage();
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    //超时的次数
                    timeOutCount++;
                    //链接超时
                    handler.removeCallbacks(againConnect);
                    if (timeOutCount<4){
                        handler.post(againConnect);
                    }else{
                        timeOutCount = 0;
                        isExits = true;
                        for (MySocketCallBack listener : callBacks) {
                            listener.socketConnectTimeOut();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //超时的次数
                    timeOutCount++;
                    handler.removeCallbacks(againConnect);
                    if (timeOutCount<4){
                        handler.post(againConnect);
                    }else{
                        timeOutCount = 0;
                        isExits = true;
                    }
                    //链接异常
                    for (MySocketCallBack listener : callBacks) {
                        listener.socketConnectError("连接IP异常:" + e.toString() + e.getMessage());
                    }
                }
            }
        });
    }

    //发送数据
    public void socketSendMessage(final String msg) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (socket != null && socket.isConnected() && !socket.isClosed() && !socket.isOutputShutdown()) {
                    try {
                        String message = msg + "\r\n";
                        outputStream.write(message.getBytes("GBK"));
                        outputStream.flush();
                        //数据发送成功
                        for (MySocketCallBack listener : callBacks) {
                            listener.sendMessageSucceed(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        timeOutCount++;
                        handler.removeCallbacks(againConnect);
                        if (timeOutCount<4){
                            handler.post(againConnect);
                        }else{
                            timeOutCount = 0;
                            isExits = true;
                        }
                        //数据发送失败
                        for (MySocketCallBack listener : callBacks) {
                            listener.sendMessageError(e.toString(),0);
                        }

                    }
                }else{
                    for (MySocketCallBack listener : callBacks) {
                        listener.sendMessageError("链接已关闭，请打开",0);
                    }
                }
            }
        });
    }

    //接受数据
    private void socketReceiveMessage() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                    if (socket != null && socket.isConnected() && !socket.isClosed()) {
                        try {
                            byte[] buffer = new byte[1024 * 4];
                            int length = 0;
                            while (null != socket
                                    &&socket.isConnected()
                                    && !socket.isClosed()
                                    && !socket.isInputShutdown()
                                    && ((length = inputStream.read(buffer)) != -1)) {
                                if (length > 0) {
                                    String message = new String(Arrays.copyOf(buffer, length),"UTF-8");
                                    //数据接收成功
                                    for (MySocketCallBack listener : callBacks) {
                                        listener.receiveMessageSucceed(message);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            if (e.getMessage().contains("Socket closed")) {
                                for (MySocketCallBack listener : callBacks) {
                                    listener.socketClose();
                                    isExits = true;
                                }
                            }else {
                                //消息接受失败
                                for (MySocketCallBack listener : callBacks) {
                                    listener.receiveMessagError("消息接受失败");
                                }
                            }
                        }
                    }else {
                        for (MySocketCallBack listener : callBacks) {
                            listener.receiveMessagError("消息接受失败,链接已断开");
                        }
                    }
            }
        });
    }

    //发送心跳
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            socketSendHeart();
            handler.postDelayed(runnable, 10 * 1000);
        }
    };

    private void socketSendHeart() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("type", "heart");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (socket != null && socket.isConnected() && !socket.isClosed() && !socket.isOutputShutdown()) {
                    try {
                        String message = jsonObject.toString() + "\r\n";
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        //数据发送成功
                        for (MySocketCallBack listener : callBacks) {
                            listener.sendMessageSucceed(1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        timeOutCount++;
                        handler.removeCallbacks(againConnect);
                        if (timeOutCount<4){
                            handler.post(againConnect);
                        }else{
                            timeOutCount = 0;
                            isExits = true;
                        }
                        //数据发送失败
                        for (MySocketCallBack listener : callBacks) {
                            listener.sendMessageError(e.toString(),1);
                        }
                    }
                }else{
                    for (MySocketCallBack listener : callBacks) {
                        listener.sendMessageError("链接已关闭，请打开",1);
                    }
                }
            }
        });
    }

    //关闭连接
    public void close(boolean isClose) {
        this.isClose = isClose;
        //如果是手动关闭，移除重连机制和检查网络机制
        if (isClose){
            handler.removeCallbacks(againConnect);
            handler.removeCallbacks(checkNeWork);
            //链接关闭
            for (MySocketCallBack listener : callBacks) {
                listener.socketClose();
                isExits = true;
            }
        }
        try {
            handler.removeCallbacks(runnable);
            if (socket != null){
                if (inputStream != null){
                    inputStream.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //重连机制
    private Runnable againConnect = new Runnable() {
        @Override
        public void run() {
            //先关闭所有
            close(isClose);

            if (!isClose){
                String s = getCurrentNetType();
                //判断网络状态
                if (s.equals("null")){
                    isNeWork = false;
                }else {
                    isNeWork = true;
                }

                if (isNeWork){
                    for (MySocketCallBack listener : callBacks) {
                        listener.socketConnectError("正在重连");
                    }
                    getInstall().socketConnect();
                }else {
                    handler.post(checkNeWork);
                    handler.removeCallbacks(againConnect);
                }

            }else {
                //手动退出
                for (MySocketCallBack listener : callBacks) {
                    listener.socketClose();
                    isExits = true;
                }
            }

        }
    };

    //检查网络状态
    private Runnable checkNeWork = new Runnable() {
            @Override
            public void run() {
                String s = getCurrentNetType();
                if (s.equals("null")){
                    isNeWork = false;
                    handler.postDelayed(checkNeWork, 10 * 1000);
                }else {
                    isNeWork = true;
                    handler.post(againConnect);
                    handler.removeCallbacks(checkNeWork);
                }
            }
    };

    public void registerListener(MySocketCallBack listener) {
        if (listener != null) {
            callBacks.remove(listener);
            callBacks.add(listener);
        }
    }

    public void unRegisterListener(MySocketCallBack listener) {
        if (listener != null) {
            callBacks.remove(listener);
        }
    }


    //判断当前的网络状态
    private String getCurrentNetType() {
        String netType = "";
        // TODO Auto-generated method stub
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            netType = "null";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            netType = "wifi";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA
                    || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                netType = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                    || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                netType = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
                netType = "4g";
            }
        }
        return netType;
    }
}
