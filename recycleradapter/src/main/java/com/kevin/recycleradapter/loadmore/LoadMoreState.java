package com.kevin.recycleradapter.loadmore;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author XieJiaHua create on 2016/8/19.(lylwo317@gmail.com)
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({LoadMoreState.LOADING, LoadMoreState.FAILED})
public @interface LoadMoreState
{
    int LOADING = 0;
    int FAILED = 1;
}
