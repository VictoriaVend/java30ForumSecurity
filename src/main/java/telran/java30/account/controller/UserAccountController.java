package telran.java30.account.controller;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java30.account.dto.UserEditDto;
import telran.java30.account.dto.UserProfileDto;
import telran.java30.account.dto.UserRegisterDto;
import telran.java30.account.service.UserService;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	@Autowired
	UserService userService;
	
	@PostMapping("/user")
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return userService.register(userRegisterDto);
	}

	@PostMapping("/login")
	public UserProfileDto login(Authentication authentication) {
		
		return userService.login(authentication.getName());
	}

	@PutMapping("/user")
	public UserProfileDto editUser( @RequestBody UserEditDto userEditDto, Authentication authentication) {
		return userService.editUser(authentication.getName(), userEditDto);
	}

	@DeleteMapping("/user")
	public UserProfileDto removeUser(Authentication authentication) {
		return userService.removeUser(authentication.getName());
	}

	@PutMapping("/user/password")
	public void changePassword( @RequestHeader("X-Password")String password,Authentication authentication) {
		userService.changePassword(authentication.getName(), password);
	}

	@PostMapping("/user/{login}/role/{role}")
	public Set<String> addRole(@PathVariable String login, @PathVariable String role) {
		return userService.addRole(login, role);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public Set<String> removeRole(@PathVariable String login, @PathVariable String role) {
		return userService.removeRole(login, role);
	}
}
