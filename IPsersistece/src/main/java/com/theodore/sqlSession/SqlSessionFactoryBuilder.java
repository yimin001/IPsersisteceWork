package com.theodore.sqlSession;


import com.theodore.config.XMLConfigBuilder;
import com.theodore.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {


    public SqlSessionFactory build (InputStream inputStream) throws DocumentException, PropertyVetoException {
        // 使用dom4j 解析配置文件 ，将解析出来的内容封装在Configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);
        // 创建SqlSessionFactory
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
