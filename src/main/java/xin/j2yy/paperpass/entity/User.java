package xin.j2yy.paperpass.entity;

public class User {
    //用户编号
    private Integer uid;
    //用户所对应的角色
    private Role role;
    //登录账号
    private String username;
    //登录密码
    private String password;
    //状态
    private Integer status;
    //验证码
    private String verifycode;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode == null ? null : verifycode.trim();
    }
}