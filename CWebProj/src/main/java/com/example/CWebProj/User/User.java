	package com.example.CWebProj.User;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class User implements UserDetails {


	private CUser cuser;
	
	public User(CUser cuser) {
		this.cuser = cuser;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		String role = (String) cuser.getRole();
		
		ArrayList<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		
		authList.add(new GrantedAuthority() {
			public String getAuthority() {
				return role;
			}
		});

		return authList;
	}

	@Override
	public String getPassword() {

		return cuser.getPassword();
	}

	@Override
	public String getUsername() {
		
		return cuser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

	
	
}
