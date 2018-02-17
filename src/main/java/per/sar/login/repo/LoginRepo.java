package per.sar.login.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import per.sar.login.entity.LoginCredentials;
@Repository
@Transactional
public interface LoginRepo extends JpaRepository<LoginCredentials, String> {   //<entity calss,Data type of primery key>


	 List<LoginCredentials> findByUsernameContainingOrPassContaining(String text, String textAgain);
	 
}
