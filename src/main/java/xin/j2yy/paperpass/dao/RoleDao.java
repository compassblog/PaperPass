package xin.j2yy.paperpass.dao;

import java.util.List;

import xin.j2yy.paperpass.entity.Role;

public interface RoleDao {
	//根据主键删除
    int deleteByPrimaryKey(Integer rid);
    //插入
    int insert(Role role);
    //选择性插入
    int insertSelective(Role role);
    //根据主键选择性更新
    int updateByPrimaryKeySelective(Role role);
    //根据主键更新
    int updateByPrimaryKey(Role role);
    //根据主键查询
    Role selectByPrimaryKey(Integer rid);
    //查询所有
    List<Role> selectAll();
}