package com.mumi.reggie.controller;

import com.mumi.reggie.common.R;
import com.mumi.reggie.entity.User;
import com.mumi.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @PostMapping("/sendMsg")
    public R<String> sendPhoneMsg(@RequestBody User user, HttpSession httpSession) {
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);

            // 将生成的code存储到session中
            httpSession.setAttribute(phone, code);

            return R.success("手机验证码短信发送成功");
        }


        return R.error("短信发送失败");
    }
}
