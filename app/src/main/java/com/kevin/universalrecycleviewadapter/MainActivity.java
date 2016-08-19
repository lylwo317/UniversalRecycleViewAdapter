package com.kevin.universalrecycleviewadapter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kevin.recycleradapter.ConvertObjectFunctional;
import com.kevin.recycleradapter.UniversalRecyclerViewAdapter;
import com.kevin.recycleradapter.IRecycleViewDisplayItem;
import com.kevin.recycleradapter.loadmore.DefaultLoadMoreDisplayItem;
import com.kevin.recycleradapter.loadmore.LoadMoreRecyclerViewAdapter;
import com.kevin.recycleradapter.loadmore.LoadMoreState;
import com.kevin.universalrecycleviewadapter.displayitems.FirstTypeDisplayItem;
import com.kevin.universalrecycleviewadapter.displayitems.SecondTypeDisplayItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    //private String mItemData = "Lorem Ipsum is";

    private String mItemData = "Lorem Ipsum is simply dummy text of the printing and "
            + "typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and";
    private LoadMoreRecyclerViewAdapter<IRecycleViewDisplayItem> mAdapter;

    private int count = 1;

    private int loadTime = 0;

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


        List<IRecycleViewDisplayItem> displayListItems = ConvertObjectFunctional
                .convert(list, new ConvertObjectFunctional.Processor<String, IRecycleViewDisplayItem>()
                {
                    @Override
                    @SuppressWarnings("unchecked")
                    public IRecycleViewDisplayItem process(String currentFrom, int srcIndex, String preFrom)
                    {

                        IRecycleViewDisplayItem listItem;

                        if (srcIndex % 3 == 0)
                        {
                            listItem = new FirstTypeDisplayItem();
                            listItem.setDisplayData(currentFrom);
                        }
                        else
                        {
                            listItem = new SecondTypeDisplayItem();
                            listItem.setDisplayData(currentFrom);
                        }

                        return listItem;

                    }
                });


        mAdapter = new LoadMoreRecyclerViewAdapter<>(getBaseContext());
        mAdapter.setLoadMoreListener(new LoadMoreRecyclerViewAdapter.LoadMoreListener()
        {
            @Override
            public void onLoadMore(final IRecycleViewDisplayItem lastItem)
            {
                new AsyncTask<Object, Object, List<IRecycleViewDisplayItem>>()
                {

                    @Override
                    protected List<IRecycleViewDisplayItem> doInBackground(Object... params)
                    {

                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        return loadMoreData();
                    }

                    @Override
                    protected void onPostExecute(List<IRecycleViewDisplayItem> o)
                    {
                        if (loadTime==0)
                        {
                            mAdapter.addList(o);//加载成功
                            mAdapter.doneLoadMoreSuccess();
                        }else if (loadTime == 1)
                        {
                            mAdapter.doneLoadMoreFailed(LoadMoreState.FAILED);//failed
                        }
                        else if (loadTime == 2)
                        {
                            mAdapter.addList(o);
                            mAdapter.disableLoadMore();//关闭加载更多
                            mAdapter.doneLoadMoreSuccess(); //加载成功
                        }
                        loadTime++;
                    }
                }.execute();
            }
        });

        mAdapter.setOnItemChangedListener(new UniversalRecyclerViewAdapter.OnItemChangedListener()
        {
            @Override
            public void onAddItem(int position, IRecycleViewDisplayItem newItem)
            {

            }

            @Override
            public void onDelete(int position, IRecycleViewDisplayItem deleteItem)
            {

            }
        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.enableLoadMore();
        mAdapter.setList(displayListItems);
    }


    private List<IRecycleViewDisplayItem> loadMoreData()
    {
        List<String> srcList = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            srcList.add(String.valueOf(count));
            count++;
        }

        return ConvertObjectFunctional.convert(srcList, new ConvertObjectFunctional.Processor<String, IRecycleViewDisplayItem>()
        {
            @Override
            @SuppressWarnings("unchecked")
            public IRecycleViewDisplayItem process(String currentFrom, int srcIndex, String preFrom)
            {

                IRecycleViewDisplayItem listItem;

                if (srcIndex % 3 == 0)
                {
                    listItem = new FirstTypeDisplayItem();
                    listItem.setDisplayData(currentFrom);
                }
                else
                {
                    listItem = new SecondTypeDisplayItem();
                    listItem.setDisplayData(currentFrom);
                }

                return listItem;

            }
        });
    }
}
