package com.kevin.recycleradapter.loadmore;

import android.content.Context;
import android.view.View;

import com.kevin.recycleradapter.UniversalRecyclerViewAdapter;
import com.kevin.recycleradapter.R;

/**
 * 默认的加载更多样式，需要自定义可以通过实现{@link ILoadMoreDisplayItem}接口
 * Created by kevin on 8/19/16.
 * Email:lylwo317@gmail.com
 */
public class DefaultLoadMoreDisplayItem implements ILoadMoreDisplayItem<DefaultLoadMoreHolderController, Object>
{


    @LoadMoreState
    private int currentState = LoadMoreState.LOADING;

    @LoadMoreState
    private int needUpdateState = LoadMoreState.FAILED;

    private DefaultLoadMoreHolderController viewHolderController;

    private LoadMoreRecyclerViewAdapter loadMoreRecyclerViewAdapter;

    @Override
    public void onShow(Context context, DefaultLoadMoreHolderController viewHolderController, int position, UniversalRecyclerViewAdapter universalRecyclerViewAdapter) {
        this.viewHolderController = viewHolderController;
        loadMoreRecyclerViewAdapter = (LoadMoreRecyclerViewAdapter)universalRecyclerViewAdapter;
        updateState(needUpdateState);
    }

    @Override
    public void switchFailedState(@LoadMoreState int failedState)
    {
        needUpdateState = failedState;
        updateState(needUpdateState);
    }

    @Override
    public void switchLoadingState()
    {
        needUpdateState = LoadMoreState.LOADING;

        updateState(needUpdateState);
    }


    private void updateState(@LoadMoreState int newState) {

        if (viewHolderController != null) {
            if (currentState != newState)//状态变化
            {
                currentState = newState;
                switch (currentState)
                {
                    case LoadMoreState.LOADING:
                        showLoadingLayout();
                        break;
                    case LoadMoreState.FAILED:
                        showFailedLayout();
                        break;
                }
            }

        }
    }

    /**
     * 显示失败时候的界面提示
     */
    private void showFailedLayout() {
        viewHolderController.tvLoad.setText(R.string.load_fail);
        viewHolderController.tvLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFailedState(LoadMoreState.LOADING);
                loadMoreRecyclerViewAdapter.loadMoreData();
            }
        });
    }

    /**
     * 显示正在加载的界面样式
     */
    private void showLoadingLayout() {
        viewHolderController.tvLoad.setText(R.string.load_loading);
        viewHolderController.tvLoad.setOnClickListener(null);
    }


    @Override
    public void setDisplayData(Object displayData) {

    }

    @Override
    public Object getDisplayData() {
        return null;
    }
}
