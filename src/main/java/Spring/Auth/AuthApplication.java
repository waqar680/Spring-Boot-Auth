package Spring.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import Spring.Auth.Entity.Role;
import Spring.Auth.Entity.UserEntity;
import Spring.Auth.Repository.UserRepository;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
	
	
	public void run(String... args) {
		UserEntity adminAccount = userRepository.findByRole(Role.ADMIN);
		
		if(null == adminAccount) {
			UserEntity userEntity = new UserEntity();
			
			userEntity.setEmail("admin@gmail.com");
			userEntity.setFirstname("admin");
			userEntity.setSecondname("admin");
			userEntity.setRole(Role.ADMIN);
			userEntity.setPassword(new BCryptPasswordEncoder().encode("admin"));
			
			userRepository.save(userEntity);
		}
	}

}
