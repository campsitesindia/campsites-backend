package com.dd.campsites.campsitesindia.security.oauth2;

import com.dd.campsites.campsitesindia.exception.OAuth2AuthenticationProcessingException;
import com.dd.campsites.campsitesindia.security.oauth2.user.OAuth2UserInfo;
import com.dd.campsites.campsitesindia.security.oauth2.user.OAuth2UserInfoFactory;
import com.dd.campsites.config.ApplicationProperties;
import com.dd.campsites.domain.AuthenticatedUser;
import com.dd.campsites.domain.Authority;
import com.dd.campsites.domain.User;
import com.dd.campsites.domain.enumeration.AuthProvider;
import com.dd.campsites.repository.AuthenticatedUserRepository;
import com.dd.campsites.repository.AuthorityRepository;
import com.dd.campsites.repository.UserRepository;
import com.dd.campsites.security.AuthoritiesConstants;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.jhipster.security.RandomUtil;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final AuthenticatedUserRepository authenticatedUserRepository;

    private final AuthorityRepository authorityRepository;

    public CustomOAuth2UserService(
        UserRepository userRepository,
        AuthenticatedUserRepository authenticatedUserRepository,
        AuthorityRepository authorityRepository
    ) {
        this.userRepository = userRepository;
        this.authenticatedUserRepository = authenticatedUserRepository;

        this.authorityRepository = authorityRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler

            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
            oAuth2UserRequest.getClientRegistration().getRegistrationId(),
            oAuth2User.getAttributes()
        );
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findOneByEmailIgnoreCase(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            AuthenticatedUser authenticatedUser = authenticatedUserRepository.findAuthenticatedUserByUser_Id(user.getId());
            System.out.println(authenticatedUser);
            if (
                !authenticatedUser.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
            ) {
                throw new OAuth2AuthenticationProcessingException(
                    "Looks like you're signed up with " +
                    authenticatedUser.getProvider() +
                    " account. Please use your " +
                    authenticatedUser.getProvider() +
                    " account to login."
                );
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return oAuth2User;
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setLogin(oAuth2UserInfo.getId());
        user.setFirstName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.setActivated(true);
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        user.setAuthorities(authorities);

        user = userRepository.save(user);

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setAuthTimestamp(Instant.now());
        authenticatedUser.setFirstName(oAuth2UserInfo.getName());
        authenticatedUser.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        authenticatedUser.setUser(user);
        authenticatedUserRepository.save(authenticatedUser);

        return user;
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFirstName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
