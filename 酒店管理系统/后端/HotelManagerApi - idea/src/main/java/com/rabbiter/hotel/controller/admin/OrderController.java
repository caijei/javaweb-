package com.rabbiter.hotel.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Order;
import com.rabbiter.hotel.domain.User;
import com.rabbiter.hotel.service.OrderService;
import com.rabbiter.hotel.service.RoomService;
import com.rabbiter.hotel.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单管理控制器，提供管理端对订单的处理功能，如查看、退订、办理入住等。
 */
@RestController("adminOrderController")  // 标注为 REST 控制器，命名为 "adminOrderController"
@RequestMapping("/admin")  // 定义基础请求路径为 "/admin"
public class OrderController {

    @Resource  // 自动注入 OrderService
    private OrderService orderService;

    @Resource  // 自动注入 RoomService
    private RoomService roomService;

    @Resource  // 自动注入 UserService
    private UserService userService;

    /**
     * 获取订单列表，根据订单状态 flags 进行筛选
     * @param flags 订单状态标志列表，用于过滤订单
     * @return 返回符合条件的订单列表
     */
    @GetMapping("/listOrders")
    public CommonResult<List<Order>> listOrders(@RequestParam("orderFlags") List<Integer> flags) {
        CommonResult<List<Order>> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.in("flag", flags);  // 根据订单状态 flags 过滤
        List<Order> userList = orderService.list(queryWrapper);  // 获取符合条件的订单列表

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());  // 设置成功状态码
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());  // 设置成功消息
        commonResult.setData(userList);  // 设置返回的数据列表

        return commonResult;
    }

    /**
     * 退订订单
     * @param orderId 订单ID
     * @return 退订结果
     */
    @PostMapping("/unsubscribe")
    public CommonResult<String> unsubscribe(@RequestParam("orderId") Integer orderId) {
        CommonResult<String> commonResult = new CommonResult<>();

        Order order = orderService.getById(orderId);  // 根据订单ID获取订单
        order.setFlag(2);  // 设置订单状态为已退订（flag=2）
        boolean result = orderService.updateById(order);  // 更新订单信息

        // 返回结果，根据操作是否成功设置状态和消息
        if (result) {
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("退订成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("退订失败");
        }

        return commonResult;
    }

    /**
     * 办理入住操作
     * @param orderId 订单ID
     * @return 办理入住结果
     */
    @PostMapping("/handle")
    public CommonResult<String> handle(@RequestParam("orderId") Integer orderId) {
        CommonResult<String> commonResult = new CommonResult<>();

        Order order = orderService.getById(orderId);  // 获取订单信息
        order.setFlag(1);  // 设置订单状态为已入住（flag=1）
        boolean result = orderService.updateById(order);  // 更新订单状态

        // 办理入住成功后处理相关操作：预定房间，积分增加
        if (result) {
            roomService.bookRoom(order.getRoomId());  // 预定房间
            User user = userService.getById(order.getUserId());  // 获取用户信息
            int jifen = (int) (user.getJifen() + order.getRealPrice());  // 计算并增加积分
            user.setJifen(jifen);
            userService.updateById(user);  // 更新用户积分

            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("办理入住成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("办理入住失败");
        }

        return commonResult;
    }
}
