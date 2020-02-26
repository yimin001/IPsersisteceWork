package com.theodore.sqlSession;

import com.theodore.config.BoundSql;
import com.theodore.enums.SqlQueryType;
import com.theodore.pojo.Configuration;
import com.theodore.pojo.MappedStatement;
import com.theodore.utils.GenericTokenParser;
import com.theodore.utils.ParameterMapping;
import com.theodore.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        //2. 获取sql语句, 转换sql语句 对#{}转换，并对参数赋值
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());


        //4.设置参数
        // 获取到了参数的全路径
        String paramType = mappedStatement.getParamType();
        //
        Class<?> paramterClass = getClassType(paramType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            // 使用反射
            Field declaredField = paramterClass.getDeclaredField(content);
            // 暴力访问
            declaredField.setAccessible(Boolean.TRUE);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }

        //5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        //6.封装返回结果集
        String resultType = mappedStatement.getResultType();
        Class<?> classType = getClassType(resultType);
        List<Object> objects = new ArrayList<>();
        while (resultSet.next()){
            Object o = classType.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                // 字段值
                Object value = resultSet.getObject(columnName);

                //使用反省或者内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, classType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);

            }
            objects.add(o);
        }
        return (List<E>) objects;
    }

    @Override
    public int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
      return update(configuration, mappedStatement, params);
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception{
        //1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        //2. 获取sql语句, 转换sql语句 对#{}转换，并对参数赋值
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //4.设置参数
        // 获取到了参数的全路径
        String paramType = mappedStatement.getParamType();
        //
        Class<?> paramterClass = getClassType(paramType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            // 使用反射
            Field declaredField = paramterClass.getDeclaredField(content);
            // 暴力访问
            declaredField.setAccessible(Boolean.TRUE);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }

        //5.执行sql
        int update = preparedStatement.executeUpdate();

        return update;
    }

    @Override
    public int insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception{
        return update(configuration, mappedStatement, params);
    }

    private Class<?> getClassType(String paramType) throws ClassNotFoundException {
        if (paramType != null){
            Class<?> aClass = Class.forName(paramType);
            return aClass;
        }
        return null;
    }

    /**
     * 完成1.对#{}使用？代替，2.解析出#{}里面的值进行存储
     * @return
     */
    public BoundSql getBoundSql(String sql){
        // 标记处理类
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }
}
