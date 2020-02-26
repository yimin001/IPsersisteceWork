package com.theodore.sqlSession;

import com.theodore.pojo.Configuration;
import com.theodore.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    int insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}
