package com.gui.projectmanagement.test;

import com.gui.projectmanagement.entity.BasicInformation;

public class TestEqual {
    public static void main(String[] args) {
        BasicInformation bi = new BasicInformation("12", "33", null, "711", "Male") ;
        BasicInformation bi2 = new BasicInformation("12", "33", null, "111", "Male") ;
        System.out.println(bi.equals(bi2));
    }
}
