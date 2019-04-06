package com.jiahangchun.leetcode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Test2 {
    public static void main(String[] args) {
        ListNode a1 = new Test2().new ListNode(2),
                b1 = new Test2().new ListNode(4),
                c1 = new Test2().new ListNode(3);
        a1.next = b1;
        b1.next = c1;

        ListNode a2 = new Test2().new ListNode(5),
                b2 = new Test2().new ListNode(6),
                c2 = new Test2().new ListNode(4);
        a2.next = b2;
        b2.next = c2;

        ListNode listNode = new Test2().addTwoNumbers(a1, a2);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        boolean flag = false;
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while(l1 != null || l2 != null){
            int nodeValue = 0;
            if(l1 == null && l2 != null){
                nodeValue = l2.val;
                l2 = l2.next;
            }else if(l2 == null && l1 != null){
                nodeValue = l1.val;
                l1 = l1.next;
            }else {
                nodeValue = l1.val + l2.val;
                l1 = l1.next;
                l2 = l2.next;
            }
            ListNode node;
            if(flag){
                if(nodeValue >= 9){
                    node = new ListNode(nodeValue + 1 - 10);
                    flag = true;
                }else {
                    node = new ListNode(nodeValue + 1);
                    flag = false;
                }
            }else {
                if(nodeValue >= 10){
                    node = new ListNode(nodeValue - 10);
                    flag = true;
                }else {
                    node = new ListNode(nodeValue);
                    flag = false;
                }
            }
            cur.next = node;
            cur = cur.next;
        }
        if(l1 == null && l2 == null){
            if(flag){
                ListNode node = new ListNode(1);
                cur.next = node;
                cur = cur.next;
            }
        }
        return dummy.next;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}

