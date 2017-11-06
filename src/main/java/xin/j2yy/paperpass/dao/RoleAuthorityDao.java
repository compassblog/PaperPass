package xin.j2yy.paperpass.dao;

import java.util.List;

import xin.j2yy.paperpass.entity.Authority;
import xin.j2yy.paperpass.entity.Role;
import xin.j2yy.paperpass.entity.RoleAuthority;

public interface RoleAuthorityDao {
	//通过角色ID查询其对应的权限信息
	List<Authority> selectByRoleId(Integer rid);
	//通过权限ID查询其所对应的角色信息
	List<Role> selectByAuthorityId(Integer aid);
	
    int deleteByPrimaryKey(Integer raid);

    int insert(RoleAuthority record);

    RoleAuthority selectByPrimaryKey(Integer raid);

    List<RoleAuthority> selectAll();

    int updateByPrimaryKey(RoleAuthority record);
}