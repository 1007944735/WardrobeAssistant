package com.example.wardrobeassistant.socketmanage;

/**
 * Created by zjn on 2017/12/26.
 */

public interface MySocketCallBack {

    void socketConnectTimeOut();//链接超时

    void socketConnectError(String errorMsg);//链接错误

    void socketConnectSucceed();//链接成功

    void sendMessageError(String errorMsg, int type);//信息发送失败

    void sendMessageSucceed(int type);//信息发送成功 0为正常消息发送 1为心跳

    void receiveMessageSucceed(String message);//接收消息成功

    void receiveMessagError(String message);//接收消息失败

    void socketClose();//关闭链接

}
