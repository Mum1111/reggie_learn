package com.mumi.reggie.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="aliyun.oss")
public class AliOSSProperties {
    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private String endpoint;
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private String accessKeyId;

    private String accessKeySecret;

    // 填写Bucket名称，例如examplebucket。
    private String bucketName;
}
