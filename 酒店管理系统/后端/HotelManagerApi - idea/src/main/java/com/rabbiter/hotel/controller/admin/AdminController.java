package com.rabbiter.hotel.controller.admin;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Admin;
import com.rabbiter.hotel.dto.AdminLoginDTO;
import com.rabbiter.hotel.service.AdminService;
import com.rabbiter.hotel.util.WebUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("adminAdminController")  // 声明为 REST 控制器，处理 "/admin" 路径下的请求
@RequestMapping("/admin")  // 定义请求路径前缀为 "/admin"
public class AdminController {

    @Resource  // 自动注入 AdminService
    private AdminService adminService;

    /**
     * 处理管理员登录请求
     * @param adminLoginDTO 包含登录用户名和密码的 DTO 对象
     * @return 登录结果，包含成功或失败的信息
     */
    @PostMapping(value = "/login")
    public CommonResult<Admin> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        CommonResult<Admin> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();

        // 根据用户名和密码查询管理员信息
        queryWrapper.eq("admin_name", adminLoginDTO.getUserName());
        String md5Password = SecureUtil.md5(adminLoginDTO.getPassword());  // 使用 MD5 加密密码
        queryWrapper.eq("password", md5Password);
        Admin admin = adminService.getOne(queryWrapper);

        if (null != admin) {
            // 登录成功，保存管理员信息到 session 中
            admin.setAdmin_name(adminLoginDTO.getUserName());
            WebUtils.getSession().setAttribute("loginAdmin", admin);

            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData(admin);
        } else {
            // 登录失败，返回错误信息
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage("账号或密码错误");
            commonResult.setData(null);
        }

        return commonResult;  // 返回结果
    }

    /**
     * 处理管理员登出请求
     * @return 登出结果，包含成功信息
     */
    @GetMapping("/logout")
    public CommonResult<String> logout(){
        CommonResult<String> commonResult = new CommonResult<>();

        // 从 session 中移除管理员登录信息
        WebUtils.getSession().removeAttribute("loginAdmin");

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData("登出成功!");

        return commonResult;  // 返回结果
    }
}
