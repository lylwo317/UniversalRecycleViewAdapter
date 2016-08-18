package com.kevin.recycleradapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by XieJiaHua on 2016/8/18.
 */
public class LoadMoreRecyclerViewAdapter<T extends IRecycleViewDisplayItem> extends GeneralRecyclerViewAdapter<T>
{


    @SuppressWarnings("unchecked")
    private LoadMoreLayoutDisplayItem footViewDisplayListItem = new LoadMoreLayoutDisplayItem();

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

    private boolean isLoadMoreOpen = false;

    private boolean isFootLoadMoreLayout(int position)
    {
        if (isLoadMoreOpen)
        {
            return position + 1 == getItemCount();
        }else
        {
            return false;
        }
    }

    @Override
    public T getItem(int position)
    {
        if (isFootLoadMoreLayout(position)) {
            return (T)footViewDisplayListItem;
        } else
        {
            return super.getItem(position);
        }

    }

    @Override
    public void onBindViewHolder(AbsRecycleViewHolderController.InnerRecyclerViewViewHolder innerRecyclerViewViewHolder, int position)
    {
        super.onBindViewHolder(innerRecyclerViewViewHolder, position);

        if (position > getItemCount() - 4)
        {
            loadMoreData();
        }
    }

    protected void loadMoreData()
    {
        if (loading || !isLoadMoreOpen)
        {
            return;
        }

        if (loadMoreListener != null)
        {
            loading = true;
            loadMoreListener.onLoadMore(displayItemList.get(displayItemList.size() - 1));
        }
    }

    public void loadMoreSuccess()
    {
        loading = false;
        footViewDisplayListItem.switchCurrentState(LoadMoreState.LOADING);
    }

    public void loadMoreFail()
    {
        loading = false;
        footViewDisplayListItem.switchCurrentState(LoadMoreState.FAILED);
    }

    public void openLoadMoreItem()
    {
        if (!this.isLoadMoreOpen)
        {
            //open
            this.isLoadMoreOpen = true;
            notifyDataSetChanged();
        }
    }

    public void loadMoreFinish()
    {
        closeLoadMoreItem();
    }

    public void closeLoadMoreItem()
    {
        loading = false;
        if (this.isLoadMoreOpen)
        {
            notifyItemRemoved(getItemCount()-1);
            this.isLoadMoreOpen = false;
            notifyItemRangeChanged(getItemCount()-1, getItemCount());
        }
    }

    @Override
    public int getItemCount()
    {

        if (super.getItemCount() == 0)//避免没有数据时，绘制LoadMoreLayout
        {
            return 0;
        }

        if (isLoadMoreOpen)
        {
            return super.getItemCount() + 1;
        }

        return super.getItemCount();
    }

    public static class LoadMoreLayoutHolderController extends AbsRecycleViewHolderController{

        public TextView tvLoad;



        @Override
        protected void initView(View view) {
            tvLoad = (TextView) view.findViewById(R.id.tv_load);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.recycler_view_default_load_more_layout;
        }
    }

    enum LoadMoreState{
        LOADING,
        FAILED
    }

    public static class LoadMoreLayoutDisplayItem implements IRecycleViewDisplayItem<LoadMoreLayoutHolderController,Object>
    {

        private LoadMoreLayoutHolderController viewHolderController;

        private boolean showFailed = false;

        private LoadMoreState state=LoadMoreState.LOADING;

        private LoadMoreRecyclerViewAdapter loadMoreRecyclerViewAdapter;

        @Override
        public void onShow(Context context, LoadMoreLayoutHolderController viewHolderController, int position, GeneralRecyclerViewAdapter generalRecyclerViewAdapter)
        {
            this.viewHolderController = viewHolderController;
            loadMoreRecyclerViewAdapter = (LoadMoreRecyclerViewAdapter)generalRecyclerViewAdapter;
            updateState(state);
        }

        public void switchCurrentState(LoadMoreState newState)
        {
            if (state != newState)
            {
                state = newState;
                updateState(state);
            }
        }


        private void updateState(LoadMoreState state)
        {
            if (viewHolderController != null)
            {
                switch (state){

                    case LOADING:
                        showLoadingLayout();
                        break;
                    case FAILED:
                        showFailedLayout();
                        break;
                }
            }
        }



        private void showFailedLayout()
        {
            viewHolderController.tvLoad.setText("抱歉~~出了点问题，点击重试一下。");
            viewHolderController.tvLoad.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    switchCurrentState(LoadMoreState.LOADING);
                    loadMoreRecyclerViewAdapter.loadMoreData();
                }
            });
        }

        private void showLoadingLayout()
        {
            viewHolderController.tvLoad.setText("正在加载...");
            viewHolderController.tvLoad.setOnClickListener(null);
        }


        @Override
        public void setDisplayData(Object o) {

        }

        @Override
        public Object getDisplayData() {
            return null;
        }
    }

    @Override
    public void onViewDetachedFromWindow(AbsRecycleViewHolderController.InnerRecyclerViewViewHolder holder)
    {
        if (getItemCount()-1==holder.getAdapterPosition())
        {
            loadMoreData();
        }
    }


    public interface LoadMoreListener{
        void onLoadMore(IRecycleViewDisplayItem item);
    }
}
