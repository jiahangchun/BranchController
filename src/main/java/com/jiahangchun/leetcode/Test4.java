package com.jiahangchun.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test4 {

    public static void main(String[] args) {
        String s = "ccc";

        System.out.println(new Test4().longestPalindrome(s));
    }

    public String longestPalindrome(String s) {
        HashSet<String> set = new HashSet<String>();
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = longest(s, i, i);
            int len2 = longest(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        if (start < 0 || end + 1 > s.length()) {
            return "";
        }

        return s.substring(start, end + 1);
    }

    private int longest(String s, int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex >= s.length()) {
            return 0;
        }
        while (startIndex >= 0 && endIndex < s.length() && s.charAt(startIndex) == s.charAt(endIndex)) {
            startIndex--;
            endIndex++;
        }

        return endIndex - startIndex - 1;
    }
}
