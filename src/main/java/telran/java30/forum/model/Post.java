package telran.java30.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@NoArgsConstructor
@Getter

@EqualsAndHashCode(of = "id")
@Document(collection = "posts")
public class Post {
	@Id
	String id;
	@Setter
	String title;
	@Setter
	String content;
	String author;
	@JsonFormat(pattern = "dd-MM-yyyy'T'hh:mm:ss")
	LocalDateTime dateCreated;
	@Setter
	@Singular
	List<String> tags;
	Integer likes;
	@Singular
	List<Comment> comments;

	public Post(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.tags = new ArrayList<String>();
		this.comments = new LinkedList<Comment>();
		this.dateCreated = LocalDateTime.now();
		this.likes = 0;
	}

	public boolean addComment(Comment comment) {
		return this.comments.add(comment);

	}

	public void addLike() {
		this.likes++;
	}

	public void addTags(List<String> list) {

		this.tags.addAll(list);
	}

}
