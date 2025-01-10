package com.rabbiter.hotel.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Comment;
import com.rabbiter.hotel.domain.Order;
import com.rabbiter.hotel.domain.User;
import com.rabbiter.hotel.service.CommentService;
import com.rabbiter.hotel.service.OrderService;
import com.rabbiter.hotel.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理控制器，提供管理端对用户的操作，如查看、删除、修改用户信息等。
 */
@RestController("adminUserController")  // 标注为 REST 控制器，命名为 "adminUserController"
@RequestMapping("/admin")  // 定义基础请求路径为 "/admin"
public class UserController {

    @Resource  // 自动注入 UserService
    private UserService userService;

    @Resource  // 自动注入 OrderService
    private OrderService orderService;

    @Resource  // 自动注入 CommentService
    private CommentService commentService;

    /**
     * 获取用户列表
     * @return 返回所有用户的列表
     */
    @GetMapping("/listUsers")
    public CommonResult<List<User>> listUsers() {
        CommonResult<List<User>> commonResult = new CommonResult<>();

        List<User> userList = userService.list();  // 获取所有用户

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());  // 设置成功状态码
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());  // 设置成功消息
        commonResult.setData(userList);  // 设置返回的用户列表

        return commonResult;
    }

    /**
     * 删除用户
     * @param userId 用户ID
     * @return 删除操作的结果
     */
    @PostMapping("/deleteUser")
    public CommonResult<String> deleteUser(@RequestParam("userId") Integer userId) {
        CommonResult<String> commonResult = new CommonResult<>();

        // 移除与用户关联的所有订单
        orderService.remove(new QueryWrapper<Order>().eq("user_id", userId));

        // 移除与用户关联的所有评论
        commentService.remove(new QueryWrapper<Comment>().eq("user_id", userId));

        // 删除用户
        boolean result = userService.removeById(userId);

        // 返回操作结果
        if (result) {
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("删除成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("删除失败");
        }

        return commonResult;
    }

    /**
     * 修改用户信息
     * @param user 修改后的用户信息
     * @return 修改用户信息的结果
     */
    @PostMapping("/updateUser")
    public CommonResult<String> updateUser(@RequestBody User user) {
        CommonResult<String> commonResult = new CommonResult<>();

        // 更新用户信息
        boolean result = userService.updateById(user);

        // 返回操作结果
        if (result) {
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("修改成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("修改失败");
        }

        return commonResult;
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 返回指定用户的信息
     */
    @PostMapping("/getUserById")
    public CommonResult<User> getUserById(@RequestParam("userId") Integer userId) {
        CommonResult<User> commonResult = new CommonResult<>();

        // 根据用户ID获取用户信息
        User user = userService.getById(userId);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());  // 设置成功状态码
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());  // 设置成功消息
        commonResult.setData(user);  // 设置返回的用户信息

        return commonResult;
    }
}
