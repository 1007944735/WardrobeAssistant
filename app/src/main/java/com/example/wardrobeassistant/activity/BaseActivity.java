package com.example.wardrobeassistant.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.arch.SwipeBackLayout;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import static com.qmuiteam.qmui.arch.SwipeBackLayout.DRAG_DIRECTION_NONE;

@SuppressLint("Registered")
public class BaseActivity extends QMUIActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    @Override
    protected int getDragDirection(@NonNull SwipeBackLayout swipeBackLayout, @NonNull SwipeBackLayout.ViewMoveAction viewMoveAction, float downX, float downY, float dx, float dy, float slopTouch) {
        return DRAG_DIRECTION_NONE;
    }
}
