package com.clouddo.file.common.service;


import com.clouddo.file.common.dto.CloudFileDTO;
import com.clouddo.file.common.model.CloudFileDO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Service层 接口类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
public interface CloudFileService {

    /**
     * 保存文件对象
     * @param cloudFileDTO 文件信息
     * @return
     */
    CloudFileDO saveFile(CloudFileDTO cloudFileDTO);

    /**
     * 保存文件对象
     * @param multipartFile
     * @return
     */
    CloudFileDO saveFile(MultipartFile multipartFile) throws IOException;

    /**
     * 删除文件
     * @param cloudFileDO 文件信息
     * @return
     */
    CloudFileDO deleteFile(CloudFileDO cloudFileDO);

    /**
     * 删除文件
     * @param id 文件ID
     * @return
     */
    CloudFileDO deleteFile(String id);

    /**
     * 查询文件
     * @param id
     * @return
     */
    CloudFileDTO download(String id);

    /**
     * 图片显示接口
     * 压缩图片
     * @param id 图片ID
     * @param width 压缩后的宽度
     * @return
     */
    void showImage(String id, OutputStream outputStream, Integer width, Integer height) throws IOException;

    /**
     * 图片显示接口
     * @param id 图片ID
     * @return
     */
    void showImage(String id, OutputStream outputStream) throws IOException;
	
}