package com.kevin.universalrecycleviewadapter.displayitems;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.kevin.recycleradapter.GeneralRecyclerViewAdapter;
import com.kevin.recycleradapter.IRecycleViewDisplayItem;
import com.kevin.universalrecycleviewadapter.viewholders.FirstTypeViewHolderController;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FirstTypeDisplayItem implements IRecycleViewDisplayItem<FirstTypeViewHolderController,String>
{

    private String data;

    @Override
    public void onShow(final Context context, FirstTypeViewHolderController viewHolderController, final int position, final GeneralRecyclerViewAdapter generalRecyclerViewAdapter) {
        viewHolderController.firstTypeItemName.setText(data);
        viewHolderController.rootView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                generalRecyclerViewAdapter.deleteItem(position);
            }
        });
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
