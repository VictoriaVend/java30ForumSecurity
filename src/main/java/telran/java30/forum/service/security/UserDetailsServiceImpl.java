package telran.java30.forum.service.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import telran.java30.account.dao.UserAccountRepository;
import telran.java30.account.model.UserAccount;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserAccountRepository userAccountReposytory;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount= userAccountReposytory.findById(username).orElseThrow(()-> new UsernameNotFoundException(username));
		String password = userAccount.getPassword();
		List<GrantedAuthority>listRole= userAccount.getRoles()
				.stream()
				.map(r->new SimpleGrantedAuthority("ROLE_"+r.toUpperCase()))
				.collect(Collectors.toList());
		
		return new User(username, password, true,true,true, true, listRole);
	}

}
