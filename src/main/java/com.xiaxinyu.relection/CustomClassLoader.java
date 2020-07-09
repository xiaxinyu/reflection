package com.xiaxinyu.relection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author XIAXINYU3
 * @date 2020.7.9
 */
public class CustomClassLoader extends ClassLoader {

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> klass = super.loadClass(name, resolve);
        return  klass;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            Class result = super.findClass(name);
            byte[] classBytes = null;
            classBytes = loadClassBytes(name);
            Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
            if (cl == null) {
                throw new ClassNotFoundException(name);
            }
            return cl;
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }

    /**
     * Loads and decrypt the class file bytes.
     *
     * @param name the class name
     * @return an array with the class file bytes
     */
    private byte[] loadClassBytes(String name) throws IOException {
        String newName = name.replace(".", File.separator) + ".class";
        InputStream inputStream = super.getParent().getResourceAsStream(name.replace('.', '/') + ".class");
        byte[] bytes = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            //buff用于存放循环读取的临时数据 
            byte[] buff = new byte[1024];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            //in_b为转换之后的结果 
            bytes = swapStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
