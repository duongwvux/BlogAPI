package com.example.spring_boot_blog_application.services;


import com.example.spring_boot_blog_application.models.Posts;
import com.example.spring_boot_blog_application.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Optional<Posts> getById(Long id){
        return postRepository.findById(id);
    }

    public List<Posts> getAll(){
        return postRepository.findAll();
    }

    public Posts save(Posts posts){
        if (posts.getCreatedDate() == null) {
            posts.setCreatedDate(LocalDateTime.now());
        }
        posts.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(posts);
    }

    public void delete(Posts post) {
        postRepository.delete(post);
    }
}
