package com.kevin.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} 控制器。主要负责ViewHolder的创建
 * @author XieJiaHua create on 2016/8/11.(lylwo317@gmail.com)
 */
public abstract class AbsRecyclerViewHolderController
{

    private Context context;
    private View rootView;

    public AbsRecyclerViewHolderController() {

    }

    /**
     * 返回ItemView
     * @return {@link android.support.v7.widget.RecyclerView.ViewHolder#itemView}
     */
    public View getItemView()
    {
        return rootView;
    }

    /**
     * 返回Context对象
     * @return context
     */
    public Context getContext()
    {
        return context;
    }

    /**
     * {@link UniversalRecyclerViewAdapter}会调用来创建{@link android.support.v7.widget.RecyclerView.ViewHolder}对象
     *
     * @param context
     * @param parent 这里是RecyclerView
     * @return
     */
    public InnerRecyclerViewViewHolder inflate(Context context, ViewGroup parent) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getLayoutId(), parent, false);
        rootView = view;
        initView(view);
        return new InnerRecyclerViewViewHolder(view, this);
    }

    /**
     * 在这里做findViewById操作
     *
     * @param rootView {@link android.support.v7.widget.RecyclerView.ViewHolder#itemView}
     */
    protected abstract void initView(View rootView);

    /**
     * 返回该Item对应的layout布局
     *
     * @return 返回R.layout
     */
    protected abstract int getLayoutId();

    public static class InnerRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public AbsRecyclerViewHolderController recycleViewHolderController;

        public InnerRecyclerViewViewHolder(View itemView, AbsRecyclerViewHolderController controller) {
            super(itemView);
            recycleViewHolderController = controller;
        }
    }
}
