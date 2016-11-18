package com.kevin.universalrecycleviewadapter.viewholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.kevin.recycleradapter.AbsRecyclerViewHolderCtrl;
import com.kevin.universalrecycleviewadapter.R;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FirstTypeViewHolderCtrl extends AbsRecyclerViewHolderCtrl
{

    public CardView cardListItem;
    public TextView firstTypeItemName;

    @Override
    protected void initView(View rootView) {
        cardListItem = (CardView) rootView.findViewById(R.id.cardList_item);
        firstTypeItemName = (TextView) rootView.findViewById(R.id.first_type_item_name);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.first_type_list_item;
    }
}
