package com.kevin.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;


/**
 * ListView中显示的item,包含需要显示的数据，对应的ViewHolder
 *
 * @param <T> AbsViewHolder
 * @param <Data> ContentData Type
 */
public interface IRecycleViewDisplayItem<T extends AbsRecyclerViewHolderController, Data> {

    /**
     * 数据展示操作和业务相关的操作。{@link UniversalRecyclerViewAdapter#onBindViewHolder(RecyclerView.ViewHolder, int)} 调用此方法
     * @param context                    getView时传递
     * @param viewHolderController       getView时传递对应的viewHolder对象
     * @param position 在Adapter绑定的数据集合中的位置
     * @param universalRecyclerViewAdapter 绑定的Adapter
     */
    void onShow(Context context, T viewHolderController, int position, UniversalRecyclerViewAdapter universalRecyclerViewAdapter);

    /**
     * 设置需要展示的数据对象
     * @param displayData 需要现在是数据对象
     */
    void setDisplayData(Data displayData);

    /**
     * 获取需要展示的数据对象
     * @return 需要展示的数据对象
     */
    Data getDisplayData();
}
