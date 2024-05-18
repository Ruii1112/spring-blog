package com.example.controller;

import com.example.domain.Post;
import com.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * postのコントローラクラス
 *
 * @author inoue
 */
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService service;

    @GetMapping()
    public String index(Model model){
        List<Post> postList = service.findAll();
        model.addAttribute("posts", postList);
        return "posts/index";
    }

    @GetMapping("/show")
    public String show(Model model, Integer id){
        Post post = service.load(id);
        model.addAttribute("post", post);
        return "posts/show";
    }
}
