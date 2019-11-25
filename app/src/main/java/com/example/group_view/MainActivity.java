package com.example.group_view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Scroller;

public class MainActivity extends AppCompatActivity {
    //手势识别器
   // private GestureDetector gestureDetector;
    private  static int screenWidth;
    private  static int screenHeight;
    public static int getScreenWidth(){
        return  screenWidth;
    }
    public static int getScreenHeight(){ return screenHeight-125;}//125为最下面的按钮

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        screenWidth =metrics.widthPixels;
        screenHeight=metrics.heightPixels;
        System.out.println("屏幕为"+screenWidth+"|"+screenHeight);
        System.out.println(getScreenHeight()+"|"+getScreenWidth());
    }
}
