package com.example.repository;

import com.example.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * postsテーブル用のリポジトリクラス
 *
 * @author inoue
 */
@Repository
public class PostRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Post> POST_ROW_MAPPER = (rs, i) -> {
        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setTitle(rs.getString("title"));
        post.setBody(rs.getString("body"));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        if(rs.getTimestamp("deleted_at") != null) {
            post.setDeletedAt(rs.getTimestamp("deleted_at").toLocalDateTime());
        }
        return post;
    };

    /**
     * 主キー検索。
     *
     * @param id 主キー
     * @return 主キーに基づいたpost情報
     */
    public Post load(Integer id){
        String sql = "SELECT id, title, body, created_at, updated_at, deleted_at FROM posts WHERE id=:id;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        Post post = template.queryForObject(sql, param, POST_ROW_MAPPER);
        return post;
    }

    /**
     * 全件検索。
     *
     * @return postsの全情報
     */
    public List<Post> findAll(){
        String sql = "SELECT id, title, body, created_at, updated_at, deleted_at FROM posts;";
        List<Post> postList = template.query(sql, POST_ROW_MAPPER);
        return postList;
    }
}
