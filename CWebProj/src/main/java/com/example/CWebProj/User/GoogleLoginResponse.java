package com.example.CWebProj.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleLoginResponse {
    private String access_token;
    private String expires_in;  
    private String refreshToken;  
    private String scope;
    private String token_type; 
    private String id_token;
}
