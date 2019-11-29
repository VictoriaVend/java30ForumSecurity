package telran.java30.account.configuration;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.java30.account.exeption.UserAuthenticationException;
import telran.java30.account.model.UserAccount;

@Configuration
@ManagedResource
public class AccountConfiguration {
	
	Map<String,UserAccount> users= new ConcurrentHashMap<>();
	
	@Value("${exp.value}")
	long expPeriod;
	
	@Value("${loginAdmin}")
	String loginAdmin;

	
	
	
	@ManagedAttribute
	public String getLoginAdmin() {
		return loginAdmin;
	}
	@ManagedAttribute
	public void setLoginAdmin(String loginAdmin) {
		this.loginAdmin = loginAdmin;
	}

	@ManagedAttribute
	public long getExpPeriod() {
		return expPeriod;
	}

	@ManagedAttribute
	public void setExpPeriod(long expPeriod) {
		this.expPeriod = expPeriod;
	}

	public UserAccountCredentials tokenDecode(String token) {
		try {
			int pos = token.indexOf(" ");
			token = token.substring(pos + 1);
			String str = new String(Base64.getDecoder().decode(token));
			String[] credention = str.split(":");

			return new UserAccountCredentials(credention[0], credention[1]);
		} catch (Exception e) {
			throw new UserAuthenticationException();
		}

	}
	public boolean addUser(String session, UserAccount userAccount) {
		return users.put(session,userAccount)==null;
	
	}
	public UserAccount getUser(String session) {
		return users.get(session);
	}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
