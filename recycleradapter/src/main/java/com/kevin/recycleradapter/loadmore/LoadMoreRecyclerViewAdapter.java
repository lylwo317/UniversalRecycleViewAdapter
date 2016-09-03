package com.kevin.recycleradapter.loadmore;

import android.content.Context;

import com.kevin.recycleradapter.AbsRecyclerViewHolderController;
import com.kevin.recycleradapter.IRecycleViewDisplayItem;
import com.kevin.recycleradapter.UniversalRecyclerViewAdapter;

import java.util.List;

/**
 * 通用的RecyclerView自动加载更多Adapter，已经实现了自定义的样式，可以仿照{@link DefaultLoadMoreDisplayItem}和{@link DefaultLoadMoreHolderController}
 * 实现自定义的加载更多样式，并通过{@link #setLoadMoreDisplayItem(ILoadMoreDisplayItem)}将自定义加载更多样式绑定到Adapter中。
 * 或者直接继承{@link UniversalRecyclerViewAdapter}来完全自定义自己的自动加载更多Adapter
 * @author XieJiaHua create on 2016/8/18.(lylwo317@gmail.com)
 */
public class LoadMoreRecyclerViewAdapter<T extends IRecycleViewDisplayItem> extends UniversalRecyclerViewAdapter<T>
{


    @SuppressWarnings("unchecked")
    private ILoadMoreDisplayItem loadMoreDisplayItem = new DefaultLoadMoreDisplayItem();

    /**
     * 在哪个位置出发加载更多，从最后一个位置倒数。最小值为0，此时在出现加载更多时候才会回调{@link #loadMoreListener}。
     * 如果设置值为1，则会在出现倒数第一个数据Item时回调{@link #loadMoreListener}，从而提前触发加载更多
     */
    private int loadMorePosition = 4;

    /**
     * 获取自动加载更多触发位置，默认是在最后一个Item加载到布局中时
     * @return 自动加载更多触发位置
     */
    public int getLoadMorePosition() {
        return loadMorePosition;
    }

    /**
     * 设置自动加载更多触发位置，不能为负数。默认为1，表示倒数第一个Item被加载到布局中时（这里不算LoadMoreItem）。为0时，则表示
     * LoadMoreItem加载到布局中时。
     * @param loadMorePosition 自动加载更多触发位置
     */
    public void setLoadMorePosition(int loadMorePosition) {
        this.loadMorePosition = loadMorePosition;
    }

    public void setLoadMoreDisplayItem(ILoadMoreDisplayItem loadMoreDisplayItem)
    {
        this.loadMoreDisplayItem = loadMoreDisplayItem;
    }

    public LoadMoreRecyclerViewAdapter(Context context)
    {
        super(context);
    }

    public LoadMoreRecyclerViewAdapter(Context context, List<T> displayItemList)
    {
        super(context, displayItemList);
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener)
    {
        this.loadMoreListener = loadMoreListener;
    }

    private LoadMoreListener loadMoreListener = null;

    private boolean loading = false;

    private boolean isLoadMoreEnable = true;

    private boolean isLoadMoreLayoutPosition(int position)
    {
        return position + 1 == getItemCount();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getDisplayItem(int position)
    {
        if (isLoadMoreLayoutPosition(position))
        {
            return (T)loadMoreDisplayItem;
        }
        else
        {
            return super.getDisplayItem(position);
        }

    }

    @Override
    public void onBindViewHolder(AbsRecyclerViewHolderController.InnerRecyclerViewViewHolder innerRecyclerViewViewHolder, int position)
    {
        super.onBindViewHolder(innerRecyclerViewViewHolder, position);

        if (position >= getItemCount() - (1 + loadMorePosition))
        {
            loadMoreData();
        }
    }

    /**
     *  执行加载更多
     */
    protected void loadMoreData()
    {
        if (!isLoadMoreEnable) {
            return;
        }

        if (loading)
        {
            return;
        }

        if (loadMoreListener != null)
        {
            startLoading();
            loadMoreDisplayItem.switchLoadingState();
            loadMoreListener.onLoadMore(displayItemList.get(displayItemList.size() - 1));
        }
    }

    /**
     * 调用来切换到失败页面，并通过{@code state}来确定需要切换的状态。比如是，网络未连接，还是服务器返回404,可以给予相应的提示
     * 回调{@link ILoadMoreDisplayItem#switchFailedState(int)}更新界面状态
     * @param state 失败的状态，是网络未连接，还是服务器错误。根据这个错误可以切换到相应的界面
     */
    public void doneLoadByFailed(int state)
    {
        stopLoading();
        loadMoreDisplayItem.switchFailedState(state);
    }

    /**
     * 加载成功，让加载更多可以再次触发
     */
    public void doneLoadBySuccess()
    {
        stopLoading();
    }

    private void stopLoading()
    {
        loading = false;
    }

    private void startLoading()
    {
        loading = true;
    }

    public void openLoadMore()
    {
        if (!this.isLoadMoreEnable)
        {
            this.isLoadMoreEnable = true;
            notifyDataSetChanged();
        }
    }

    public void closeLoadMore()
    {
        loading = false;
        if (this.isLoadMoreEnable)
        {
            this.isLoadMoreEnable = false;
            loadMoreDisplayItem.switchNoMoreState();
        }
    }
    /**
     * 已经加载完所有数据，关闭加载更多
     */
    public void doneLoadByFinishLoadAll() {
        closeLoadMore();
    }

    @Override
    public int getItemCount()
    {

        if (super.getItemCount() == 0)//避免没有数据时，绘制LoadMoreLayout
        {
            return 0;
        }

        return super.getItemCount() + 1;
    }

    public interface LoadMoreListener
    {
        void onLoadMore(IRecycleViewDisplayItem item);
    }
}
