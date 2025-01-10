package com.rabbiter.hotel.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Order;
import com.rabbiter.hotel.domain.Room;
import com.rabbiter.hotel.domain.Type;
import com.rabbiter.hotel.domain.User;
import com.rabbiter.hotel.dto.BookDTO;
import com.rabbiter.hotel.dto.DateSectionDTO;
import com.rabbiter.hotel.dto.ReturnRoomDTO;
import com.rabbiter.hotel.dto.TypeDTO;
import com.rabbiter.hotel.service.OrderService;
import com.rabbiter.hotel.service.RoomService;
import com.rabbiter.hotel.service.TypeService;
import com.rabbiter.hotel.service.UserService;
import com.rabbiter.hotel.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController  // 标注为 REST 控制器
@RequestMapping("/user")  // 请求路径前缀为 "/user"
public class RoomController {

    @Resource  // 自动注入 RoomService
    private RoomService roomService;
    @Resource  // 自动注入 OrderService
    private OrderService orderService;
    @Resource  // 自动注入 TypeService
    private TypeService typeService;
    @Resource  // 自动注入 UserService
    private UserService userService;

    /**
     * 获取指定日期范围内的房间列表
     * @param dateSectionDTO 日期范围数据传输对象
     * @return 包含房间信息的列表
     */
    @PostMapping(value = "/listRoom")
    public CommonResult<List<ReturnRoomDTO>> listRoom(@RequestBody DateSectionDTO dateSectionDTO) {
        CommonResult<List<ReturnRoomDTO>> commonResult = new CommonResult<>();

        // 调用服务获取房间列表
        List<ReturnRoomDTO> list = roomService.listRooms(dateSectionDTO);

        commonResult.setData(list);
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }

    /**
     * 获取房间的详细信息
     * @param roomId 房间 ID
     * @return 包含房间详细信息的对象
     */
    @PostMapping(value = "/roomDetail")
    public CommonResult<ReturnRoomDTO> roomDetail(@RequestParam("roomId") Integer roomId) {
        CommonResult<ReturnRoomDTO> commonResult = new CommonResult<>();

        // 调用服务获取房间详细信息
        ReturnRoomDTO returnRoomDTO = roomService.roomDetail(roomId);
        commonResult.setData(returnRoomDTO);
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }

    /**
     * 预订房间
     * @param bookDTO 预订数据传输对象
     * @return 预订结果
     */
    @PostMapping("/bookRoom")
    public CommonResult<String> bookRoom(@RequestBody BookDTO bookDTO) {
        CommonResult<String> commonResult = new CommonResult<>();

        // 从 session 中获取当前登录用户信息
        User user = (User) WebUtils.getSession().getAttribute("loginUser");
        if(user == null) {
            commonResult.setData("登录信息过期");
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            return commonResult;
        }

        // 获取房间和房型信息
        Room room = roomService.getById(bookDTO.getRoomId());
        Type type = typeService.getById(room.getType());
        Order order = new Order();
        BeanUtils.copyProperties(bookDTO,  order);
        order.setUserId(user.getId());

        // 计算预订天数及总价格
        int days = (int) Math.ceil((bookDTO.getLeaveTime().getTime() - bookDTO.getInTime().getTime()) / (60 * 60 * 24 * 1000 * 1.0));
        order.setRealPrice(type.getPrice() * days);

        // 保存订单信息
        orderService.save(order);

        // 更新用户状态为已预订
        user.setState(1);
        userService.updateById(user);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData("预订成功!");

        return commonResult;
    }

    /**
     * 根据房型 ID 获取房间列表
     * @param typeDTO 房型数据传输对象
     * @return 包含指定房型的房间列表
     */
    @PostMapping("/listRoomsByTypeId")
    public CommonResult<List<ReturnRoomDTO>> listRoomsByTypeId(@RequestBody TypeDTO typeDTO) {
        CommonResult<List<ReturnRoomDTO>> commonResult = new CommonResult<>();

        // 复制房型 DTO 信息到日期范围 DTO
        DateSectionDTO dateSectionDTO = new DateSectionDTO();
        BeanUtils.copyProperties(typeDTO, dateSectionDTO);

        // 调用服务获取房间列表
        List<ReturnRoomDTO> roomList = roomService.listRooms(dateSectionDTO);

        // 筛选符合房型 ID 的房间
        List<ReturnRoomDTO> returnRoomList = new ArrayList<>();
        if (0 != roomList.size()) {
            for (ReturnRoomDTO room : roomList) {
                if (typeDTO.getTypeId().equals(room.getType().getId())) {
                    returnRoomList.add(room);
                }
            }
        }

        commonResult.setData(returnRoomList);
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }
}
