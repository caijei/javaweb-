package com.rabbiter.hotel.dto;

import com.rabbiter.hotel.domain.Comment;

public class ReturnCommentDTO {

    private ReturnUserDTO user;
    private Comment comment;

    public ReturnCommentDTO() {
    }

    public ReturnCommentDTO(ReturnUserDTO user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public ReturnUserDTO getUser() {
        return user;
    }

    public void setUser(ReturnUserDTO user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ReturnCommentDTO{" +
                "user=" + user +
                ", comment=" + comment +
                '}';
    }
}
