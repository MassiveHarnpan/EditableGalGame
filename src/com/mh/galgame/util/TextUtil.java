package com.mh.galgame.util;

import java.util.Stack;

import static com.mh.galgame.util.StringUtil.isEmpty;

public class TextUtil {


    public static String arr2str(String[] strs) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            builder.append(strs[i]);
            if (i!=strs.length-1)
                builder.append(" ");
        }
        return builder.toString();
    }

    public static String nextId(String id) {
        if (id == null || isEmpty(id)) {
            return id;
        }
        Stack<Character> prefix = new Stack<>();
        Stack<Character> number = new Stack<>();
        for (char ch : id.toCharArray()) {
            prefix.push(ch);
        }
        while ((!prefix.isEmpty()) && Character.isDigit(prefix.peek())) {
            number.push(prefix.pop());
        }
        if (number.isEmpty()) {
            return id;
        }
        while ((!number.isEmpty()) && '0' == number.peek()) {
            prefix.push(number.pop());
        }
        if (number.isEmpty()) {
            if ((!prefix.isEmpty()) && prefix.peek() == '0') {
                number.push(prefix.pop());
            } else {
                return id;
            }
        }
        StringBuilder builder = new StringBuilder();
        while (!number.isEmpty()) {
            builder.append(number.pop());
        }
        int next = Integer.valueOf(builder.toString()) + 1;
        builder.delete(0, builder.length());
        while (!prefix.isEmpty()) {
            builder.append(prefix.pop());
        }
        builder.reverse().append(next);
        return builder.toString();
    }

}
