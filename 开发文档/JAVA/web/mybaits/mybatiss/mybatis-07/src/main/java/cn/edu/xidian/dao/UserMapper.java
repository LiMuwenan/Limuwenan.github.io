package cn.edu.xidian.dao;

import cn.edu.xidian.domain.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {


    User queryUserById(int id);

    void updateUserById(Map map);

}
