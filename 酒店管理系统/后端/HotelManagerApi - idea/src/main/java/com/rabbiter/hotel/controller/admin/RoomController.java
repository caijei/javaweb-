package com.rabbiter.hotel.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Order;
import com.rabbiter.hotel.domain.Room;
import com.rabbiter.hotel.dto.AdminReturnRoomDTO;
import com.rabbiter.hotel.dto.DateSectionDTO;
import com.rabbiter.hotel.service.OrderService;
import com.rabbiter.hotel.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 房间管理控制器，提供管理端对房间的操作，如查看、添加、修改、删除房间等。
 */
@RestController("adminRoomController")  // 标注为 REST 控制器，命名为 "adminRoomController"
@RequestMapping("/admin")  // 定义基础请求路径为 "/admin"
public class RoomController {

    @Resource  // 自动注入 RoomService
    private RoomService roomService;

    @Resource  // 自动注入 OrderService
    private OrderService orderService;

    /**
     * 获取房间列表
     * @return 返回所有房间的列表
     */
    @GetMapping("/listRooms")
    public CommonResult<List<Room>> listRooms() {
        CommonResult<List<Room>> commonResult = new CommonResult<>();

        List<Room> roomList = roomService.list();  // 获取所有房间

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());  // 设置成功状态码
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());  // 设置成功消息
        commonResult.setData(roomList);  // 设置返回的房间列表

        return commonResult;
    }

    /**
     * 获取房间详细信息
     * @param roomId 房间ID
     * @return 返回房间的详细信息
     */
    @PostMapping(value = "/roomDetail")
    public CommonResult<AdminReturnRoomDTO> roomDetail(@RequestParam("roomId") Integer roomId) {
        CommonResult<AdminReturnRoomDTO> commonResult = new CommonResult<>();

        AdminReturnRoomDTO returnRoomDTO = roomService.adminRoomDetail(roomId);  // 获取房间详细信息

        commonResult.setData(returnRoomDTO);  // 设置返回的房间详细信息
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());  // 设置成功状态码
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());  // 设置成功消息
        return commonResult;
    }

    /**
     * 添加新房间
     * @param room 房间信息
     * @return 添加房间的结果
     */
    @PostMapping("/addRoom")
    public CommonResult<String> addRoom(@RequestBody Room room) {
        CommonResult<String> commonResult = new CommonResult<>();

        // 检查房间号是否已存在
        Room roomByNumber = roomService.getOne(new QueryWrapper<Room>().eq("number", room.getNumber()));
        if(roomByNumber != null) {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("房间号已存在");
            return commonResult;
        }

        // 保存新房间信息
        boolean result = roomService.save(room);

        // 返回操作结果
        if (result) {
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("添加房间成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("添加房间失败");
        }

        return commonResult;
    }

    /**
     * 修改房间信息
     * @param room 修改后的房间信息
     * @return 修改房间信息的结果
     */
    @PostMapping("/updateRoom")
    public CommonResult<String> updateRoom(@RequestBody Room room) {
        CommonResult<String> commonResult = new CommonResult<>();

        // 检查房间号是否已存在
        Room roomByNumber = roomService.getOne(new QueryWrapper<Room>().eq("number", room.getNumber()).ne("id", room.getId()));
        if(roomByNumber != null) {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("房间号已存在");
            return commonResult;
        }

        // 更新房间信息
        boolean result = roomService.updateById(room);

        // 返回操作结果
        if (result) {
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("修改房间信息成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("修改房间信息失败");
        }

        return commonResult;
    }

    /**
     * 删除房间
     * @param roomId 房间ID
     * @return 删除房间的结果
     */
    @PostMapping("/deleteRoom")
    public CommonResult<String> deleteRoom(@RequestParam("roomId") Integer roomId) {
        CommonResult<String> commonResult = new CommonResult<>();

        // 移除与房间关联的所有订单
        orderService.remove(new QueryWrapper<Order>().eq("room_id", roomId));

        // 删除房间
        boolean result = roomService.removeById(roomId);

        // 返回操作结果
        if (result) {
            // 如果房间删除成功，确保所有关联的订单也被删除
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("room_id", roomId);
            orderService.remove(queryWrapper);

            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("删除房间成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("删除房间失败");
        }

        return commonResult;
    }
}
