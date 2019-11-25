package com.example.group_view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.io.InputStream;

class pintu extends View {
    private static final  String TAG="MainVIew";
    private Context context;
    private Paint paint;
    private Bitmap bitmap;
    private int Width;
    private int Height;
    private Bitmap[] bitmaps;
    private int[][] data;
    private Board board;
    private  int COL=3;
    private  int ROW=3;
    private boolean isSuccess;
    private String tupian;
    private int[][] dir={
            {-1,0},//左
            {0,-1},//上
            {1,0},//右
            {0,1}//下
    };

    public void setCOL(int COL) {
        this.COL = COL;
    }

    public void setROW(int ROW) {
        this.ROW = ROW;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public pintu(Context context) {
        super(context);//引用父类的构造方法，之后便可以使用父类的函数
        this.context=context;
        paint=new Paint();
        tupian="timg";
        paint.setAntiAlias(true);//抗锯齿
        init();
        startGame();
        Log.d("MainView",MainActivity.getScreenWidth() + "," + MainActivity.getScreenHeight());
    }
    public void init()
    {
        AssetManager assetManager=context.getAssets();//资源管理框架
        InputStream assetInputStream= null;//打开assets文件下的文件
        try {
            assetInputStream = assetManager.open(tupian+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap b= BitmapFactory.decodeStream(assetInputStream);
        bitmap=Bitmap.createScaledBitmap(b,1080,1745,true);//高和宽分别调用父类的函数
        Width=bitmap.getWidth()/COL;//长高分别除3
        Height=bitmap.getHeight()/ROW;
        bitmaps=new Bitmap[COL*ROW];
        int idx=0;
        for(int i=0;i<ROW;i++)
        {
            for(int j=0;j<COL;j++)
            {
                bitmaps[idx++]=Bitmap.createBitmap(bitmap,j*Width,i*Height,Width,Height);//图片分块,装入Bitmap数组里
            }
        }
    }
    public void startGame()
    {
        board=new Board();
        data=board.credteRandomBoard(ROW,COL);
        isSuccess=false;
        invalidate();//重新绘制
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.GRAY);
        for(int i=0;i<ROW;i++) {
            for (int j = 0; j < COL; j++) {
                int idx = data[i][j];
                if(idx==ROW*COL-1&&!isSuccess)
                {
                    continue;
                }
                canvas.drawBitmap(bitmaps[idx],j*Width,i*Height,paint);
            }
        }
    }
    private Point xyToIndex(int x,int y)//确定点击的块
    {
        int newX=x%Width>0?1:0;
        int newY=y%Width>0?1:0;
        int col=x/Width+newX;
        int row=y/Height+newY;
        return  new Point(col-1,row-1);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            Point point=xyToIndex((int)event.getX(),(int)event.getY());
            for(int i=0;i<dir.length;i++)//上下左右各试一次（通过测试获得白块的位置（如果白块的相对位置被记录在每个块上，是否可以加快速度？（要想速度快必须存储更多的使用信息，也就是空间换时间（其实就是充分的利用所有
            // 信息））））
            {
                int newX=point.getX()+dir[i][0];
                int newY=point.getY()+dir[i][1];
                if(newX>=0&&newX<COL&&newY>=0&&newY<ROW)//判断是否越界
                {
                    if(data[newY][newX]==COL*ROW-1)//找到周围的空白块
                    {
                        int temp=data[point.getY()][point.getX()];
                        data[point.getY()][point.getX()]=data[newY][newX];
                        data[newY][newX]=temp;
                        invalidate();
                        if(board.isSuccess(data))
                        {
                            isSuccess=true;
                            invalidate();
                            new AlertDialog.Builder(context)
                                    .setTitle("完成拼图")
                                    .setCancelable(false)
                                    .setMessage("胜利o(*￣▽￣*)ブ")
                                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startGame();
                                        }
                                    })
                                    .setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.exit(0);
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                }
            }
        }else if(event.getAction()==MotionEvent.ACTION_MOVE)
        {
            return true;
        }
        return true;
    }
}