package com.jiahangchun.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test3 {

    public static void main(String[] args) {
        String s = "pwddw";
        int longestLength = new Test3().lengthOfLongestSubstring(s);
        System.out.println(longestLength);
    }

    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    public boolean checkIfContainsSameChar(String s, int start, int end) {
        Set<CharSequence> set = new HashSet<>();

        for (int i = start; i < end; i++) {
            if (set.contains(s.subSequence(i, i + 1))) {
                return false;
            }
            set.add(s.subSequence(i, i + 1));
        }

        return true;
    }

}
