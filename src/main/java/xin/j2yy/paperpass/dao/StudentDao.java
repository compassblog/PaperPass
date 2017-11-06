package xin.j2yy.paperpass.dao;

import java.util.List;

import xin.j2yy.paperpass.entity.Student;

public interface StudentDao {
	//根据主键删除
    int deleteByPrimaryKey(Integer sid);
    //插入
    int insert(Student student);
    //选择性插入
    int insertSelective(Student student);
    //根据主键查询
    Student selectByPrimaryKey(Integer sid);
    //查询所有
    List<Student> selectAll();
    //根据主键选择性更新
    int updateByPrimaryKeySelective(Student student);
    //根据主键更新
    int updateByPrimaryKey(Student student);
}