package telran.java30.forum.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRepostDto {
	String user;
	String message;
	@JsonFormat(pattern = "dd-MM-yyyy'T'hh:mm:ss")
	LocalDateTime dateCreated;
	Integer likes;
}
