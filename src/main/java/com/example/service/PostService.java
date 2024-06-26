package com.example.service;

import com.example.domain.Post;
import com.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * postのサービスクラス
 *
 * @author inoue
 */
@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post load(Integer id){
        return repository.load(id);
    }

    public List<Post> findAll(){
        return repository.findAll();
    }

    public Post create(Post post){
        return repository.save(post);
    }

    public Post update(Post post){
        return repository.save(post);
    }

    public void delete(Post post){
        repository.delete(post);
    }
}
