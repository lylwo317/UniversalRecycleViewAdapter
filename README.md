# UniversalRecyclerViewAdapter
简化RecyclerAdapter使用

演示视频：

[![Alt text](https://img.youtube.com/vi/maJQoNVcjQc/0.jpg)](https://www.youtube.com/watch?v=maJQoNVcjQc)


```
public class FirstTypeViewHolderController extends AbsRecyclerViewHolderController
{

    public CardView cardlistItem;
    public TextView firstTypeItemName;

    @Override
    protected void initView(View rootView) {
        cardlistItem = (CardView) rootView.findViewById(R.id.cardlist_item);
        firstTypeItemName = (TextView) rootView.findViewById(R.id.first_type_item_name);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.first_type_list_item;
    }
}
```