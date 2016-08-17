package com.kevin.recycleradapter;

import android.content.Context;


/**
 * ListView中显示的item,包含需要显示的数据，对应的ViewHolder
 * @param <T> AbsViewHolder
 * @param <U> ContentData Type
 */
public interface IRecycleViewDisplayListItem<T extends AbsRecycleViewHolderController,U> {

    /**
     * 设置对应的viewholder的值
     * @param context getView时传递
     * @param viewHolderController getView时传递对应的viewHolder对象
     * @param position
     * @param generalRecyclerViewAdapter
     */
    void onShow(Context context, T viewHolderController, int position, GeneralRecyclerViewAdapter generalRecyclerViewAdapter);

    /**
     * 每个viewHolder的唯一标示
     * @return
     */
    String getDisplayItemId();

    /**
     * 设置需要展示的数据对象
     * @param o
     */
    void setDisplayData(U o);

    /**
     * 获取需要展示的数据对象
     * @return
     */
    U getDisplayData();
}
