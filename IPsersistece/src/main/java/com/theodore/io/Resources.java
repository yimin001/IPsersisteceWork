package com.theodore.io;

import java.io.InputStream;

public class Resources {

        // 根据配置文件的路径，讲配置文件信息加载成流的方式，存储在内存中
        public static InputStream getResourceAsStream(String path){
            InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
            return  resourceAsStream;
        }
}
