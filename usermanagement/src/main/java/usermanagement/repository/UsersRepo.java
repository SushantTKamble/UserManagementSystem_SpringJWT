package usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
 
import usermanagement.entity.OurUsers;

 
public interface UsersRepo extends JpaRepository<OurUsers, Integer> {
	
	
	Optional<OurUsers> findByEmail(String email);

}
