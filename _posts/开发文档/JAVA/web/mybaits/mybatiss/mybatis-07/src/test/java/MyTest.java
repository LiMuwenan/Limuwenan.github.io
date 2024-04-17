import cn.edu.xidian.dao.UserMapper;
import cn.edu.xidian.domain.User;
import cn.edu.xidian.untils.MybatisUntils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class MyTest {

    @Test
    public void queryUserListTest(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user1 = userMapper.queryUserById(1);
        System.out.println(user1);
        System.out.println("======================");
        User user2 = userMapper.queryUserById(1);
        System.out.println(user2);

        sqlSession.close();
    }

    @Test
    public void queryUserById(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        HashMap map = new HashMap();

        map.put("user_pwd","123142");
        map.put("user_id",1);

        userMapper.updateUserById(map);

        sqlSession.commit();

        User user1 = userMapper.queryUserById(2);
        System.out.println(user1);

        sqlSession.close();
    }

    @Test
    public void cacheTest(){
        SqlSession sqlSession1 = MybatisUntils.getSqlSession();
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        User user1 = userMapper1.queryUserById(1);
        sqlSession1.close();

        SqlSession sqlSession2 = MybatisUntils.getSqlSession();
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = userMapper2.queryUserById(1);



        System.out.println(user1);
        System.out.println("================");
        System.out.println(user1);


        sqlSession2.close();
    }
}
