package com.kevin.universalrecycleviewadapter.displayitems;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.kevin.recycleradapter.UniversalRecyclerViewAdapter;
import com.kevin.recycleradapter.IRecycleViewDisplayItem;
import com.kevin.universalrecycleviewadapter.viewholders.FirstTypeViewHolderController;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FirstTypeDisplayItem implements IRecycleViewDisplayItem<FirstTypeViewHolderController,String>
{

    private String data;

    @Override
    public void onShow(final Context context, FirstTypeViewHolderController viewHolderController, final int position, final UniversalRecyclerViewAdapter universalRecyclerViewAdapter) {
        viewHolderController.firstTypeItemName.setText(data);
        viewHolderController.getItemView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                universalRecyclerViewAdapter.deleteItem(position);
            }
        });
    }

    @Override
    public void setDisplayData(String displayData) {
        data = displayData;
    }

    @Override
    public String getDisplayData() {
        return data;
    }
}
