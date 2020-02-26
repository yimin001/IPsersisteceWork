package com.theodore.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.theodore.io.Resources;
import com.theodore.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }

    /**
     * 该方法就是使用dom4j 将配置文件进行解析，封装configuration中
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        //拿到根对象 configuration
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("user"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);

        //mapper.xml的解析 拿到路径-- 字节输入流--dom4进行解析

        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String mapperMath = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperMath);
            XMLMapperBuild xmlMapperBuild = new XMLMapperBuild(configuration);
            xmlMapperBuild.parse(resourceAsStream);
        }

        return configuration;
    }
}
