package com.kevin.recycleradapter.loadmore;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;

import com.kevin.recycleradapter.UniversalRecyclerViewAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 默认的加载更多界面切换逻辑，需要自定义可以通过实现{@link ILoadMoreDisplayItem}接口
 * Created by kevin on 8/19/16.
 * Email:lylwo317@gmail.com
 */
public class DefaultLoadMoreDisplayItem implements ILoadMoreDisplayItem<DefaultLoadMoreHolderCtrl, Object> {


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DefaultLoadMoreDisplayItem.LOADING, DefaultLoadMoreDisplayItem.FAILED, DefaultLoadMoreDisplayItem.NO_MORE})
    public @interface LoadMoreState {
    }

    private static final int LOADING = 0;
    public static final int FAILED = 1;
    private static final int NO_MORE = 2;


    @LoadMoreState
    private int currentState = LOADING;

    @LoadMoreState
    private int needUpdateState = LOADING;

    private DefaultLoadMoreHolderCtrl viewHolderController;

    private LoadMoreRecyclerViewAdapter loadMoreRecyclerViewAdapter;

    @Override
    public void onShow(Context context, DefaultLoadMoreHolderCtrl viewHolderController, int position, UniversalRecyclerViewAdapter universalRecyclerViewAdapter) {
        this.viewHolderController = viewHolderController;
        loadMoreRecyclerViewAdapter = (LoadMoreRecyclerViewAdapter) universalRecyclerViewAdapter;
        //强制更新一次界面
        updateStateAlways(needUpdateState);
        bindListener();
    }

    private void bindListener() {
        //点击重试，出现加载
        viewHolderController.failedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreRecyclerViewAdapter.loadMoreData();
            }
        });
    }

    @Override
    public void switchFailedState(@LoadMoreState int failedState) {
        needUpdateState = failedState;
        updateStateIfNeed(needUpdateState);
    }

    @Override
    public void switchNoMoreState() {
        needUpdateState = NO_MORE;
        updateStateIfNeed(needUpdateState);
    }

    @Override
    public void switchLoadingState() {
        needUpdateState = LOADING;
        updateStateIfNeed(needUpdateState);
    }

    /**
     * 如果状态没有发生变化，直接忽略
     * @param newState 新的状态
     */
    private void updateStateIfNeed(@LoadMoreState int newState) {

        if (currentState != newState)//状态变化
        {
            updateStateAlways(newState);
        }

    }

    /**
     * 如果可以的话，总会执行切换操作
     * @param newState 新的状态
     */
    private void updateStateAlways(@LoadMoreState int newState) {
        currentState = newState;
        if (viewHolderController != null) {
            switch (newState) {
                case LOADING:
                    viewHolderController.switchToLoadingLayout();
                    break;
                case FAILED:
                    viewHolderController.switchToFailedLayout();
                    break;
                case NO_MORE:
                    viewHolderController.switchToNoMoreLayout();
                    break;
            }
        }
    }

    @Override
    public void setDisplayData(Object displayData) {

    }

    @Override
    public Object getDisplayData() {
        return null;
    }
}
