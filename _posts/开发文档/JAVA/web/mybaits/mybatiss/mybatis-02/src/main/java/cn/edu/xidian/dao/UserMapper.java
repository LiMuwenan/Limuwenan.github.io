package cn.edu.xidian.dao;

import cn.edu.xidian.domain.User;

import java.util.List;

public interface UserMapper {
    //查询全部用户
    List<User> getUserList();

    //根据用户id查询用户
    User getUserById(int id);

    //插入用户
    void insertUser(User user);

    //删除用户
    void deleteUserById(int id);

    //更新用户
    void updateUserById(User user);

}
