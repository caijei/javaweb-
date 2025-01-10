package com.rabbiter.hotel.controller.admin;

import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Comment;
import com.rabbiter.hotel.domain.User;
import com.rabbiter.hotel.dto.ReturnCommentDTO;
import com.rabbiter.hotel.dto.ReturnUserDTO;
import com.rabbiter.hotel.service.CommentService;
import com.rabbiter.hotel.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论管理控制器，提供管理端查看评论的功能。
 */
@RestController("adminCommentController")  // 标注为 REST 控制器，命名为 "adminCommentController"
@RequestMapping("/admin")  // 定义基础请求路径为 "/admin"
public class CommentController {

    @Resource  // 自动注入 CommentService
    private CommentService commentService;

    @Resource  // 自动注入 UserService
    private UserService userService;

    /**
     * 获取评论列表的方法
     * @return 返回包含评论及用户信息的列表
     */
    @GetMapping("/listComment")
    public CommonResult<List<ReturnCommentDTO>> listComment() {
        CommonResult<List<ReturnCommentDTO>> commonResult = new CommonResult<>();
        List<ReturnCommentDTO> returnCommentList = new ArrayList<>();

        // 获取所有评论列表
        List<Comment> commentList = commentService.list();

        for (Comment comment : commentList) {
            ReturnCommentDTO commentDTO = new ReturnCommentDTO();

            // 根据评论中的 userId 获取用户信息
            User user = userService.getById(comment.getUserId());
            ReturnUserDTO returnUserDTO = new ReturnUserDTO();
            BeanUtils.copyProperties(user, returnUserDTO);  // 将 User 对象属性拷贝到 ReturnUserDTO

            commentDTO.setComment(comment);  // 设置评论信息
            commentDTO.setUser(returnUserDTO);  // 设置用户信息

            returnCommentList.add(commentDTO);  // 添加到返回列表中
        }

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());  // 设置成功状态码
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());  // 设置成功消息
        commonResult.setData(returnCommentList);  // 设置返回的数据列表

        return commonResult;
    }
}
