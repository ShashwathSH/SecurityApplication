package com.shashwathshSecurityApp.SecurityApplication.controllers;


import com.shashwathshSecurityApp.SecurityApplication.dtos.PostDto;
import com.shashwathshSecurityApp.SecurityApplication.services.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class PostController {

    PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping
    public PostDto createNewPost(@RequestBody PostDto postDto){
        return postService.createNewPost(postDto);
    }

    @GetMapping("{id}")
    public PostDto getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @PutMapping("/{postId}")
    public PostDto updatePost(@PathVariable Long postId,
                              @RequestBody PostDto postDto){
        return postService.updatePost(postId,postDto);
    }
}
