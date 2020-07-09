package com.xiaxinyu.relection;

import org.junit.Test;

import java.sql.Struct;

public class ClassLoaderTest {

    @Test
    public void testString() throws ClassNotFoundException {
        CustomClassLoader loader = new CustomClassLoader();
        Class stringClass = loader.loadClass("java.lang.String", true);
        System.out.println(stringClass);
    }
}
