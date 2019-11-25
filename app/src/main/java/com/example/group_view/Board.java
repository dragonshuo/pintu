package com.example.group_view;


import java.util.Random;

class Board {
    private static final String TAG="board";
    private int[][] array=null;
    private int row=0;
    private int col=0;
    private int[][] dir={//4*2的数组
            {0,1},//下
            {1,0},//右
            {0,-1},//上
            {-1,0}//左
    };
    private void createIntegerArray(int row,int col)
    {
        array=new int[row][col];
        int idx=0;
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                array[i][j]=idx++;//012 编号
                                  //345
                                  //678
            }
        }
    }
    private Point move(int srcX,int srcY,int xOffset,int yOffset)
    {
        int x=srcX+xOffset;
        int y=srcY+yOffset;
        if(x<0||y<0||x>=col||y>=row)//超出范围返回Point（-1，-1）
        {
            return new Point(-1,-1);
        }
        int temp=array[y][x];
        array[y][x]=array[srcY][srcX];
        array[srcY][srcX]=temp;
        return new Point(x,y);
    }
    private Point getNextPoint(Point src)
    {
        Random rd=new Random();//重置随机种子
        int idx=rd.nextInt(4);//随机从【0，4）选一个整数
        int xOffset=dir[idx][0];//随机上下左右选一个操作
        int yOffset=dir[idx][1];
        Point newPoint=move(src.getX(),src.getY(),xOffset,yOffset);
        if(newPoint.getX()!=-1&&newPoint.getY()!=-1){//没有越界
            return newPoint;
        }
        return getNextPoint(src);//越界的话接着找
    }
    public int[][] credteRandomBoard(int row,int col)//在可以恢复的情况下进行随机次数的打乱
    {
        if(row<2||col<2)
        {
            throw new IllegalArgumentException("行和列均不能小于2");
        }
        this.row=row;
        this.col=col;
        createIntegerArray(row,col);
        int count=0;
        Point tempPoint=new Point(col-1,row-1);
        Random rd=new Random();
        int num=rd.nextInt(100)+20;//随机生成【20-120）随机整数
        while (count<num)
        {
            tempPoint=getNextPoint(tempPoint);
            count++;
        }
        return array;
    }
    public boolean isSuccess(int[][] arr)
    {
        int idx=0;
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr[i].length&&idx<row*col-1;j++)//j小于数组的”宽度（3）“且idx小于8
            {
                if(arr[idx/row][idx%col]>arr[(idx+1)/row][(idx+1)%col])// 如果发现前一项比后一项大，代表没有拼好（除取行，余取列）
                {
                    return false;
                }
                idx++;
            }
        }
        return true;
    }
}
