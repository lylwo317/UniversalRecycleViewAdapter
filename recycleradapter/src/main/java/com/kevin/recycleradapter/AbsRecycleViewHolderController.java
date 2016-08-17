package com.kevin.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by XieJiaHua on 2016/8/11.
 */
public abstract class AbsRecycleViewHolderController
{

    private Context context;
    public View rootView;

    public AbsRecycleViewHolderController()
    {

    }

    /**
     * 创建布局
     * @param context
     * @param parent
     * @return
     */
    public InnerRecyclerViewViewHolder inflate(Context context, ViewGroup parent)
    {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getLayoutId(), parent, false);
        initView(view);
        rootView = view;
        return new InnerRecyclerViewViewHolder(view,this);
    }

    /**
     * 在这里做findViewById操作
     * @param view inflate的view
     */
    protected abstract void initView(View view);

    /**
     * 返回该Item对应的layout布局，
     * @return 返回R.layout
     */
    protected abstract int getLayoutId();

    public static class InnerRecyclerViewViewHolder extends RecyclerView.ViewHolder{

        public AbsRecycleViewHolderController recycleViewHolderController;

        public InnerRecyclerViewViewHolder(View itemView, AbsRecycleViewHolderController factory)
        {
            super(itemView);
            recycleViewHolderController = factory;
        }
    }
}
