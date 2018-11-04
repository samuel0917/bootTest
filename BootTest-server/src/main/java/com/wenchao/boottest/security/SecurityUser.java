package com.wenchao.boottest.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wenchao.boottest.entity.mybatis.Role;
import com.wenchao.boottest.entity.mybatis.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser extends User implements UserDetails
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8534701529594386481L;

	public SecurityUser(User user) {
        if(user != null)
        {
            this.setId(user.getId());
            this.setUsername(user.getUsername());
            this.setEmail(user.getEmail());
            this.setPassword(user.getPassword());
            this.setNickname(user.getNickname());
            this.setUserPhone(user.getUserPhone());
            this.setRoles(user.getRoles());
        }
    }
    // 获取权限信息 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Role> roles = this.getRoles();
        if(roles != null)
        {
            for (Role role : roles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    public String getUsername() {
        return super.getUsername();
    }
    // 账号是否未过期，默认是false
    public boolean isAccountNonExpired() {
        return true;
    }
    // 账号是否未锁定，默认是false
    public boolean isAccountNonLocked() {
        return true;
    }
    // 账号凭证是否未过期，默认是false
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}
