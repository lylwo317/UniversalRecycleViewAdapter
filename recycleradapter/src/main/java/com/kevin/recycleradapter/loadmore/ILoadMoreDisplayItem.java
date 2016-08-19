package com.kevin.recycleradapter.loadmore;

import com.kevin.recycleradapter.AbsRecyclerViewHolderController;
import com.kevin.recycleradapter.IRecycleViewDisplayItem;

/**
 * Created by kevin on 8/19/16.
 * Email:lylwo317@gmail.com
 */
public interface ILoadMoreDisplayItem<T extends AbsRecyclerViewHolderController, U> extends IRecycleViewDisplayItem<T, U> {

    /**
     * 当加载失败时，根据newstate值，切换相应的失败状态界面，例如没有网络，出错重试
     *
     * @param failedState 可以定义失败的类型，根据不同的failedState做不同的展示
     */
    void switchFailedState(int failedState);

    /**
     * 切换到加载中的状态，也就是默认状态
     */
    void switchLoadingState();
}
