package com.sasuke.imagify.util;

/**
 * Created by abc on 5/12/2018.
 */

import android.support.v7.widget.GridLayoutManager;

public class MySpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    int spanCnt1;

    public MySpanSizeLookup(int spanPos, int spanCnt1, int spanCnt2) {
        super();
        this.spanCnt1 = spanCnt1;
    }

    @Override
    public int getSpanSize(int position) {
        return spanCnt1;
    }
}

