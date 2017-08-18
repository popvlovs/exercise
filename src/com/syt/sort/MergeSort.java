package com.syt.sort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Think on 2017/8/12.
 */
public class MergeSort {

    static public <T extends Comparable> void sort(T[] list) {
        if (list == null || list.length < 2) {
            return;
        }

        List<T> array = Arrays.asList(list);
        sort(array, 0, list.length-1);
    }

    static public <T extends Comparable> void sort(List<T> list, int start, int end) {

        // 从中间点开始，将数组对半分开
        int mid = (start + end) >> 1;
        if (mid-start >= 2) {
            sort(list, start, mid);
        }

        if (end-mid-1 >= 2) {
            sort(list, mid+1, end);
        }

        // 制作一份list的拷贝
        List<T> temp = new ArrayList(end - start);
        temp.addAll(list);

        // 归并
        int idx = start;

        int idx1 = start;
        int idx2 = mid+1;
        while (idx1 <= mid && idx2 <= end) {
            T val1 = temp.get(idx1);
            T val2 = temp.get(idx2);

            if (val1.compareTo(val2) < 0) {
                list.set(idx, val1);
                idx1++;
            } else {
                list.set(idx, val2);
                idx2++;
            }
            idx++;
        }

        // 将剩余项补足
        while (idx1 <= mid) {
            T value = temp.get(idx1++);
            list.set(idx++, value);
        }

        while (idx2 <= end) {
            T value = temp.get(idx2++);
            list.set(idx++, value);
        }
    }
}
