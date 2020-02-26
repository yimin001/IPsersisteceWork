package com.theodore.test;

import com.theodore.dao.UserMapper;
import com.theodore.io.Resources;
import com.theodore.pojo.User;
import com.theodore.sqlSession.SqlSession;
import com.theodore.sqlSession.SqlSessionFactory;
import com.theodore.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void testSelect() throws Exception {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
        // proxy 代理对象执行接口里面的任何方法，都是执行invoke方法
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user1 = userMapper.selectOne(user);

        System.out.println(user1);

        List<User> objects = userMapper.selectAll();
        for (User object : objects) {
            System.out.println(object);
        }

    }

    @Test
    public void testInsert() throws Exception {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        User user = new User();
        user.setId(4);
        user.setUsername("李四");
        // proxy 代理对象执行接口里面的任何方法，都是执行invoke方法
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.insert(user);

    }


    @Test
    public void testUpdate() throws Exception {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        User user = new User();
        user.setId(4);
        user.setUsername("王五");
        // proxy 代理对象执行接口里面的任何方法，都是执行invoke方法
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.update(user);

    }

    @Test
    public void testDelete() throws Exception {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        User user = new User();
        user.setId(4);
        user.setUsername("王五");
        // proxy 代理对象执行接口里面的任何方法，都是执行invoke方法
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.delete(user);

    }
}
