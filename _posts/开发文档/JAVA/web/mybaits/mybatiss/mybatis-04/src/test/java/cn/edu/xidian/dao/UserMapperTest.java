package cn.edu.xidian.dao;

import cn.edu.xidian.domain.User;
import cn.edu.xidian.untils.MybaitsUntils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

public class UserMapperTest {

    Logger logger = Logger.getLogger(UserMapperTest.class);

    @Test
    public void userMapperTest(){
        SqlSession sqlSession = MybaitsUntils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserById(5);

        System.out.println(user);

        sqlSession.close();
    }
}
