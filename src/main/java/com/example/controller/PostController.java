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
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping()
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
    @GetMapping("/show/{id}")
    public String show(Model model, @PathVariable Integer id){
        Post post = service.load(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    /**
     * post作成画面の表示
     *
     * @param form postのフォーム
     * @return create.html
     */
    @GetMapping("/post/create")
    public String create(PostForm form){
        return "posts/create";
    }

    /**
     * postの保存処理
     *
     * @return 作成したpostの詳細画面へリダイレクト
     */
    @PostMapping("/post/store")
    public String store(@Validated PostForm form, BindingResult result){
        if(result.hasErrors()){
            return create(form);
        }

        Post post = new Post();

        BeanUtils.copyProperties(form, post);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post = service.create(post);

        return "redirect:/show/" + post.getId();
    }

    /**
     * post編集画面の表示.
     *
     * @param id 編集したいpostのid
     * @param model post情報を格納するmodel
     * @param form postのform
     * @return edit.html
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, PostForm form){
        Post post = service.load(id);
        form.setTitle(post.getTitle());
        form.setBody(post.getBody());
        model.addAttribute("post", post);
        return "posts/edit";
    }

    /**
     * postの更新処理
     * @param id 更新するpostのid
     * @param form postのform
     * @param result バリデーション結果
     * @param model editメソッドに渡す用のmodel
     * @return 更新したpostのshow.html
     */
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @Validated PostForm form, BindingResult result, Model model){
        if(result.hasErrors()){
            return edit(id, model, form);
        }

        Post post = service.load(id);
        post.setTitle(form.getTitle());
        post.setBody(form.getBody());
        post.setUpdatedAt(LocalDateTime.now());
        post = service.update(post);

        return "redirect:/show/" + post.getId();
    }

    /**
     * postの削除処理.
     *
     * @param id 削除するpostのid
     * @return 一覧画面へリダイレクト
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Post post = service.load(id);
        post.setDeletedAt(LocalDateTime.now());
        service.delete(post);
        return "redirect:/";
    }
}
