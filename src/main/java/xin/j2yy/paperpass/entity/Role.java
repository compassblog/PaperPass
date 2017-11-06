package xin.j2yy.paperpass.entity;

import java.util.List;

public class Role {
	//角色编号
    private Integer rid;
    //角色名称
    private String rolename;
    //该角色下所对应的所有权限
    private List<Authority> authorities;
    
    public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public Integer getRid() {
        return rid;
    }
    
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }
}