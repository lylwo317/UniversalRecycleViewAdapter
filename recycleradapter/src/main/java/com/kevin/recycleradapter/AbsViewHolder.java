package com.kevin.recycleradapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/9/7.
 */
public class AbsViewHolder extends RecyclerView.ViewHolder{

    public AbsViewHolder(View itemView) {
        super(itemView);
    }

    public AbsViewHolder(ViewGroup parent, int resId) {
        super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
    }
}
