package xin.j2yy.paperpass.dao;

import java.util.List;

import xin.j2yy.paperpass.entity.User;

public interface UserDao {
	//根据主键删除
    int deleteByPrimaryKey(Integer uid);
    //插入
    int insert(User user);
    //选择性插入
    int insertSelective(User user);
    //根据主键查询
    User selectByPrimaryKey(Integer uid);
    //查询所有
    List<User> selectAll();
    //根据主键选择性更新
    int updateByPrimaryKeySelective(User user);
    //根据主键更新
    int updateByPrimaryKey(User user);
}