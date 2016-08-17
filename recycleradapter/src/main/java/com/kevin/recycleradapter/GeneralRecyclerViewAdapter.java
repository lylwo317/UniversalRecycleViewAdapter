package com.kevin.recycleradapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XieJiaHua on 2016/8/11.
 */
public class GeneralRecyclerViewAdapter<T extends IRecycleViewDisplayListItem> extends RecyclerView.Adapter<AbsRecycleViewHolderController.InnerRecyclerViewViewHolder>
{
    private Context context;
    private List<T> displayItemList;

    private Map<Class< ? extends IRecycleViewDisplayListItem>, Class<? extends AbsRecycleViewHolderController>> displayAndViewHolderMap = new HashMap<>();

    private List<Class<? extends AbsRecycleViewHolderController>> viewHolderControllerList = new ArrayList<>();

    private T getItem(int position)
    {
       return displayItemList.get(position);
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

        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        if (list != null && list.size() != 0) {
            if (displayItemList == null) {
                displayItemList = new ArrayList<>();
            }
            displayItemList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addItem(int position,T newItem){
        displayItemList.add(newItem);
        notifyItemRangeChanged(position,displayItemList.size()-position);
    }

    public void delItem(int position){
        displayItemList.remove(position);
        notifyItemRangeChanged(position,displayItemList.size()-position);
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
    public void onBindViewHolder(AbsRecycleViewHolderController.InnerRecyclerViewViewHolder innerRecyclerViewViewHolder, int i)
    {
        displayItemList.get(i).onShow(context,innerRecyclerViewViewHolder.recycleViewHolderController,i, this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getItemViewType(int position)
    {
        IRecycleViewDisplayListItem displayListItem = getItem(position);
        Class<? extends AbsRecycleViewHolderController> viewHolderController = getViewHolderController(displayListItem);
        return viewHolderControllerList.indexOf(viewHolderController);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Class<? extends AbsRecycleViewHolderController> getViewHolderController(IRecycleViewDisplayListItem displayListItem) {

        Class<? extends AbsRecycleViewHolderController> viewHolderController =  displayAndViewHolderMap.get(displayListItem.getClass());
        if (viewHolderController == null) {
            Type[] genericInterfaces = displayListItem.getClass().getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType) {
                    Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                    for (Type genericType : genericTypes) {

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
        return viewHolderController;
    }

    @Override
    public int getItemCount()
    {
        return displayItemList == null ? 0 : displayItemList.size();
    }
}
