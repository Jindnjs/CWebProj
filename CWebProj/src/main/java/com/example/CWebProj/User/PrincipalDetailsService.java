//package com.example.CWebProj.User;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class PrincipalDetailsService implements UserDetailsService{
//
//	private final CUserRepository cuserRepository;
//	@Autowired
//    public PrincipalDetailsService(@Lazy CUserRepository cuserRepository) {
//        this.cuserRepository = cuserRepository;
//    }
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		CUser cuser = cuserRepository.findByUsername(username).orElseThrow(() -> {
//            return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
//        });
//		return new PrincipalDetails(cuser);
//	}
//
//}
