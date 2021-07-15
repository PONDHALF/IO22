package me.pondhalf.plugin;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

public class Debug {

    public static void main(String[] args) {
//        String pattern = "###,###.###";
//        DecimalFormat decimalFormat = new DecimalFormat(pattern);
//
//        String format = decimalFormat.format(1000);
//        System.out.println(format);
        // int a = (int) (Math.random() * 10);
        String a = "16x Grass Block";
        String[] ag = a.split(" ");
        String amount = ag[1].replace("x","");
        System.out.print(amount);

    }

}

