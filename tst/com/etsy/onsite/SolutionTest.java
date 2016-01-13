package com.etsy.onsite;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SolutionTest {

    private Solution solution;

    @Before
    public void before() {
        solution = new Solution();
    }

    @Test
    public void test_III() {
        assertEquals("III", solution.int2roman(3));
    }

    @Test
    public void test_IV() {
        assertEquals("IV", solution.int2roman(4));
    }

    @Test
    public void test_X() {
        assertEquals("X", solution.int2roman(10));
    }

    @Test
    public void test_XIV() {
        assertEquals("XIV", solution.int2roman(14));
    }

    @Test
    public void test_LXVII() {
        assertEquals("LXVII", solution.int2roman(67));
    }

    @Test
    public void test_XCIX() {
        assertEquals("XCIX", solution.int2roman(99));
    }
}
