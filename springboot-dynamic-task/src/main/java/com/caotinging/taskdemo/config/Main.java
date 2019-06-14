package com.caotinging.taskdemo.config;

import java.util.*;

/**
 * @Author: bise
 * @Date: 2019/6/4 17:04
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        int code  = new Double(1).hashCode();
        String str = Integer.toBinaryString(code);
        System.out.println(code);
        System.out.println(str+"----"+str.length());
    }

}
