package com.wenchao.boottest.entity.mybatis;

import java.util.List;

import com.wenchao.boottest.common.bean.BaseEntity;
import com.wenchao.boottest.common.bean.Column;
import com.wenchao.boottest.common.bean.Id;
import com.wenchao.boottest.common.bean.Table;


@Table(value = "sys_login_user")
public class User extends BaseEntity implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2444257002237589456L;
	/**
	 * 主键
	 */
	@Id(value = "id")
    private Long id;
	/**
	 * 用户登陆名
	 */
	@Column(value = "username")
    private String username;
	/**
	 * 密码
	 */
	@Column(value = "password")
    private String password;
	/**
	 * 昵称
	 */
	@Column(value = "nickname")
    private String nickname;
	/**
	 * 邮箱
	 */
	@Column(value = "email")
    private String email;
	/**
	 * 手机号
	 */
	@Column(value = "user_phone")
    private String userPhone;
	/**
	 * 微信unionid
	 */
	@Column(value = "wechat_unionid")
    private String wechatUnionid;
	
	/**
	 * 用户类型id
	 */
	@Column(value = "user_cata_code")
    private Integer userCataCode;
	/**
	 * 用户类型值
	 */
	@Column(value = "user_cata_text")
    private String userCataText;
	/**
	 * 用户角色
	 */
    private List<Role> roles;     

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getWechatUnionid() {
		return wechatUnionid;
	}

	public void setWechatUnionid(String wechatUnionid) {
		this.wechatUnionid = wechatUnionid;
	}

	public Integer getUserCataCode() {
		return userCataCode;
	}

	public void setUserCataCode(Integer userCataCode) {
		this.userCataCode = userCataCode;
	}

	public String getUserCataText() {
		return userCataText;
	}

	public void setUserCataText(String userCataText) {
		this.userCataText = userCataText;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
    
    
}