package com.theodore.sqlSession;

import java.util.List;

public interface SqlSession {

     <E> List<E> selectList(String statementId, Object... params) throws Exception;

     <T> T selectOne(String statementId, Object... params) throws Exception;

     void insert(String statementId, Object... params) throws Exception;

     void delete(String statementId, Object... params) throws Exception;

     void update(String statementId, Object... params) throws Exception;

     //为dao实现代理实现类
     <T> T getMapper(Class<?> mapperClass);

}
