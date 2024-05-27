package Spring.Auth.ServiceImpl;

import java.util.HashMap;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Spring.Auth.Dto.SigninRequest;
import Spring.Auth.Dto.SignupRequest;
import Spring.Auth.Entity.JwtAuthenticationResponse;
import Spring.Auth.Entity.Role;
import Spring.Auth.Entity.UserEntity;
import Spring.Auth.Repository.UserRepository;
import Spring.Auth.Service.AuthenticationService;
import Spring.Auth.Service.JwtService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.var;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	public UserEntity signup(SignupRequest signupRequest) {
		UserEntity userEntity = new UserEntity();
		
		userEntity.setEmail(signupRequest.getEmail());
		userEntity.setFirstname(signupRequest.getFirstname());
		userEntity.setSecondname(signupRequest.getSecondname());
		userEntity.setRole(Role.USER);
		userEntity.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		
		return userRepository.save(userEntity);
	}
	
	public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
		 if (StringUtils.isEmpty(signinRequest.getEmail()) || StringUtils.isEmpty(signinRequest.getPassword())) {
		        throw new IllegalArgumentException("Email or password cannot be empty");
		    }
		 try {
			 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				        signinRequest.getEmail(), signinRequest.getPassword()));

				UserEntity userToken = userRepository.findByEmail(signinRequest.getEmail())
				        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

				String jwt = jwtService.generateToken(userToken);
				String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userToken);

				JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
				jwtAuthenticationResponse.setToken(jwt);
				jwtAuthenticationResponse.setRefreshToken(refreshToken);
				return jwtAuthenticationResponse;
		 }catch (Exception e) {
		        throw new BadCredentialsException("Invalid email or password", e);
		}
		  
		
	}
}








