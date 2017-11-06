package xin.j2yy.paperpass.entity;

import java.util.List;

public class Authority {
    //权限编号
    private Integer aid;
    //权限url
    private String url;
    //该权限下所对应的所有角色
    private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}