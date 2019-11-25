package com.example.group_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;

import java.util.ArrayList;

class MyViewPaper extends ViewGroup implements View.OnClickListener {
    private Context mContext;
    private int[] images={R.mipmap.one,R.mipmap.two,R.mipmap.three};
    private int scrollx=0;
    private int scrolly=0;
    private int position=0;
    private Scroller scroller;
    private pintu pintu;
    private String[] p={"one","two"};
    public MyViewPaper(Context context) {
        super(context);
        this.mContext=context;
        scroller=new Scroller(mContext);
        init();
    }
    public MyViewPaper(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.mContext=context;
        scroller=new Scroller(mContext);
        init();
    }
    public MyViewPaper(Context context, AttributeSet attrs,int defstyleAttr) {//attributeSet attr从xml中定义参数，defStyleAttr 总体中优先级最高的属性,defStyleres 优先级次之的内置于view的style Xml直接定义 > xml中style引用 > defStyleAttr > defStyleRes > theme直接定义
        super(context,attrs,defstyleAttr);
        this.mContext=context;
        scroller=new Scroller(mContext);
        init();
    }
//
//    private void init() {
////        for(int i=0;i<images.length;i++)
////        {
////            ImageView iv=new ImageView(getContext());
////            iv.setBackgroundResource(images[i]);
////            this.addView(iv);//添加子view
////        }
////    }
    @SuppressLint("ResourceType")
    private void init() {
      pintu=new pintu(getContext());
      this.addView(pintu);
      ImageView iv=new ImageView(getContext());
      iv.setBackgroundResource(images[0]);
      this.addView(iv);//添加子view
        Button button=new Button(getContext());
        button.setText("设置");
        button.setId(0);
        button.setOnClickListener(this);
        this.addView(button);
        Button x2=new Button(getContext());
        x2.setId(1);
        x2.setText("2x2");
        x2.setOnClickListener(this);
        this.addView(x2);
        Button x3=new Button(getContext());
        x3.setId(3);
        x3.setText("3x3");
        x3.setOnClickListener(this);
        this.addView(x3);
        Button x4=new Button(getContext());
        x4.setId(4);
        x4.setText("4x4");
        x4.setOnClickListener(this);
        this.addView(x4);
        Button one=new Button(getContext());//----------------------------------------------------------------------------------------------
        one.setId(11);
        one.setText("one");
        one.setOnClickListener(this);
        this.addView(one);
        Button two=new Button(getContext());
        two.setId(12);
        two.setText("two");
        two.setOnClickListener(this);
        this.addView(two);
        Button three=new Button(getContext());
        three.setId(13);
        three.setText("three");
        three.setOnClickListener(this);
        this.addView(three);
        Button ok=new Button(getContext());//---------------------------------------------------------------------------------------------
        ok.setId(2);
        ok.setText("ok");
        ok.setOnClickListener(this);
        this.addView(ok);
    }
    //处理子View的摆放位置
    GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //相对滑动：X方向滑动多少距离，view就跟着滑动多少距离
            scrollBy((int) distanceX, 0);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    });
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {//change为view有新的尺寸或位置，l为相对于父view的left位置，t表示相对于父view的Top位置，r表示相对于父vuew的right，b表示相对于父view的Bottom位置
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            if(i==2) {
                childView.layout(getWidth() - 200, 100, getWidth(), t + 200);
            }
            else if(i>=3&&i<6){
                childView.layout(getWidth()+100+(i-3)*150,t+100,getWidth()+100+((i-2)*150),t+200);
            }else if(i>=6&&i<9) {
                childView.layout(getWidth()+100+(i-6)*150,t+300,getWidth()+100+((i-5)*150),t+400);
            }else if(i==9)
            {
                childView.layout(getWidth()+100,t+1300,2*getWidth()-100,t+1400);
            }
            else {
                childView.layout(i*getWidth(),t,(i+1)*getWidth(),b);
            }
            System.out.println("第"+i+"子view\n");
        }

    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        mGestureDetector.onTouchEvent(event);
//        switch (event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("A_M","scrollx="+getScrollX());
//                scrollx=getScrollX();//相对于初始位置的滑动距离
//                position=(getScrollX()+getWidth()/2)/getWidth();//postion第几个屏幕(过了屏幕一半就+1)
//                if(position>=images.length-1){
//                    position=0;
//                }
//                if(position<0){
//                    position=images.length-2;
//                }break;
//            case MotionEvent.ACTION_UP:
//                scroller.startScroll(scrollx,0,position*getWidth()-scrollx,0);
//                scrollTo(position*getWidth(),0);
//                invalidate();
//                break;
//            default:break;
//        }
//        return true;
//    }
    @Override
    public void computeScroll()
    {
        if(scroller.computeScrollOffset())
        {
            scrollTo(scroller.getCurrX(),0);
            Log.e("Currx","scroller.getCurrX()="+scroller.getCurrX());
            postInvalidate();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case 0:scrollTo(getWidth(),0);
                invalidate();
                break;
            case 1:
                pintu.setCOL(2);
                pintu.setROW(2);
                pintu.init();
                pintu.startGame();
                //pintu.onDraw(new Canvas());
                break;
            case 2:
                scrollTo(0,0);
                invalidate();
                break;
            case 3:
                pintu.setCOL(3);
                pintu.setROW(3);
                pintu.init();
                pintu.startGame();
                break;
            case 4:
                pintu.setCOL(4);
                pintu.setROW(4);
                pintu.init();
                pintu.startGame();
                break;
            case 11:
                pintu.setTupian("one");
                pintu.init();
                pintu.startGame();
                break;
            case 12:
                pintu.setTupian("two");
                pintu.init();
                pintu.startGame();
                break;
            case 13:
                pintu.setTupian("timg");
                pintu.init();
                pintu.startGame();
                break;
                default:break;
        }
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String select= p[position];
//        pintu.setTupian(select);
//        pintu.init();
//        pintu.startGame();
//    }
}
