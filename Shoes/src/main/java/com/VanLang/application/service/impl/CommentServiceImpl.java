package com.VanLang.application.service.impl;

import com.VanLang.application.entity.User;
import com.VanLang.application.exception.InternalServerException;
import com.VanLang.application.model.request.CreateCommentPostRequest;
import com.VanLang.application.model.request.CreateCommentProductRequest;
import com.VanLang.application.repository.CommentRepository;
import com.VanLang.application.service.CommentService;
import com.VanLang.application.entity.Comment;
import com.VanLang.application.entity.Post;
import com.VanLang.application.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createCommentPost(CreateCommentPostRequest createCommentPostRequest, long userId) {
        Comment comment = new Comment();
        Post post = new Post();
        post.setId(createCommentPostRequest.getPostId());
        comment.setPost(post);
        User user = new User();
        user.setId(userId);
        comment.setUser(user);
        comment.setContent(createCommentPostRequest.getContent());
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new InternalServerException("Có lỗi trong quá trình bình luận!");
        }
        return comment;
    }

    @Override
    public Comment createCommentProduct(CreateCommentProductRequest createCommentProductRequest, long userId) {
        Comment comment = new Comment();
        comment.setContent(createCommentProductRequest.getContent());
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        User user = new User();
        user.setId(userId);
        comment.setUser(user);
        Product product = new Product();
        product.setId(createCommentProductRequest.getProductId());
        comment.setProduct(product);
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new InternalServerException("Có lỗi trong quá trình bình luận!");
        }
        return comment;
    }
}
