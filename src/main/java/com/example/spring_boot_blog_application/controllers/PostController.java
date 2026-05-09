package com.example.spring_boot_blog_application.controllers;

import com.example.spring_boot_blog_application.models.Account;
import com.example.spring_boot_blog_application.models.Posts;
import com.example.spring_boot_blog_application.services.AccountService;
import com.example.spring_boot_blog_application.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model){
        Optional<Posts> optionalPosts = postService.getById(id);
        if(optionalPosts.isPresent()){
            Posts posts = optionalPosts.get();
            model.addAttribute("post", posts);
            return "post";
        } else {
            return "404";
        }

    }


    @GetMapping("/posts/new")
    public String crateNewPost(Model model){
        Optional<Account> optionalAccount = accountService.findByEmail("user.user@domain.com");
        if(optionalAccount.isPresent()){
            Posts post = new Posts();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_new";
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/new")
    public String saveNewPost(@ModelAttribute Posts post){
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model){
        Optional<Posts> optionalPosts = postService.getById(id);
        if(optionalPosts.isPresent()){
            Posts post = optionalPosts.get();
            model.addAttribute("post", post);
            return "post_edit";
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, Posts post, BindingResult result,  Model model){
        Optional<Posts> optionalPosts = postService.getById(id);
        if(optionalPosts.isPresent()){
            Posts existingPost = optionalPosts.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());

            postService.save(existingPost);
        }
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id){

        Optional<Posts> optionalPosts = postService.getById(id);
        if(optionalPosts.isPresent()){
            Posts post = optionalPosts.get();

            postService.delete(post);
            return "redirect:/";
        } else  {
            return "404";
        }
    }
}
