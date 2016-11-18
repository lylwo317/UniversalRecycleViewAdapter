package com.kevin.recycleradapter.loadmore;

import com.kevin.recycleradapter.AbsRecyclerViewHolderCtrl;
import com.kevin.recycleradapter.IRecyclerDisplayItem;

/**
 * 自动加载更多Item界面控制
 * Created by kevin on 8/19/16.
 * Email:lylwo317@gmail.com
 */
interface ILoadMoreDisplayItem<T extends AbsRecyclerViewHolderCtrl, Data> extends IRecyclerDisplayItem<T, Data> {

    /**
     * 当加载失败时，根据{@code failedState}值，切换相应的失败状态界面，例如没有网络，服务器错误等等
     *
     * @param failedState 可以定义失败的类型，根据不同的failedState做不同的展示
     */
    void switchFailedState(int failedState);


    /**
     * 当调用{@link LoadMoreRecyclerViewAdapter#closeLoadMore()}时，会调用此方法来切换到
     * 没有更多可以加载的界面提示
     *
     * @see DefaultLoadMoreDisplayItem#switchNoMoreState()
     */
    void switchNoMoreState();

    /**
     * 切换到加载中的状态(默认状态)
     */
    void switchLoadingState();
}
