package com.kevin.universalrecycleviewadapter.displayitems;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.kevin.recycleradapter.UniversalRecyclerViewAdapter;
import com.kevin.recycleradapter.IRecycleViewDisplayItem;
import com.kevin.universalrecycleviewadapter.viewholders.SecondTypeViewHolderController;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SecondTypeDisplayItem implements IRecycleViewDisplayItem<SecondTypeViewHolderController, String>
{

    private String data;

    @Override
    public void onShow(final Context context, SecondTypeViewHolderController viewHolderController, final int position, final UniversalRecyclerViewAdapter universalRecyclerViewAdapter) {
        viewHolderController.secondTypeItemName.setText(data);
        viewHolderController.getItemView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                SecondTypeDisplayItem secondTypeDisplayItem = new SecondTypeDisplayItem();
                secondTypeDisplayItem.setDisplayData(String.valueOf(position));
                universalRecyclerViewAdapter.addItem(position,secondTypeDisplayItem);
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
