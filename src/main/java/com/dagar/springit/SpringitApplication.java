package com.dagar.springit;

import com.dagar.springit.domain.Comment;
import com.dagar.springit.domain.Link;
import com.dagar.springit.repository.CommentRepository;
import com.dagar.springit.repository.LinkRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static java.lang.System.out;

@SpringBootApplication
public class SpringitApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringitApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringitApplication.class, args);
	}

	@Bean
	PrettyTime prettyTime(){
		return new PrettyTime();
	}
	//@Bean
	CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository) {
		return args -> {
			Link link = new Link("Getting started with Spring Boot 2", "https://www.danvega.dev");
			linkRepository.save(link);

			Comment comment = new Comment("Great post!", link);
			commentRepository.save(comment);
			//link.addComment(comment);
			comment = new Comment("What a guy!", link);
			commentRepository.save(comment);

			comment = new Comment("What a playa!", link);
			commentRepository.save(comment);

			Long id = 1L;
			Optional<Link> firstLink = linkRepository.findById(id);
			if (firstLink.isPresent()) {
				out.println(firstLink.get().getTitle());
			} else {
				out.println("Not Found!");
			}
//			List<Comment> comments = linkRepository
//					.findCommentById(firstLink.orElse(new Link("Stub", "Stub")).getId());
			Long cid = 1L;
			Optional<Comment> firstComment = commentRepository.findById(cid);
			if (firstComment.isPresent()) {
				out.println(firstComment.get().getBody());
			} else {
				out.println("Not Found!");
			}
		};
	}
}
