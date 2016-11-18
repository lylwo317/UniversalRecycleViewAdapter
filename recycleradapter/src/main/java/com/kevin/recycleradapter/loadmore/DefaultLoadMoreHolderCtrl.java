package com.kevin.recycleradapter.loadmore;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevin.recycleradapter.AbsRecyclerViewHolderCtrl;
import com.kevin.recycleradapter.R;

/**
 * 默认的加载更多View控制器
 * Created by kevin on 8/19/16.
 * Email:lylwo317@gmail.com
 */
public class DefaultLoadMoreHolderCtrl extends AbsRecyclerViewHolderCtrl
{
    public View failedLayout;
    private View loadingLayout;
    private View noMoreLayout;

    private ViewGroup rootLayout;

    private View currentLayout;


    @SuppressLint("InflateParams")
    @Override
    protected void initView(View rootView) {

        if (rootView instanceof ViewGroup) {
            rootLayout = (ViewGroup) rootView;
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        loadingLayout = inflater.inflate(R.layout.recycler_view_default_loading_layout, rootLayout,false);

        failedLayout = inflater.inflate(R.layout.recycler_view_default_failed_retry_layout, rootLayout,false);

        noMoreLayout = inflater.inflate(R.layout.recycler_view_default_no_more_layout, rootLayout,false);
    }

    /**
     * 切换到相应的布局
     * @param layout 需要显示的布局
     */
    private void switchToLayout(View layout) {
        if (layout != null && layout!=currentLayout) {
            rootLayout.removeViewInLayout(currentLayout);
            rootLayout.addView(layout);
            currentLayout = layout;
        }
    }

    /**
     * 切换到失败的布局
     */
    public void switchToFailedLayout() {
        switchToLayout(failedLayout);
    }

    /**
     * 切换到正在加载的布局
     */
    public void switchToLoadingLayout() {
        switchToLayout(loadingLayout);
    }

    /**
     * 切换到没有更多可以加载的布局
     */
    public void switchToNoMoreLayout() {
        switchToLayout(noMoreLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_view_default_load_more_layout;
    }
}
