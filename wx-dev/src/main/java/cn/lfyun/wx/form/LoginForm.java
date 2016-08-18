package cn.lfyun.wx.form;

import org.hibernate.validator.constraints.Length;


public class LoginForm {

	@Length(min = 4, max = 10, message = "username.length.error")
	private String username;
	@Length(min = 4, max = 10, message = "password.length.error")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
