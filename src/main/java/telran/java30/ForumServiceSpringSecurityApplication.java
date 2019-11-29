package telran.java30;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.java30.account.dao.UserAccountRepository;
import telran.java30.account.model.UserAccount;

@SpringBootApplication
public class ForumServiceSpringSecurityApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceSpringSecurityApplication.class, args);
	}
	@Autowired
	UserAccountRepository accountRepository;
	@Override
	public void run(String... args) throws Exception {

		if(!accountRepository.existsById("admin")) {
			String hashPassword = BCrypt.hashpw("admin", BCrypt.gensalt()); 
			UserAccount admin = UserAccount.builder()
					.login("admin")
					.password(hashPassword)
					.firstName("Super")
					.lastName("Admin")
					.role("User")
					.role("Moderator")
					.role("Administrator")
					.expDate(LocalDateTime.now().plusYears(25))
					.build();
			accountRepository.save(admin);
		}
		
	
		
	}

}
