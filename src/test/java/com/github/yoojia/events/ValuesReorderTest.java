package com.github.yoojia.events;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author Yoojia Chen (yoojia.chen@gmail.com)
 * @since 2.0
 */
public class ValuesReorderTest {

    final static Class<?>[] types = new Class<?>[]{String.class, Integer.class, Integer.class, Double.class, Double.class};

    @Test
    public void testSameOrder(){
        Object[] values = new Object[]{"str", 1, 2, 3.0, 4.0};
        Object[] ordered = MethodHandler.reorder(types, new PayloadEvent("any", values));
        Assert.assertEquals("str", ordered[0]);
        Assert.assertEquals(1, ordered[1]);
        Assert.assertEquals(2, ordered[2]);
        Assert.assertEquals(3.0, ordered[3]);
        Assert.assertEquals(4.0, ordered[4]);
    }

    @Test
    public void testDiffOrder0(){
        Object[] values = new Object[]{"str", 3.0, 4.0, 1, 2};
        Object[] ordered = MethodHandler.reorder(types, new PayloadEvent("any", values));
        Assert.assertEquals("str", ordered[0]);
        Assert.assertEquals(1, ordered[1]);
        Assert.assertEquals(2, ordered[2]);
        Assert.assertEquals(3.0, ordered[3]);
        Assert.assertEquals(4.0, ordered[4]);
    }

    @Test
    public void testDiffOrder1(){
        Object[] values = new Object[]{3.0, "str", 4.0, 1, 2};
        Object[] ordered = MethodHandler.reorder(types, new PayloadEvent("any", values));
        Assert.assertEquals("str", ordered[0]);
        Assert.assertEquals(1, ordered[1]);
        Assert.assertEquals(2, ordered[2]);
        Assert.assertEquals(3.0, ordered[3]);
        Assert.assertEquals(4.0, ordered[4]);
    }

    @Test
    public void testDiffOrder2(){
        Object[] values = new Object[]{1, 3.0, "str", 4.0, 2};
        Object[] ordered = MethodHandler.reorder(types, new PayloadEvent("any", values));
        Assert.assertEquals("str", ordered[0]);
        Assert.assertEquals(1, ordered[1]);
        Assert.assertEquals(2, ordered[2]);
        Assert.assertEquals(3.0, ordered[3]);
        Assert.assertEquals(4.0, ordered[4]);
    }

    @Test
    public void testDiffOrder3(){
        Object[] values = new Object[]{1, 3.0, "str", 2, 4.0};
        Object[] ordered = MethodHandler.reorder(types, new PayloadEvent("any", values));
        Assert.assertEquals("str", ordered[0]);
        Assert.assertEquals(1, ordered[1]);
        Assert.assertEquals(2, ordered[2]);
        Assert.assertEquals(3.0, ordered[3]);
        Assert.assertEquals(4.0, ordered[4]);
    }
}
