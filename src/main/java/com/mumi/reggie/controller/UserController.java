package com.mumi.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mumi.reggie.common.R;
import com.mumi.reggie.entity.User;
import com.mumi.reggie.service.UserService;
import com.mumi.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.nickname}")
    private String nickname;

    @Value("${spring.mail.username}")
    private String sender;

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

    @PostMapping("/sendEmail")
    public R<String> emailLogin(@RequestBody User user) {
        String email = user.getEmail();

        if(StringUtils.isNotEmpty(email)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(nickname + '<' + sender + '>');
            message.setTo(email);
            message.setSubject("你的邮件验证码");
            // 发送邮件

            String content = "【验证码】您的验证码为：" + code + " 。 验证码五分钟内有效，逾期作废。\n\n\n";

            message.setText(content);

            mailSender.send(message);

            return R.success("验证码邮件发送成功");
        }

        return R.error("邮件发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, Object> map, HttpSession httpSession) {
        log.info(map.toString());
        // 判断验证码是否正确
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        Object codeSession = httpSession.getAttribute(phone);

        if (code.equals(codeSession)) {
            // 判断手机号是否存在在数据库中
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            
            if(user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }

            httpSession.setAttribute("user", user.getId());

            return R.success(user);
        }


        return R.error("登录失败");
    }
}
