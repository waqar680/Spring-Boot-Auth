package Spring.Auth.Repository;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Spring.Auth.Entity.Role;
import Spring.Auth.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	
	Optional<UserEntity> findByEmail(String email);
	
	 UserEntity findByRole(Role role);
 }
