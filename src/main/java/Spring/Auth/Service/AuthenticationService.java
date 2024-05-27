package Spring.Auth.Service;

import Spring.Auth.Dto.SigninRequest;
import Spring.Auth.Dto.SignupRequest;
import Spring.Auth.Entity.JwtAuthenticationResponse;
import Spring.Auth.Entity.UserEntity;

public interface AuthenticationService {
	
	UserEntity signup(SignupRequest signupRequest);
	
	JwtAuthenticationResponse signin(SigninRequest signinRequest);

}
	