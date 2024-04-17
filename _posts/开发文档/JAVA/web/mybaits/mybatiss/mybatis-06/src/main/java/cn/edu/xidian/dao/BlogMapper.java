package cn.edu.xidian.dao;

import cn.edu.xidian.domain.Blog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BlogMapper {

    //插入数据
    int addBlog(Blog blog);

    //查询博客
    List<Blog> queryBlogIF(Map map);

    List<Blog> queryBlogChoose(Map map);

    List<Blog> queryBlogWhere(Map map);

    //更新表
    void updateBlog(HashMap map);

    //查询blog_views在1000-2000范围内的博客
    List<Blog> queryBlogForeach(Map map);


}
