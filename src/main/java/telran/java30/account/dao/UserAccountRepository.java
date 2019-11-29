package telran.java30.account.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.java30.account.model.UserAccount;


public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

}
