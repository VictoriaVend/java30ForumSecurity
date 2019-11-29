package telran.java30.account.service;

import java.util.Set;

import telran.java30.account.dto.UserEditDto;
import telran.java30.account.dto.UserProfileDto;
import telran.java30.account.dto.UserRegisterDto;

public interface UserService {
	UserProfileDto register(UserRegisterDto userRegisterDto);

	UserProfileDto login(String login);

	UserProfileDto editUser(String login, UserEditDto userEditDto);

	UserProfileDto removeUser(String login);

	void changePassword(String login, String password);

	Set<String> addRole(String login, String role);

	Set<String> removeRole(String login, String role);

}
