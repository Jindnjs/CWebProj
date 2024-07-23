package com.example.CWebProj.User;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final CUserRepository cuserRepository;

    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" +providerId;

        Optional<CUser> optionalUser = cuserRepository.findByUsername(username);
        CUser cuser;

        if(optionalUser.isEmpty()) {
            cuser = CUser.builder()
                    .username(username)
                    .cname(oAuth2User.getAttribute("name"))
                    .provider(provider)
                    .providerId(providerId)
                    .role("ROLE_USER")
                    .build();
            cuserRepository.save(cuser);
        } else {
            cuser = optionalUser.get();
        }

        return new PrincipalDetails(cuser, oAuth2User.getAttributes());
    }
}