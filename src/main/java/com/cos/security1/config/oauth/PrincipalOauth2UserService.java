package com.cos.security1.config.oauth;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.config.oauth.provider.FacebookUserInfo;
import com.cos.security1.config.oauth.provider.GoogleUserInfo;
import com.cos.security1.config.oauth.provider.NaverUserInfo;
import com.cos.security1.config.oauth.provider.OAuth2Userinfo;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PrincipalOauth2UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private UserRepository userRepository;

    // 구글로 부터 받은 userRequest 데이터에 대한 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration()); // 어떤 OAuth에서 로그인 했는지 확인가능
        System.out.println("userToken : " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 구글 로그인 버튼 클릭 → 구글 로그인 창 → 로그인을 완료 → code를 리턴(OAuth-Client라이브러리) → AccessToken요청
        // userRequest 정보 → 회원 프로필 받음(loadUser함수) 호출 → 구글로부터 회원 프로필을 받아줌
        System.out.println("getAttribute " + oAuth2User.getAttributes());
        OAuth2Userinfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("Google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("Facebook")){
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("Naver")){
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("reponse"));
        }
        else {
            System.out.println("미지원 로그인");
        }

//        String provider = userRequest.getClientRegistration().getRegistrationId(); //
//        String providerId = (String) oAuth2User.getAttributes().get("sub");
//        String username = provider+"_"+providerId; // google_
//        String password = bCryptPasswordEncoder.encode("겟인데어");
//        String email = oAuth2User.getAttribute("email");
//        String role = "ROLE_USER";

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        System.out.println("==================username : " + username);

        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            System.out.println("구글 로그인이 최초 입니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }else {
            System.out.println("구글 로그인을 기록있음. 자동 회원 가입.");
            return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
        }
        //회원가입 진행

        return super.loadUser(userRequest);
    }
}
