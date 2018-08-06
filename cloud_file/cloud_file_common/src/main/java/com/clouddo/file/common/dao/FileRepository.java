package com.clouddo.file.common.dao;

import com.clouddo.file.common.dto.CloudFileDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 存储库 FileRepository
 * 用于存储小文件（小于16M）
 * @author zhongming
 * @since 3.0
 * 2018/7/24上午11:13
 */
public interface FileRepository extends MongoRepository<CloudFileDTO, String> {

}
