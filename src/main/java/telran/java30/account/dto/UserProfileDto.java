package telran.java30.account.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserProfileDto {
	String login;
	String firstName;
	String lastName;
	Set<String> roles;

}
