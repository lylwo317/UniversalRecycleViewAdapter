package com.kevin.recycleradapter;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 将{@code List<From>} 转成 {@code List<To>}
 * Created by Administrator on 2016/8/17.
 */
public class ConvertObjectFunctional
{

    public interface Processor<From, To> {
        /**
         * 主要转换逻辑
         * @param currentFrom 当前From对象
         * @param srcIndex 当前From对象的Index
         * @param preFrom 前一个From对象。有可能为null
         * @return To类型对象
         */
        public To process(From currentFrom, int srcIndex, @Nullable From preFrom);
    }

    /**
     * 转换方法
     * @param sources 源List
     * @param processor 处理器
     * @param <From> 源List中的Item类型
     * @param <To> 目标List中的Item类型
     * @return 目标List
     */
    public static <From, To> List<To> convert(List<From> sources, Processor<From, To> processor) {
        List<To> list = new ArrayList<>();
        if (sources != null && sources.size() > 0) {
            for (int i = 0; i < sources.size(); i++) {
                From temp = sources.get(i);
                From pre = null;
                if (i > 0) {
                    pre = sources.get(i - 1);
                }
                To target = processor.process(temp, i, pre);
                if (target != null) {
                    list.add(target);
                }
            }
        }
        return list;
    }
}
