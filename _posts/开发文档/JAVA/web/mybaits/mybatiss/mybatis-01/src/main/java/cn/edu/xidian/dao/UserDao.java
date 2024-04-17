package cn.edu.xidian.dao;

import cn.edu.xidian.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //查询全部用户
    List<User> getUserList();

    //根据用户id查询用户
    @Select("select * from mybatis.user where user_id = #{user_id}")
    User getUserById(int id);

    //插入用户
    void insertUser(User user);

    //删除用户
    void deleteUserById(int id);

    //更新用户
    void updateUserById(User user);

    List<User> getUserByLimit(Map<String,Integer> map);

}
