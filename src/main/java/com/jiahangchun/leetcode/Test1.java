package com.jiahangchun.leetcode;

import org.springframework.util.Assert;

import java.util.*;

public class Test1 {
    public int[] twoSum(int[] nums, int target) {
        int[] results = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    results[0] = i;
                    results[1] = j;
                    return results;
                }
            }
        }
        return results;
    }

    public static void main(String[] args) {
//        int[] nums=new int[]{2, 7, 11, 15};
//        int target=9;
//        int[] result=new Test1().twoSum(nums,target);
//        System.out.println(result[0]+" "+result[1]);

        int[] initValue = new int[]{2, 6, 1, 5,0,11,55,4,7,2, 6, 1, 5,0,11,55,4,7,2, 6, 1, 5,0,11,55,4,7,2, 6, 1, 5,0,11,55,4,7,2, 6, 1, 5,0,11,55,4,7,111,222,333,4444,5555,6666};
//        Arrays.sort(initValue);
//        insert_sort(initValue);
//        quick_sort(initValue,0,initValue.length-1);
        Arrays.sort(initValue);
//        int[] tmps=new int[9];
//        meger_sort(initValue,0,initValue.length-1,tmps);
        System.out.println(Arrays.toString(initValue));

//        System.out.println(Arrays.toString(initValue));
//
//        List<Integer> list = new ArrayList<>();
//        list.add(3);
//        list.add(1);
//        list.add(2);
//
//        Comparator<Integer> comparator = new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1 - o2;
//            }
//        };
//        Collections.sort(list, comparator);
//        list.stream().forEach(key -> {
//            System.out.println(key);
//        });
//
//
//        int[] arrays = new int[]{1, 2, 3};
//        Arrays.sort(arrays);
    }


    /**
     * 快速排序
     * @param args
     */
    public static void quick_sort(int[] args,int orignLeftIndex,int originRightIndex){
        //args check ...

        if(originRightIndex<0 || orignLeftIndex<0){
            return;
        }
        int firstValue=args[orignLeftIndex];
        int leftIndex=orignLeftIndex,rightIndex=originRightIndex;
        if(originRightIndex<=orignLeftIndex){
            return;
        }
        while(leftIndex!=rightIndex) {
              while(leftIndex<rightIndex && args[rightIndex]>firstValue){
                  rightIndex--;
              }
              if(leftIndex<rightIndex){
                  args[leftIndex]=args[rightIndex];
              }

              while(leftIndex<rightIndex && args[leftIndex]<firstValue){
                  leftIndex++;
              }
              if(leftIndex<rightIndex){
                  args[rightIndex]=args[leftIndex];
              }
        }

        args[leftIndex]=firstValue;
        quick_sort(args,orignLeftIndex,leftIndex-1);
        quick_sort(args,leftIndex+1,originRightIndex);
    }


    /**
     * 简单插入排序实现
     *
     * @param args
     */
    public static void insert_sort(int[] args) {
        if (args == null || args.length <= 0) {
            return;
        }
        for (int i = 1; i < args.length; i++) {
            int q = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (args[i] < args[j]) {
                    break;
                } else {
                    q = j;
                }
            }
            if (q < 0) {
                continue;
            }
            int tmp = args[i];
            for (int k = i; k > q; k--) {
                args[k] = args[k - 1];
            }
            args[q] = tmp;
        }
    }

    /**
     * 归并排序
     */
    public static void meger_sort(int[] args,int offset,int end,int[] tmps){
        if(offset<end) {
            int mid = (end + offset) / 2;
            meger_sort(args, offset, mid, tmps);
            meger_sort(args, mid + 1, end, tmps);
            merge(args, offset,  mid, end,tmps);
        }
    }

    private static void merge(int[] arr,int left,int mid,int right,int[] temp){
        int i = left;//左序列指针
        int j = mid+1;//右序列指针
        int t = 0;//临时数组指针
        while (i<=mid && j<=right){
            if(arr[i]<=arr[j]){
                temp[t++] = arr[i++];
            }else {
                temp[t++] = arr[j++];
            }
        }
        while(i<=mid){//将左边剩余元素填充进temp中
            temp[t++] = arr[i++];
        }
        while(j<=right){//将右序列剩余元素填充进temp中
            temp[t++] = arr[j++];
        }
        t = 0;
        //将temp中的元素全部拷贝到原数组中
        while(left <= right){
            arr[left++] = temp[t++];
        }
    }



}
