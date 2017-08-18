package com.syt.sort;

/**
 * Created by Think on 2017/8/11.
 */
public class BubbleSort {
    public static void main(String[] args) {

        Integer list [] = {1, 2, 5, 6, 1, 2, 4};

        QuickSort<Integer> sorter = new QuickSort<>();
        sorter.sort(list);
//        MergeSort.<Integer>sort(list);
//        BubbleSorter sorter = new BubbleSorter(list);
//        sorter.print();
//        sorter.sort();
//        sorter.print();
    }

    private static class BubbleSorter {
        private int [] list;

        public BubbleSorter(int[] list) {
            this.list = list;
        }

        public void print() {
            System.out.print("[");
            for (Integer i : list) {
                System.out.print(i + ", ");
            }
            System.out.println("]");
        }

        public void sort() {
            if (list == null || list.length <= 2) {
                return;
            }
            for (int i=0; i<list.length; ++i){
                for (int j=0; j<list.length-i-1; ++j)
                {
                    if (list[j] > list[j + 1]) {
                        swap(j, j + 1);
                    }
                }
            }
        }

        private void swap(int i, int j) {
            list[i] = list[i] ^ list[j];
            list[j] = list[i] ^ list[j];
            list[i] = list[i] ^ list[j];
        }
    }
}
