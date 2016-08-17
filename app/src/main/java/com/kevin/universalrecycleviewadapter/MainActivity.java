package com.kevin.universalrecycleviewadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kevin.recycleradapter.ConvertObjectFuntional;
import com.kevin.recycleradapter.GeneralRecyclerViewAdapter;
import com.kevin.recycleradapter.IRecycleViewDisplayListItem;
import com.kevin.universalrecycleviewadapter.displayitems.FirstTypeDisplayItem;
import com.kevin.universalrecycleviewadapter.displayitems.SecondTypeDisplayItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private String mItemData = "Lorem Ipsum is simply dummy text of the printing and "
            + "typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private GeneralRecyclerViewAdapter<IRecycleViewDisplayListItem> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        String[] listItems = mItemData.split(" ");

        final List<String> list = new ArrayList<>();
        Collections.addAll(list, listItems);


        List<IRecycleViewDisplayListItem> displayListItems = ConvertObjectFuntional.convert(list, new ConvertObjectFuntional.Processor<String, IRecycleViewDisplayListItem>() {
            @Override
            @SuppressWarnings("unchecked")
            public IRecycleViewDisplayListItem process(String currentIndexFrom, int srcIndex, String preFrom) {

                IRecycleViewDisplayListItem listItem;

                if (srcIndex % 3 == 0)
                {
                    listItem = new FirstTypeDisplayItem();
                    listItem.setDisplayData(list.get(srcIndex));
                }else
                {
                    listItem = new SecondTypeDisplayItem();
                    listItem.setDisplayData(list.get(srcIndex));
                }

                return listItem;

            }
        });


        mAdapter = new GeneralRecyclerViewAdapter<>(getBaseContext(), displayListItems);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
