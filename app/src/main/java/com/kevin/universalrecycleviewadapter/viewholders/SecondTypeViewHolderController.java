package com.kevin.universalrecycleviewadapter.viewholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.kevin.recycleradapter.AbsRecyclerViewHolderController;
import com.kevin.universalrecycleviewadapter.R;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SecondTypeViewHolderController extends AbsRecyclerViewHolderController
{

    public CardView cardlistItem;
    public TextView textView;
    public TextView secondTypeItemName;

    @Override
    protected void initView(View rootView) {

        cardlistItem = (CardView) rootView.findViewById(R.id.cardlist_item);
        textView = (TextView) rootView.findViewById(R.id.textView);
        secondTypeItemName = (TextView) rootView.findViewById(R.id.second_type_item_name);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.second_type_list_item;
    }
}
