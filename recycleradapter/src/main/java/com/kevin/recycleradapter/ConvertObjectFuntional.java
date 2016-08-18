package com.kevin.recycleradapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ConvertObjectFuntional {

    public interface Processor<From,To>{
        public To process(From currentFrom,int srcIndex,From preFrom);
    }

    public static <From,To> List<To> convert(List<From> sources, Processor<From,To> processor) {
        List<To> list = new ArrayList<>();
        if (sources!=null && sources.size()>0)
        {
            for(int i = 0; i < sources.size(); i++)
            {
                From temp = sources.get(i);
                From pre = null;
                if (i > 0) {
                    pre = sources.get(i - 1);
                }
                To target = processor.process(temp,i,pre);
                if (target != null)
                {
                    list.add(target);
                }
            }
        }
        return list;
    }
}
