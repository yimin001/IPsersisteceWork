package com.theodore.dao;

import com.theodore.pojo.User;

import java.util.List;

public interface UserMapper {

    List<User> selectAll() throws Exception;

    User selectOne(User user) throws Exception;

    void insert(User user) throws Exception;

    void update(User user) throws Exception;

    void delete(User user) throws Exception;



 }
