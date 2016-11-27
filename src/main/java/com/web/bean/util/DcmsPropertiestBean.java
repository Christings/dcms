package com.web.bean.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * $文件路径实体类
 *
 * @author 田军兴
 * @date 2016-11-13
 */

@Configuration
@PropertySource(value="classpath:dcms.properties")
public class DcmsPropertiestBean {

    private String fileUploadPath;

    public String getFileUploadPath() {
        return fileUploadPath;
    }

    @Value(value = "${fileUpload.path}")
    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }
}
