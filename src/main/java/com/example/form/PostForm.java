package com.example.form;

import jakarta.validation.constraints.NotBlank;

/**
 * postのformクラス
 *
 * @author inoue
 */
public class PostForm {
    /** postの主キー */
    private Integer id;
    /** postのタイトル */
    @NotBlank(message = "タイトルを入力してください")
    private String title;
    /** postの本文 */
    @NotBlank(message = "本文を入力してください")
    private String body;

    @Override
    public String toString() {
        return "PostForm{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
