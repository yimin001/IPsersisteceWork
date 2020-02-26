package com.theodore.sqlSession;

import com.theodore.pojo.Configuration;
import com.theodore.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }


    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);
        return (List<E>)list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1){
            return (T)objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }

    @Override
    public void insert(String statementId, Object... params) throws Exception{
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        simpleExecutor.insert(configuration, mappedStatement, params);

    }

    @Override
    public void delete(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        simpleExecutor.delete(configuration, mappedStatement, params);
    }

    @Override
    public void update(String statementId, Object... params) throws Exception{
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        simpleExecutor.update(configuration, mappedStatement, params);
    }

    /**
     * 使用jdK动态代理
     * @param mapperClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) {

        // 使用jdk

        Object o = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // proxy 代理对象引用， method代理对象方法， args-传递参数
                // 底层还是执行jdbc代码，根据不同情况调用不同方法
                // 准备参数， statementId, params
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;

                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                // todo
                switch (mappedStatement.getSqlQueryType()){
                    case DELETE :
                        delete(statementId,args); break;
                    case INSERT:
                        insert(statementId, args); break;
                    case UPDATE:
                        update(statementId, args); break;
                    case SELECT:
                        Type genericReturnType = method.getGenericReturnType();
                        // 判断返回参数是否进行了泛型化
                        if (genericReturnType instanceof ParameterizedType){
                            List<Object> objects = selectList(statementId, args);
                            return objects;
                        }
                        Object o1 = selectOne(statementId, args);
                        return o1;
                    default:break;
                }
            return null;

            }
        });
        return (T)o;
    }
}
