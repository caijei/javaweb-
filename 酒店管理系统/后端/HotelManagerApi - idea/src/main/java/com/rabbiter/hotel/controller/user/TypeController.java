package com.rabbiter.hotel.controller.user;

import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Type;
import com.rabbiter.hotel.service.TypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 控制器类，用于处理房型相关的用户请求。
 */
@RestController  // 标注为 REST 控制器
@RequestMapping("/user")  // 请求路径前缀为 "/user"
public class TypeController {

    @Resource  // 自动注入 TypeService
    private TypeService typeService;

    /**
     * 获取所有房型列表。
     * @return 包含房型列表的结果对象。
     */
    @GetMapping("/listTypes")  // 定义 GET 请求路径为 "/listTypes"
    public CommonResult<List<Type>> listTypes() {
        CommonResult<List<Type>> commonResult = new CommonResult<>();

        // 调用服务获取所有房型列表
        List<Type> list = typeService.list();

        // 设置返回结果数据、状态码及消息
        commonResult.setData(list);
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }
}
