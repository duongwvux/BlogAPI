package com.example.spring_boot_blog_application.repositories;

import com.example.spring_boot_blog_application.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {


}
