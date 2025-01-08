package com.rabbiter.hotel.controller.user;

import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Comment;
import com.rabbiter.hotel.domain.User;
import com.rabbiter.hotel.dto.CommentDTO;
import com.rabbiter.hotel.service.CommentService;
import com.rabbiter.hotel.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/publishComment")
    public CommonResult<String> publishComment(@RequestBody CommentDTO commentDTO) {
        CommonResult<String> commonResult = new CommonResult<>();

        User user = (User) WebUtils.getSession().getAttribute("loginUser");

        if (1 != user.getState()) {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("评价失败，您还没在该店消费过!");
        }
        else {
            Comment comment = new Comment();
            BeanUtils.copyProperties(commentDTO, comment);
            comment.setUserId(user.getId());

            commentService.save(comment);

            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("评价成功!");
        }

        return commonResult;
    }

}
