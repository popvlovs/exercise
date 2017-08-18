package com.syt.sort;

/**
 * Created by Think on 2017/8/11.
 */


public class DivisionSearch {
    static public void main(String[] args) {
        String list[] = {"1", "2", "3", "4", "5", "6", "7"};
        DividSearcher<String> ds = new DividSearcher<String>(list);
        int idx = ds.search("4");
        System.out.print(idx);
    }

    static private class DividSearcher <T extends Comparable>{
        private T[] list;

        public DividSearcher(T[] list) {
            this.list = list;
        }

        public int search(T val) {
            return search(val, 0, list.length - 1);
        }

        private int search(T val, int start, int end) {
            if (start > end) {
                return -1;
            }

            int mid = (end + start) >> 1;
            T midVal = list[mid];

            if (midVal == val) {
                return mid;
            } else if (midVal.compareTo(val) < 0) {
                return search(val, mid + 1, end);
            } else if (midVal.compareTo(val) > 0) {
                return search(val, start, mid - 1);
            }

            return -1;
        }
    }
}
