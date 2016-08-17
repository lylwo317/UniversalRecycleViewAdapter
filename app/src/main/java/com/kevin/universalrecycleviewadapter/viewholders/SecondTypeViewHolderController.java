package com.kevin.universalrecycleviewadapter.viewholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.kevin.recycleradapter.AbsRecycleViewHolderController;
import com.kevin.universalrecycleviewadapter.R;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SecondTypeViewHolderController extends AbsRecycleViewHolderController {

    public CardView cardlistItem;
    public TextView textView;
    public TextView secondTypeItemName;

    @Override
    protected void initView(View view) {

        cardlistItem = (CardView) view.findViewById(R.id.cardlist_item);
        textView = (TextView) view.findViewById(R.id.textView);
        secondTypeItemName = (TextView) view.findViewById(R.id.second_type_item_name);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.second_type_list_item;
    }
}
