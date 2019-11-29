package telran.java30.account.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.java30.account.configuration.AccountConfiguration;
import telran.java30.account.dao.UserAccountRepository;
import telran.java30.account.dto.UserEditDto;
import telran.java30.account.dto.UserProfileDto;
import telran.java30.account.dto.UserRegisterDto;
import telran.java30.account.exeption.UserAuthenticationException;
import telran.java30.account.exeption.UserExistsException;
import telran.java30.account.model.UserAccount;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	AccountConfiguration accountConfiguration;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserProfileDto register(UserRegisterDto userRegisterDto) {
		if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
			throw new UserExistsException();
		}
		String hashPassword = passwordEncoder.encode(userRegisterDto.getPassword());
		UserAccount userAccount = UserAccount.builder().login(userRegisterDto.getLogin()).password(hashPassword)
				.firstName(userRegisterDto.getFirstName()).lastName(userRegisterDto.getLastName()).role("User")
				.expDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod())).build();
		userAccountRepository.save(userAccount);
		return userAccountToUserProfileDto(userAccount);
	}

	private UserProfileDto userAccountToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder().login(userAccount.getLogin()).firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName()).roles(userAccount.getRoles()).build();
	}

	@Override
	public UserProfileDto login(String login) {
		
		return userAccountToUserProfileDto(userAccountRepository.findById(login).get());
	}

	@Override
	public UserProfileDto editUser(String login, UserEditDto userEditDto) {

		UserAccount userAccount = userAccountRepository.findById(login).get();
		userAccount.setFirstName(userEditDto.getFirstName());
		userAccount.setLastName(userEditDto.getLastName());
		userAccountRepository.save(userAccount);

		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto removeUser(String login) {
		UserAccount userAccount =userAccountRepository.findById(login).get();
		userAccountRepository.deleteById(userAccount.getLogin());
		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public void changePassword(String login, String password) {
		UserAccount userAccount = userAccountRepository.findById(login).get();
		userAccount.setPassword(passwordEncoder.encode(password));
		userAccount.setExpDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()));
		userAccountRepository.save(userAccount);

	}

	@Override
	public Set<String> addRole(String login, String role) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserAuthenticationException::new);
		userAccount.addRole(role);
		userAccountRepository.save(userAccount);
		return userAccount.getRoles();

	}

	@Override
	public Set<String> removeRole(String login, String role) {
		
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserAuthenticationException::new);
		if (userAccount.removeRole(role)) {
			userAccountRepository.save(userAccount);
		}
		return userAccount.getRoles();
	}

}
