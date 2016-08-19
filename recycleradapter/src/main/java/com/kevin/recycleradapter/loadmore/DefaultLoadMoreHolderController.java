package com.kevin.recycleradapter.loadmore;

import android.view.View;
import android.widget.TextView;

import com.kevin.recycleradapter.AbsRecyclerViewHolderController;
import com.kevin.recycleradapter.R;

/**
 * 默认的加载更多样式
 * Created by kevin on 8/19/16.
 * Email:lylwo317@gmail.com
 */
public class DefaultLoadMoreHolderController extends AbsRecyclerViewHolderController
{

    public TextView tvLoad;


    @Override
    protected void initView(View rootView) {
        tvLoad = (TextView) rootView.findViewById(R.id.tv_load);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_view_default_load_more_layout;
    }
}
