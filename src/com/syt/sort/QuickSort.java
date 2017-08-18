package com.syt.sort;

/**
 * Created by Think on 2017/8/12.
 */
public class QuickSort<T extends Comparable> {

    public void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        sort(array, 0, array.length-1);
    }

    public void sort(T[] array, int start, int end) {

        T pivot = array[start];

        int i = start+1;
        int j = end;
        while (i != j) {
            while (array[j].compareTo(pivot) > 0 && i < j) {
                j--;
            }

            while (array[i].compareTo(pivot) < 0 && i < j) {
                i++;
            }

            if (i != j) {
                swap(array, i, j);
            }
        }

        swap(array, start, i);
        if (i-1 > start) {
            sort(array, start, i-1);
        }
        if (end > i+1) {
            sort(array, i+1, end);
        }
    }

    private void swap(T[] array, int i, int j) {
        T temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }
}
