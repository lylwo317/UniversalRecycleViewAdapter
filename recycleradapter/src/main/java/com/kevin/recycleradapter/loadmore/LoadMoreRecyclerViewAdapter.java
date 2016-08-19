package com.kevin.recycleradapter.loadmore;

import android.content.Context;

import com.kevin.recycleradapter.AbsRecyclerViewHolderController;
import com.kevin.recycleradapter.IRecycleViewDisplayItem;
import com.kevin.recycleradapter.UniversalRecyclerViewAdapter;

import java.util.List;

/**
 * @author XieJiaHua create on 2016/8/18.(lylwo317@gmail.com)
 */
public class LoadMoreRecyclerViewAdapter<T extends IRecycleViewDisplayItem> extends UniversalRecyclerViewAdapter<T>
{


    @SuppressWarnings("unchecked")
    private ILoadMoreDisplayItem loadMoreDisplayItem = new DefaultLoadMoreDisplayItem();

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

    private boolean isLoadMoreEnable = false;

    private boolean isLoadMoreLayoutPosition(int position)
    {
        return isLoadMoreEnable && position + 1 == getItemCount();
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

        if (position > getItemCount() - 4)
        {
            loadMoreData();
        }
    }

    protected void loadMoreData()
    {
        if (loading || !isLoadMoreEnable)
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

    public void doneLoadMoreFailed(@LoadMoreState int state)
    {
        doneLoading();
        loadMoreDisplayItem.switchFailedState(state);
    }

    public void doneLoadMoreSuccess()
    {
        doneLoading();
    }

    private void doneLoading()
    {
        loading = false;
    }

    private void startLoading()
    {
        loading = true;
    }

    public void enableLoadMore()
    {
        if (!this.isLoadMoreEnable)
        {
            this.isLoadMoreEnable = true;
            notifyDataSetChanged();
        }
    }

    public void disableLoadMore()
    {
        loading = false;
        if (this.isLoadMoreEnable)
        {
            //int position = getItemCount()-1;
            this.isLoadMoreEnable = false;
            /*notifyItemRemoved(position);
            notifyItemChanged(position);*/
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount()
    {

        if (super.getItemCount() == 0)//避免没有数据时，绘制LoadMoreLayout
        {
            return 0;
        }

        if (isLoadMoreEnable)
        {
            return super.getItemCount() + 1;
        }

        return super.getItemCount();
    }

    @Override
    public void onViewDetachedFromWindow(AbsRecyclerViewHolderController.InnerRecyclerViewViewHolder holder)
    {
        if (getItemCount() - 1 == holder.getAdapterPosition())
        {
            loadMoreData();
        }
    }

    public interface LoadMoreListener
    {
        void onLoadMore(IRecycleViewDisplayItem item);
    }
}
