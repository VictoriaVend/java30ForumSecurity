package telran.java30.forum.service.security;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import telran.java30.account.dao.UserAccountRepository;
import telran.java30.account.model.UserAccount;
import telran.java30.forum.dao.ForumRepository;
import telran.java30.forum.model.Post;

@Component("customSecurity")
public class CustomWebSecurity {
	@Autowired
	ForumRepository forumRepository;
	@Autowired
	UserAccountRepository userAccountRepository;

	public boolean checkAuthorityForDeletePost(String id, Authentication authentication) {
		Post post = forumRepository.findById(id).orElse(null);
		if (post == null) {
			return false;
		}
		return post.getAuthor().equals(authentication.getName());
	}
	public boolean checkExpDate(Authentication authentication) {
		UserAccount userAccount=userAccountRepository.findById(authentication.getName()).get();
		return userAccount.getExpDate().isAfter(LocalDateTime.now());
	}
}
