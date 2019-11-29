package telran.java30.forum.dto;

import java.time.LocalDateTime;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRepostDto {
	String id;
	String title;
	String content;
	String author;
	@JsonFormat(pattern = "dd-MM-yyyy'T'hh:mm:ss")
	LocalDateTime dateCreated;
	@Singular
	List<String> tags;
	Integer likes;
	@Singular
	List<CommentRepostDto> comments;

}
