package com.theodore.config;

import com.theodore.enums.SqlQueryType;
import com.theodore.pojo.Configuration;
import com.theodore.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuild {

    private Configuration configuration;
    public XMLMapperBuild(Configuration configuration){
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        // 根标签mapper
        Element rootElement = document.getRootElement();
        // 获取namespace
        String namespace = rootElement.attributeValue("namespace");

        // 获取select
        List<Element> selectSqlList = rootElement.selectNodes("//select");
        for (Element element : selectSqlList) {
            createMappedStatement(element, namespace, SqlQueryType.SELECT);
        }
        // 获取delete
        List<Element> deleteSqlList = rootElement.selectNodes("//delete ");
        for (Element element : deleteSqlList) {
            createMappedStatement(element, namespace, SqlQueryType.DELETE);
        }
        // 获取insert
        List<Element> insertSqlList = rootElement.selectNodes("//insert");
        for (Element element : insertSqlList) {
            createMappedStatement(element, namespace, SqlQueryType.INSERT);
        }
        // 获取update
        List<Element> updateSqlList = rootElement.selectNodes("//update ");
        for (Element element : updateSqlList) {
            createMappedStatement(element, namespace, SqlQueryType.UPDATE);
        }

    }

    public void createMappedStatement(Element element, String namespace, SqlQueryType sqlQueryType){
        MappedStatement mappedStatement = new MappedStatement();
        //
        mappedStatement.setSqlQueryType(sqlQueryType);
        // id
        String id = element.attributeValue("id");
        //resultType
        String resultType = element.attributeValue("resultType");
        // paramType
        String paramterType = element.attributeValue("paramterType");
        String sqlText = element.getTextTrim();
        mappedStatement.setId(id);
        mappedStatement.setResultType(resultType);
        mappedStatement.setParamType(paramterType);
        mappedStatement.setSql(sqlText);
        configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
    }
}
