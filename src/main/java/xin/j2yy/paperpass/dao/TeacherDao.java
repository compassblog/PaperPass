package xin.j2yy.paperpass.dao;

import java.util.List;

import xin.j2yy.paperpass.entity.Teacher;

public interface TeacherDao {
	//根据主键删除
    int deleteByPrimaryKey(Integer tid);
    //插入
    int insert(Teacher teacher);
    //选择性插入
    int insertSelective(Teacher teacher);
    //根据主键查询
    Teacher selectByPrimaryKey(Integer tid);
    //查询所有
    List<Teacher> selectAll();
    //根据主键选择性更新
    int updateByPrimaryKeySelective(Teacher teacher);
    //根据主键更新
    int updateByPrimaryKey(Teacher teacher);
}