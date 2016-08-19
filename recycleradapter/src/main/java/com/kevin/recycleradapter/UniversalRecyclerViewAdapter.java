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
 * 通用的RecyclerViewAdapter。
 * 主要获取并维护{@link IRecycleViewDisplayItem}与{@link AbsRecyclerViewHolderController}之间的关系映射，从而不用再去自己维护每种布局的对应关系
 * 用户可以动态加入不同种类的Item。还可以在此基础上通过继承实现其它功能，例如加载更多{@link com.kevin.recycleradapter.loadmore.LoadMoreRecyclerViewAdapter}
 * @author  XieJiaHua create on 2016/8/11.(lylwo317@gmail.com)
 */
public class UniversalRecyclerViewAdapter<T extends IRecycleViewDisplayItem> extends RecyclerView.Adapter<AbsRecyclerViewHolderController.InnerRecyclerViewViewHolder> {
    private Context context;
    protected List<T> displayItemList;

    private Map<Class<? extends IRecycleViewDisplayItem>, Class<? extends AbsRecyclerViewHolderController>> displayAndViewHolderMap = new HashMap<>();

    private List<Class<? extends AbsRecyclerViewHolderController>> viewHolderControllerList = new ArrayList<>();

    /**
     * 设置Item被删除或添加的监听器
     * @param onItemChangedListener 绑定的监听器
     */
    public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
        this.onItemChangedListener = onItemChangedListener;
    }

    private OnItemChangedListener onItemChangedListener = null;

    /**
     * 获取对应position的displayItem
     * @param position 想要获取的displayItem的index
     * @return 检索到的displayItem
     */
    protected T getDisplayItem(int position)
    {
        if (displayItemList != null)
        {
            return displayItemList.get(position);
        }else
        {
            return null;
        }
    }

    public UniversalRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public UniversalRecyclerViewAdapter(Context context, List<T> displayItemList) {
        this.displayItemList = displayItemList;
        this.context = context;
    }

    /**
     * 绑定数据集合到Adapter
     * @param list 需要绑定的数据集合
     */
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

    /**
     * 添加数据集合到列表尾部
     * @param list 需要添加的数据集合
     */
    public void addList(List<T> list) {
        if (list != null && list.size() != 0) {
            if (displayItemList == null) {
                displayItemList = new ArrayList<>();
            }
            int position = getItemCount() - 1;
            displayItemList.addAll(list);
            notifyItemInserted(position);
        }
    }

    /**
     * 添加一条Item到指定位置，越界的直接返回，不会添加进去
     * @param position 添加的位置
     * @param newItem 添加的Item
     */
    public void addItem(int position, T newItem) {
        if (displayItemList == null) {
            displayItemList = new ArrayList<>();
        }

        if (position > displayItemList.size() || position < 0) {
            return;
        }
        if (onItemChangedListener != null) {
            onItemChangedListener.onAddItem(position, newItem);
        }
        displayItemList.add(position, newItem);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    /**
     * 从指定位置删除一条Item
     * @param position 需要删除的Item所在的位置
     */
    public void deleteItem(int position) {
        if (position >= 0 && position < displayItemList.size()) {
            T deleteItem = displayItemList.get(position);
            if (onItemChangedListener != null) {
                onItemChangedListener.onDelete(position, deleteItem);
            }
            displayItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    @Override
    public AbsRecyclerViewHolderController.InnerRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        try {
            AbsRecyclerViewHolderController controller = viewHolderControllerList.get(viewType).newInstance();
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
    public void onBindViewHolder(AbsRecyclerViewHolderController.InnerRecyclerViewViewHolder innerRecyclerViewViewHolder, int position) {
        getDisplayItem(position).onShow(context, innerRecyclerViewViewHolder.recycleViewHolderController, position, this);
    }


    @Override
    @SuppressWarnings("unchecked")
    public int getItemViewType(int position) {
        IRecycleViewDisplayItem displayListItem = getDisplayItem(position);
        Class<? extends AbsRecyclerViewHolderController> viewHolderController = getViewHolderCtrlByDisplayItem(displayListItem);
        return viewHolderControllerList.indexOf(viewHolderController);
    }

    /**
     * 先从{@link #displayAndViewHolderMap}缓存中查找<code>displayItem</code>对应的ViewHolderController类型，如果没有则
     * 通过反射获取{@link IRecycleViewDisplayItem}上的泛型类{@link IRecycleViewDisplayItem<T>}的具体Class对象,并加入到缓存中，下次不用
     * 再通过反射获取
     * @param displayItem
     * @return
     */
    @SuppressWarnings("unchecked")
    private Class<? extends AbsRecyclerViewHolderController> getViewHolderCtrlByDisplayItem(IRecycleViewDisplayItem displayItem) {

        Class<? extends AbsRecyclerViewHolderController> viewHolderController = displayAndViewHolderMap.get(displayItem.getClass());
        if (viewHolderController == null) {
            Type[] genericInterfaces = displayItem.getClass().getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {

                if (genericInterface instanceof ParameterizedType) {

                    Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                    for (Type genericType : genericTypes) {

                        if (genericType instanceof Class) {
                            if (AbsRecyclerViewHolderController.class.isAssignableFrom((Class<?>) genericType)) {

                                viewHolderController = (Class<? extends AbsRecyclerViewHolderController>) genericType;
                                displayAndViewHolderMap.put(displayItem.getClass(), viewHolderController);

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
    public int getItemCount() {
        return displayItemList == null ? 0 : displayItemList.size();
    }


    /**
     * 监听删除和添加Item操作
     */
    public interface OnItemChangedListener {
        void onAddItem(int position, IRecycleViewDisplayItem newItem);

        void onDelete(int position, IRecycleViewDisplayItem deleteItem);
    }
}
