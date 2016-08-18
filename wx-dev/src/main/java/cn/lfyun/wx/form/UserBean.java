package cn.lfyun.wx.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class UserBean {

	@NotEmpty(message="姓名不能为空")
    private String name;

    @Range(min=20,max=120,message="年龄在20到120岁之间")
    private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

    
}
