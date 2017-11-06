package xin.j2yy.paperpass.dao;

import java.util.List;

import xin.j2yy.paperpass.entity.Authority;

public interface AuthorityDao {
    //根据主键删除
	int deleteByPrimaryKey(Integer aid);
	//插入一个权限
	int insert(Authority authority);
	//选择性插入
    int insertSelective(Authority authority);
    //根据主键查询
    Authority selectByPrimaryKey(Integer aid);
    //查询所有
    List<Authority> selectAll();
    //根据主键更新
    int updateByPrimaryKey(Authority authority);
    //选择性更新
    int updateByPrimaryKeySelective(Authority authority);

}