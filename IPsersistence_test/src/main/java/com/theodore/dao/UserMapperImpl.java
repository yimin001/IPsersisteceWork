package com.theodore.dao;

import com.theodore.io.Resources;
import com.theodore.pojo.User;
import com.theodore.sqlSession.SqlSession;
import com.theodore.sqlSession.SqlSessionFactory;
import com.theodore.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class UserMapperImpl implements UserMapper {

    public List<User> selectAll() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();

        List<User> objects = sqlSession.selectList("user.selectAll");
        for (User object : objects) {
            System.out.println(object);
        }
        return objects;
    }

    public User selectOne(User user) throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        user.setId(1);
        user.setUsername("张三");
        User user1 = sqlSession.selectOne("user.selectOne", user);
        System.out.println(user1);
        return user1;
    }

    public void insert(User user) throws Exception {

    }

    public void update(User user) throws Exception {

    }

    public void delete(User user) throws Exception {

    }


}
