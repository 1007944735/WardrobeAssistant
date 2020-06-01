package com.example.wardrobeassistant.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.wardrobeassistant.activity.ClothingDetailsActivity;
import com.example.wardrobeassistant.activity.MainActivity;
import com.example.wardrobeassistant.activity.SocketConfigActivity;
import com.example.wardrobeassistant.socketmanage.MySocket;

public class CheckSocketConnect {

    public static boolean checkSocket(Context context) {
        if (MySocket.getInstall().isExits()){
            Toast.makeText(context,"衣橱未连接",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context,SocketConfigActivity.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

}
