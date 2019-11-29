package telran.java30.forum.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of= {"user","dateCreated"})
public class Comment {
	String user;
	@Setter
	String message;
	@JsonFormat(pattern = "dd-MM-yyyy'T'hh:mm:ss")
	LocalDateTime dateCreated;
	Integer likes;

	public Comment(String user, String message) {
		this.user = user;
		this.message = message;
		this.dateCreated = LocalDateTime.now();
		this.likes = 0;
	}

	public void addLike() {
		this.likes++;
	}

}
