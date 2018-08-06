package com.clouddo.file.common.dto;

import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.commons.common.util.security.MD5Utils;
import com.clouddo.file.common.model.CloudFileDO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件实体传输层
 * @author zhongming
 * @since 3.0
 * 2018/7/24上午11:07
 */
public class CloudFileDTO implements Serializable {

    private static final long serialVersionUID = -6193612235605073958L;

    private CloudFileDO cloudFileDO;

    private InputStream inputStream;


    /**
     * 创建文件信息
     * @param multipartFile
     * @return
     */
    public static CloudFileDTO create(MultipartFile multipartFile) throws IOException {
        CloudFileDTO cloudFileDTO = new CloudFileDTO();
        cloudFileDTO.setInputStream(multipartFile.getInputStream());
        //创建文件数据库信息
        CloudFileDO cloudFileDO = new CloudFileDO();
        cloudFileDO.setFileId(UUIDGenerator.getUUID());
        cloudFileDO.setContentType(multipartFile.getContentType());
        cloudFileDO.setCreateTime(new Date());
        cloudFileDO.setFileName(multipartFile.getOriginalFilename());
        cloudFileDO.setSize(multipartFile.getSize());
        cloudFileDO.setMd5(MD5Utils.md5(multipartFile.getInputStream()));
        cloudFileDTO.setCloudFileDO(cloudFileDO);
        return cloudFileDTO;
    }


    public CloudFileDTO() {
    }

    public CloudFileDTO(CloudFileDO cloudFileDO, InputStream inputStream) {
        this.cloudFileDO = cloudFileDO;
        this.inputStream = inputStream;
    }

    public CloudFileDO getCloudFileDO() {
        return cloudFileDO;
    }

    public void setCloudFileDO(CloudFileDO cloudFileDO) {
        this.cloudFileDO = cloudFileDO;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
