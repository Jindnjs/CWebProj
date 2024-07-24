package com.example.CWebProj.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipalDetails implements UserDetails , OAuth2User{
	
	private static final long serialVersionUID = 5845106256039667698L;
	
	private CUser cuser;
	private Map<String, Object> attributes;

	public PrincipalDetails(CUser cuser) {
        this.cuser = cuser;
    }

    public PrincipalDetails(CUser cuser, Map<String, Object> attributes) {
        this.cuser = cuser;
        this.attributes = attributes;
    }

    // 권한 관련 작업을 하기 위한 role return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(new SimpleGrantedAuthority(cuser.getRole()));

        return collections;
    }

    // get Password 메서드
    @Override
    public String getPassword() {
        return cuser.getPassword();
    }

    // get Username 메서드 (생성한 User은 loginId 사용)
    @Override
    public String getUsername() {
        return cuser.getUsername();
    }

    // 계정이 활성화(사용가능)인지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return cuser.getCname();
	}

}
