package com.example.controller;

import com.example.domain.Post;
import com.example.form.PostForm;
import com.example.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
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

    /**
     * トップページの表示。
     *
     * @param model postの全情報を格納するmodel
     * @return index.html
     */
    @GetMapping()
    public String index(Model model){
        List<Post> postList = service.findAll();
        model.addAttribute("posts", postList);
        return "posts/index";
    }

    /**
     * 詳細画面の表示。
     *
     * @param model 詳細画面を表示するpostを格納するmodel
     * @param id 詳細画面を表示するpostのid
     * @return show.html
     */
    @GetMapping("/show")
    public String show(Model model, Integer id){
        Post post = service.load(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    /**
     * post作成画面の表示
     *
     * @return create.html
     */
    @GetMapping("/create")
    public String create(PostForm form){
        return "posts/create";
    }

    /**
     * postの保存処理
     *
     * @return 作成したpostの詳細画面へリダイレクト
     */
    @PostMapping("/store")
    public String store(@Validated PostForm form, BindingResult result){
        if(result.hasErrors()){
            return create(form);
        }

        Post post = new Post();

        BeanUtils.copyProperties(form, post);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post = service.create(post);

        return "redirect:/post/show?id=" + post.getId();

    }
}
