package com.example.group_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class mylistview  extends ListView {
    public mylistview(Context context) {
        super(context);
    }
    public mylistview(Context context, AttributeSet attributeSet){
        super(context);
    }
    public mylistview(Context context,AttributeSet attributeSet,int defStyle){
        super(context);
    }
    @Override
    protected void onMeasure(int Width,int height){
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(Width,expandSpec);
    }
}
