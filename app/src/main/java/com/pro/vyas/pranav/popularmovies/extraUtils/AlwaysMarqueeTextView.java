package com.pro.vyas.pranav.popularmovies.extraUtils;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

public class AlwaysMarqueeTextView extends android.support.v7.widget.AppCompatTextView {
    protected boolean a;

    public AlwaysMarqueeTextView(Context context) {
        super(context);
        a = false;
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        a = false;
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        a = false;
    }

    public boolean isFocused() {
        return a || super.isFocused();
    }

    public void setAlwaysMarquee(boolean flag) {
        setSelected(flag);
        setSingleLine(flag);
        if (flag)
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
        a = flag;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused)

            super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused)
            super.onWindowFocusChanged(focused);
    }
}