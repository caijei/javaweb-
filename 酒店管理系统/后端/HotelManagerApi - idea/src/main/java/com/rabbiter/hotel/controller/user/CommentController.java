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

@RestController  // 声明为 REST 控制器，处理 "/user" 路径下的请求
@RequestMapping("/user")  // 定义请求路径前缀为 "/user"
public class CommentController {

    @Resource  // 自动注入 CommentService
    private CommentService commentService;

    /**
     * 发布用户评论
     * @param commentDTO 包含评论信息的 DTO 对象
     * @return 发布评论结果，包含成功或失败的信息
     */
    @PostMapping("/publishComment")
    public CommonResult<String> publishComment(@RequestBody CommentDTO commentDTO) {
        CommonResult<String> commonResult = new CommonResult<>();

        // 从 session 中获取当前登录的用户信息
        User user = (User) WebUtils.getSession().getAttribute("loginUser");

        // 检查用户状态，如果未在该店消费过，则禁止评论
        if (1 != user.getState()) {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("评价失败，您还没在该店消费过!");
        } else {
            // 将评论数据从 DTO 转换为 Comment 实体
            Comment comment = new Comment();
            BeanUtils.copyProperties(commentDTO, comment);
            comment.setUserId(user.getId());  // 设置用户 ID

            // 保存评论到数据库
            commentService.save(comment);

            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("评价成功!");
        }

        return commonResult;  // 返回结果
    }
}
