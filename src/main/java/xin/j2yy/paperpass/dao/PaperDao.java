package xin.j2yy.paperpass.dao;

import java.util.List;

import xin.j2yy.paperpass.entity.Paper;

public interface PaperDao {
	//根据主键删除
    int deleteByPrimaryKey(Integer pid);
    //插入
    int insert(Paper paper);
    //选择性插入
    int insertSelective(Paper paper);
    //根据主键查询
    Paper selectByPrimaryKey(Integer pid);
    //查询所有
    List<Paper> selectAll();
    //根据教师ID查询所有论文信息
    List<Paper> selectByTeacherId(Integer tid);
    //根据主键选择性更新
    int updateByPrimaryKeySelective(Paper record);
    //根据主键更新
    int updateByPrimaryKey(Paper record);
}