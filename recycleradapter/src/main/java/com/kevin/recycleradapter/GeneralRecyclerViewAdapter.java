package com.kevin.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对RecyclerViewAdapter封装，简化使用。这是一个通用的RecyclerViewAdapter。
 * Created by XieJiaHua on 2016/8/11.
 */
public class GeneralRecyclerViewAdapter<T extends IRecycleViewDisplayItem> extends RecyclerView.Adapter<AbsRecycleViewHolderController.InnerRecyclerViewViewHolder>
{
    private Context context;
    protected List<T> displayItemList;

    private Map<Class< ? extends IRecycleViewDisplayItem>, Class<? extends AbsRecycleViewHolderController>> displayAndViewHolderMap = new HashMap<>();

    private List<Class<? extends AbsRecycleViewHolderController>> viewHolderControllerList = new ArrayList<>();

    public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener)
    {
        this.onItemChangedListener = onItemChangedListener;
    }

    private OnItemChangedListener onItemChangedListener = null;

    protected T getItem(int position)
    {
        return displayItemList.get(position);
    }

    public GeneralRecyclerViewAdapter(Context context)
    {
        this.context = context;
    }

    public GeneralRecyclerViewAdapter(Context context, List<T> displayItemList)
    {
        this.displayItemList = displayItemList;
        this.context = context;
    }

    public void setList(List<T> list) {
        if (displayItemList != null) {
            displayItemList.clear();
        } else {
            displayItemList = new ArrayList<>();
        }

        if (list != null && list.size() != 0) {
            this.displayItemList.addAll(list);
        }

        notifyItemInserted(0);
        notifyItemRangeChanged(0, getItemCount());
    }

    public void addList(List<T> list) {
        if (list != null && list.size() != 0) {
            if (displayItemList == null) {
                displayItemList = new ArrayList<>();
            }
            int position = getItemCount()-1;
            displayItemList.addAll(list);
            notifyItemInserted(position);
        }
    }

    public void addItem(int position,T newItem)
    {
        if (displayItemList == null)
        {
            displayItemList = new ArrayList<>();
        }

        if (position > displayItemList.size() || position < 0) {
            return;
        }
        if (onItemChangedListener != null)
        {
            onItemChangedListener.onAddItem(position, newItem);
        }
        displayItemList.add(position, newItem);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void deleteItem(int position)
    {
        if (position>=0 && position<displayItemList.size())
        {
            T deleteItem = displayItemList.get(position);
            if (onItemChangedListener != null)
            {
                onItemChangedListener.onDelete(position, deleteItem);
            }
            displayItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    @Override
    public AbsRecycleViewHolderController.InnerRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        try {
            AbsRecycleViewHolderController controller = viewHolderControllerList.get(viewType).newInstance();
            return controller.inflate(context, parent);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(AbsRecycleViewHolderController.InnerRecyclerViewViewHolder innerRecyclerViewViewHolder, int position)
    {
        getItem(position).onShow(context,innerRecyclerViewViewHolder.recycleViewHolderController,position, this);
    }


    @Override
    @SuppressWarnings("unchecked")
    public int getItemViewType(int position)
    {
        IRecycleViewDisplayItem displayListItem = getItem(position);
        Class<? extends AbsRecycleViewHolderController> viewHolderController = getViewHolderController(displayListItem);
        return viewHolderControllerList.indexOf(viewHolderController);
    }

    /**
     * 先从缓存的Map中查找对应的ViewHolderController类型，如果没有则
     * 通过反射获取{@link IRecycleViewDisplayItem}上的泛型类{@link IRecycleViewDisplayItem <T>}的具体Class对象
     * @param displayListItem
     * @return
     */
    @SuppressWarnings("unchecked")
    private Class<? extends AbsRecycleViewHolderController> getViewHolderController(IRecycleViewDisplayItem displayListItem) {

        Class<? extends AbsRecycleViewHolderController> viewHolderController =  displayAndViewHolderMap.get(displayListItem.getClass());
        if (viewHolderController == null) {
            Type[] genericInterfaces = displayListItem.getClass().getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {

                if (genericInterface instanceof ParameterizedType) {

                    Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                    for (Type genericType : genericTypes) {

                        if(genericType instanceof Class){
                            if (AbsRecycleViewHolderController.class.isAssignableFrom((Class<?>) genericType)) {

                                viewHolderController = (Class<? extends AbsRecycleViewHolderController>) genericType;
                                displayAndViewHolderMap.put(displayListItem.getClass(), viewHolderController);

                                if (!viewHolderControllerList.contains(viewHolderController)) {
                                    viewHolderControllerList.add(viewHolderController);
                                }
                            }
                        }

                    }
                }
            }
        }
        return viewHolderController;
    }

    @Override
    public int getItemCount()
    {
        return displayItemList == null ? 0 : displayItemList.size();
    }


    public interface OnItemChangedListener{
        void onAddItem(int position, IRecycleViewDisplayItem newItem);

        void onDelete(int position, IRecycleViewDisplayItem deleteItem);
    }
}
