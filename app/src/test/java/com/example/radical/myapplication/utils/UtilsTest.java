package com.example.radical.myapplication.utils;

import com.example.radical.myapplication.MainActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright (c)
 * Title.
 * <p>
 * Description.
 *
 * @author radical
 * @version 1.0
 * @since 2016-08-15
 */
public class UtilsTest {

    @Test
    public void testGetbuild() throws Exception {
        String string=Utils.getbuild();
        System.out.println(string);
    }
}