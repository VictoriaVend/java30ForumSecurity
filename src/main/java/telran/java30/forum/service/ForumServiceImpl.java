package telran.java30.forum.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java30.forum.dao.ForumRepository;
import telran.java30.forum.dto.CommentRepostDto;
import telran.java30.forum.dto.DatesDto;
import telran.java30.forum.dto.MessageDto;
import telran.java30.forum.dto.PostDto;
import telran.java30.forum.dto.PostRepostDto;
import telran.java30.forum.exeption.PostNotFoundException;
import telran.java30.forum.model.Comment;
import telran.java30.forum.model.Post;

@Service
public class ForumServiceImpl implements ForumService {
	@Autowired
	ForumRepository forumRepository;

	@Override
	public boolean addLike(String id) {

		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.addLike();
		forumRepository.save(post);
		return forumRepository.existsById(id);

	}

	@Override
	public PostRepostDto addComment(MessageDto message, String id, String author) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		Comment comment = new Comment(author, message.getMessage());
		post.addComment(comment);
		Post post1 = forumRepository.save(post);
		return postToPostRepstDto(post1);
	}

	@Override
	public List<PostRepostDto> findPostByAuthor(String author) {
		return forumRepository.findByAuthor(author).map(p -> postToPostRepstDto(p)).collect(Collectors.toList());

	}

	@Override
	public PostRepostDto addPost(PostDto postDto, String author) {
		Post post = new Post(postDto.getTitle(), postDto.getContent(), author);
		post.addTags(postDto.getTags());
		post = forumRepository.save(post);
		return postToPostRepstDto(post);
	}

	@Override
	public PostRepostDto findPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return postToPostRepstDto(post);
	}

	@Override
	public PostRepostDto updatePost(PostDto postDto, String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setTags(postDto.getTags());
		forumRepository.save(post);
		return postToPostRepstDto(post);
	}

	@Override
	public PostRepostDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		forumRepository.deleteById(id);
		return postToPostRepstDto(post);
	}

	private PostRepostDto postToPostRepstDto(Post post) {
		List<CommentRepostDto> commentlist = new LinkedList<>();
		post.getComments().forEach(com -> commentlist.add(comentToCommentRepostDto(com)));
		List<String> taglist = new ArrayList<>();
		post.getTags().forEach(s -> taglist.add(s));

		return PostRepostDto.builder().id(post.getId()).title(post.getTitle()).content(post.getContent())
				.author(post.getAuthor()).dateCreated(post.getDateCreated()).tags(taglist).likes(post.getLikes())
				.comments(commentlist).build();
	}

	private CommentRepostDto comentToCommentRepostDto(Comment comment) {
		return CommentRepostDto.builder().user(comment.getUser()).message(comment.getMessage())
				.dateCreated(comment.getDateCreated()).likes(comment.getLikes()).build();

	}

	@Override
	public List<PostRepostDto> findPostsByTags(List<String> tags) {
		return forumRepository.findPostsByTagsIn(tags).map(p -> postToPostRepstDto(p)).collect(Collectors.toList());

	}

	@Override
	public List<PostRepostDto> findPostsByDates(DatesDto dateDto) {
		return forumRepository.findPostsByDateCreatedBetween(dateDto.getFrom(), dateDto.getTo())
				.map(p -> postToPostRepstDto(p)).collect(Collectors.toList());
	}

	@Override
	public List<CommentRepostDto> findAllCommentsPost(String id) {
		List<CommentRepostDto> comments = new LinkedList<>();
		forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id)).getComments()
				.forEach(com -> comments.add(comentToCommentRepostDto(com)));
		return comments;
	}

	@Override
	public List<CommentRepostDto> findAllCommentsByAutor(String id, String author) {
		List<CommentRepostDto> comments = new LinkedList<>();
		forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id)).getComments().forEach(c -> {
			if (c.getUser().equals(author)) {
				comments.add(comentToCommentRepostDto(c));
			}
		});

		return comments;
	}

}
