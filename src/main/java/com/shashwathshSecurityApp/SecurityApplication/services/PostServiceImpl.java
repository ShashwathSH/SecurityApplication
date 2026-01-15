package com.shashwathshSecurityApp.SecurityApplication.services;

import com.shashwathshSecurityApp.SecurityApplication.dtos.PostDto;
import com.shashwathshSecurityApp.SecurityApplication.entities.PostEntity;
import com.shashwathshSecurityApp.SecurityApplication.entities.User;
import com.shashwathshSecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.shashwathshSecurityApp.SecurityApplication.repositories.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@ToString
public class PostServiceImpl implements PostService{

    PostRepository postRepository;
    ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity,PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto inputPost) {
        PostEntity post = modelMapper.map(inputPost,PostEntity.class);
        PostEntity savedPost = postRepository.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto getPostById(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("User {}",user);
        return postRepository.findById(id)
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id: " + id));
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {

        PostEntity olderPost = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post not found with id: " + postId));
        postDto.setId(postId);
        modelMapper.map(postDto,olderPost);
        PostEntity savedPost = postRepository.save(olderPost);
        return modelMapper.map(savedPost, PostDto.class);
    }
}
