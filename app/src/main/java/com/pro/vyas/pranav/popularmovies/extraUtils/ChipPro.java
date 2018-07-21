package com.pro.vyas.pranav.popularmovies.extraUtils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.pro.vyas.pranav.popularmovies.R;

public class ChipPro extends android.support.v7.widget.AppCompatTextView {
    public ChipPro(Context context) {
        super(context);
    }

    public ChipPro(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChipPro(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAsChip(boolean setAsChip){
       if(setAsChip){
       }
    }
}
