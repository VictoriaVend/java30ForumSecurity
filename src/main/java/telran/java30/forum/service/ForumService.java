package telran.java30.forum.service;

import java.util.List;

import telran.java30.forum.dto.CommentRepostDto;
import telran.java30.forum.dto.DatesDto;
import telran.java30.forum.dto.MessageDto;
import telran.java30.forum.dto.PostDto;
import telran.java30.forum.dto.PostRepostDto;

public interface ForumService {

	List<CommentRepostDto> findAllCommentsPost(String id);
	
	boolean addLike(String id);

	List<PostRepostDto> findPostsByTags(List<String> tags);

	List<PostRepostDto> findPostsByDates(DatesDto dateDto);

	PostRepostDto addComment(MessageDto message, String id, String author);

	List<PostRepostDto> findPostByAuthor(String author);

	PostRepostDto addPost(PostDto postDto, String author);

	PostRepostDto findPostById(String id);

	PostRepostDto updatePost(PostDto postDto, String id);

	PostRepostDto deletePost(String id);

	List<CommentRepostDto> findAllCommentsByAutor(String id, String author);

}
