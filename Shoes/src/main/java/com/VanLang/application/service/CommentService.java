package com.VanLang.application.service;

import com.VanLang.application.model.request.CreateCommentPostRequest;
import com.VanLang.application.model.request.CreateCommentProductRequest;
import com.VanLang.application.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Comment createCommentPost(CreateCommentPostRequest createCommentPostRequest, long userId);
    Comment createCommentProduct(CreateCommentProductRequest createCommentProductRequest, long userId);
}
