package Spring.Auth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Spring.Auth.Dto.SigninRequest;
import Spring.Auth.Dto.SignupRequest;
import Spring.Auth.Entity.JwtAuthenticationResponse;
import Spring.Auth.Entity.UserEntity;
import Spring.Auth.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	private  AuthenticationService authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<UserEntity> signup(@RequestBody SignupRequest signupRequest){
		return ResponseEntity.ok(authenticationService.signup(signupRequest));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
		return ResponseEntity.ok(authenticationService.signin(signinRequest));
	}
	
//	@GetMapping("/check")
//		public String check() { return "api is run";}
//		

}






