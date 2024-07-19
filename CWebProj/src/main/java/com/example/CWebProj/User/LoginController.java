package com.example.CWebProj.User;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {
	
	
	
    @Value("https://oauth2.googleapis.com")
    private String googleAuthUrl;

    @Value("https://accounts.google.com")
    private String googleLoginUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("http://localhost:8080/login/oauth2/code/google")
    private String googleRedirectUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;


    @Autowired
    private CUserService cuserService;

    @GetMapping("/code/{registrationId}")
    public String googleLogin(@RequestParam String code, @PathVariable String registrationId) throws JsonMappingException, JsonProcessingException {
    	System.out.println("logincontroller : " + code);
    	System.out.println("registrationId : " + registrationId);
    	
    	
        GoogleOAuthRequest googleOAuthRequest = GoogleOAuthRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .code(code)
                .redirectUri(googleRedirectUrl)
                .grantType("authorization_code")
                .build();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GoogleLoginResponse> apiResponse = restTemplate.postForEntity(googleAuthUrl + "/token", googleOAuthRequest, GoogleLoginResponse.class);
        
        GoogleLoginResponse googleLoginResponse = apiResponse.getBody();



        String googleToken = googleLoginResponse.getId_token();

        String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo").queryParam("id_token",googleToken).toUriString();

        String resultJson = restTemplate.getForObject(requestUrl, String.class);
        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(resultJson);

        String email = rootNode.get("email").asText();

        System.out.println("Email: " + email);
        
		int result = cuserService.logincheck(email);
		
		if (result == 1) { //디비에 회원정보가 이미 있을 경우 로그인 성공
			return "redirect:/"; 
		} else {           //디비에 회원정보가 이미 없는 경우 회원 가입
			return "redirect:/user/signup";
		}
        
    }
    
    
    
}