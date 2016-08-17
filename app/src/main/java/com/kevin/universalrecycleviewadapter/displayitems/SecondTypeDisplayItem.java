package com.kevin.universalrecycleviewadapter.displayitems;

import android.content.Context;

import com.kevin.recycleradapter.GeneralRecyclerViewAdapter;
import com.kevin.recycleradapter.IRecycleViewDisplayListItem;
import com.kevin.universalrecycleviewadapter.viewholders.SecondTypeViewHolderController;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SecondTypeDisplayItem implements IRecycleViewDisplayListItem<SecondTypeViewHolderController, String> {

    private String data;

    @Override
    public void onShow(Context context, SecondTypeViewHolderController viewHolderController, int position, GeneralRecyclerViewAdapter generalRecyclerViewAdapter) {
        viewHolderController.secondTypeItemName.setText(data);
    }

    @Override
    public String getDisplayItemId() {
        return null;
    }

    @Override
    public void setDisplayData(String o) {
        data = o;
    }

    @Override
    public String getDisplayData() {
        return data;
    }
}
