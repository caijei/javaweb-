package com.rabbiter.hotel.controller.user;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.User;
import com.rabbiter.hotel.dto.LoginDTO;
import com.rabbiter.hotel.dto.PasswordDTO;
import com.rabbiter.hotel.dto.RegisterDTO;
import com.rabbiter.hotel.dto.ReturnUserDTO;
import com.rabbiter.hotel.service.UserService;
import com.rabbiter.hotel.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户相关操作的控制器类，包含注册、登录、登出、获取用户详情和更新密码等功能。
 */
@RestController  // 标注为 REST 控制器
@RequestMapping("/user")  // 定义基础请求路径为 "/user"
public class UserController {

    @Resource  // 自动注入 UserService
    private UserService userService;

    /**
     * 用户注册方法
     * @param registerDTO 包含注册信息的数据传输对象
     * @return 返回注册结果
     */
    @PostMapping(value = "/register")
    public CommonResult<String> register(@RequestBody RegisterDTO registerDTO) {
        // 验证邮箱是否唯一
        long count = userService.count(new QueryWrapper<User>().eq("email", registerDTO.getEmail()));
        if (count > 0) {
            // 邮箱已存在
            CommonResult<String> commonResult = new CommonResult<>();
            commonResult.setData("邮箱已存在");
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            return commonResult;
        }

        CommonResult<String> commonResult = new CommonResult<>();
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);  // 将 DTO 属性拷贝到 User 对象
        user.setPassword(SecureUtil.md5(registerDTO.getPassword()));  // 密码加密

        userService.save(user);  // 保存用户信息

        commonResult.setData("注册成功");
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }

    /**
     * 用户登录方法
     * @param loginDTO 包含登录信息的数据传输对象
     * @return 返回登录结果
     */
    @PostMapping(value = "/login")
    public CommonResult<String> login(@RequestBody LoginDTO loginDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email", loginDTO.getEmail());
        String md5Password = SecureUtil.md5(loginDTO.getPassword());  // 密码加密
        queryWrapper.eq("password", md5Password);
        User user = userService.getBaseMapper().selectOne(queryWrapper);  // 查询用户

        if (null != user) {
            WebUtils.getSession().setAttribute("loginUser", user);  // 将用户信息存入 session
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("登录成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("账号密码错误，请重试");
        }

        return commonResult;
    }

    /**
     * 用户登出方法
     * @return 返回登出结果
     */
    @GetMapping("/logout")
    public CommonResult<String> logout() {
        CommonResult<String> commonResult = new CommonResult<>();
        WebUtils.getSession().removeAttribute("loginUser");  // 移除 session 中的用户信息

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData("登出成功!");
        return commonResult;
    }

    /**
     * 获取用户详细信息的方法
     * @return 返回用户详细信息
     */
    @GetMapping("/userDetail")
    public CommonResult<ReturnUserDTO> userDetail() {
        CommonResult<ReturnUserDTO> commonResult = new CommonResult<>();
        ReturnUserDTO returnUser = new ReturnUserDTO();

        User user2 = (User) WebUtils.getSession().getAttribute("loginUser");  // 获取当前登录用户信息
        User user = userService.getById(user2.getId());  // 查询用户详情
        BeanUtils.copyProperties(user, returnUser);  // 将 User 属性拷贝到 DTO

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(returnUser);
        return commonResult;
    }

    /**
     * 更新用户密码的方法
     * @param passwordDTO 包含旧密码和新密码的数据传输对象
     * @return 返回更新密码结果
     */
    @PostMapping("/updatePassword")
    public CommonResult<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        User user2 = (User) WebUtils.getSession().getAttribute("loginUser");  // 获取当前登录用户
        User user = userService.getById(user2.getId());  // 查询用户详情

        String md5OldPassword = SecureUtil.md5(passwordDTO.getOldPassword());  // 加密旧密码
        if (!user.getPassword().equals(md5OldPassword)) {
            // 旧密码不匹配
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("密码错误");
            return commonResult;
        }

        String md5NewPassword = SecureUtil.md5(passwordDTO.getNewPassword());  // 加密新密码
        user.setPassword(md5NewPassword);  // 更新密码
        userService.updateById(user);  // 保存修改

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData("修改密码成功");
        return commonResult;
    }

}
