package com.clouddo.file.common.service.impl;

import com.clouddo.file.common.constatns.FileTypeConstatns;
import com.clouddo.file.common.dto.CloudFileDTO;
import com.clouddo.file.common.mapper.CloudFileMapper;
import com.clouddo.file.common.model.CloudFileDO;
import com.clouddo.file.common.service.CloudFileService;
import com.clouddo.file.common.util.ImageUtil;
import com.mongodb.client.gridfs.GridFSBuckets;
import net.coobird.thumbnailator.Thumbnails;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件存储实现类
 * @author zhongming
 * @since 3.0
 * 2018/7/24上午11:16
 */
@Service
public class CloudFileServiceMongoImpl implements CloudFileService {

    @Autowired
    private CloudFileMapper cloudFileMapper;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * MongoDB工厂
     */
    @Autowired
    MongoDbFactory dbFactory ;

    /**
     * mogon存储文件大小的限值，大小为16M
     */
    public static final Long FILE_SIZE_LIMIT = 16777216L;

    /**
     * 保存文件对象
     * @param cloudFileDTO 文件信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CloudFileDO saveFile(CloudFileDTO cloudFileDTO) {
        //将文件保存到mongoDB中
        ObjectId objectId = this.gridFsTemplate.store(cloudFileDTO.getInputStream(), cloudFileDTO.getCloudFileDO().getFileName());
        cloudFileDTO.getCloudFileDO().setDbId(objectId.toString());
        //将文件信息保存到数据库
        if(StringUtils.isEmpty(cloudFileDTO.getCloudFileDO().getType())) {
            cloudFileDTO.getCloudFileDO().setType(FileTypeConstatns.TEST.getValue());
        }
        List<CloudFileDO> cloudFileDOList = new ArrayList<CloudFileDO>(1);
        cloudFileDOList.add(cloudFileDTO.getCloudFileDO());
        this.cloudFileMapper.batchInsert(cloudFileDOList);
        return cloudFileDTO.getCloudFileDO();
    }

    /**
     * 保存文件信息
     * @param multipartFile
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CloudFileDO saveFile(MultipartFile multipartFile) throws IOException {
        CloudFileDTO cloudFileDTO = CloudFileDTO.create(multipartFile);
        return this.saveFile(cloudFileDTO);
    }


    /**
     * 删除文件
     * @param cloudFileDO 文件信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CloudFileDO deleteFile(CloudFileDO cloudFileDO) {
        if(cloudFileDO == null || StringUtils.isEmpty(cloudFileDO.getFileId())) {
            return null;
        }
        return this.deleteFile(cloudFileDO.getFileId());
    }

    /**
     * 删除文件
     * @param id 文件ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CloudFileDO deleteFile(String id) {
        if(!StringUtils.isEmpty(id)) {
            //通过ID从数据库中查询文件信息
            CloudFileDO cloudFileDO = new CloudFileDO();
            cloudFileDO.setFileId(id);
            cloudFileDO = this.cloudFileMapper.get(cloudFileDO);
            if(cloudFileDO != null) {
                //从文件数据库删除文件信息
                this.gridFsTemplate.delete(new Query().addCriteria(Criteria.where("_id").is(cloudFileDO.getDbId())));
                //从文件信息数据库删除文件信息
                List<CloudFileDO> cloudFileDOList = new ArrayList<CloudFileDO>(1);
                cloudFileDOList.add(cloudFileDO);
                this.cloudFileMapper.delete(cloudFileDOList);
                return cloudFileDO;
            }
        }
        return null;
    }

    /**
     * 文件下载接口
     * @param id
     * @return
     */
    @Override
    public CloudFileDTO download(String id) {
        //1、从数据库中查询文件信息
        CloudFileDO cloudFileDO = new CloudFileDO();
        cloudFileDO.setFileId(id);
        cloudFileDO = this.cloudFileMapper.get(cloudFileDO);
        //2、根据dbid从文件数据库中查询文件
        if(cloudFileDO != null) {
            return new CloudFileDTO(cloudFileDO, GridFSBuckets.create(dbFactory.getDb()).openDownloadStream(new ObjectId(cloudFileDO.getDbId())));
        }
        return null;
    }

    /**
     * 图片显示接口
     * 压缩图片
     * @param id 图片ID
     * @param width 压缩后的宽度
     * @return
     */
    @Override
    public void showImage(String id, OutputStream outputStream, Integer width, Integer height) throws IOException {
        CloudFileDTO cloudFileDTO = this.download(id);
        //对图片进行压缩
        if(cloudFileDTO != null && cloudFileDTO.getInputStream() != null) {
           if(width == null) {
               FileCopyUtils.copy(cloudFileDTO.getInputStream(), outputStream);
           } else if(height == null) {
               ImageUtil.compress(cloudFileDTO.getInputStream(), outputStream, width);
           } else {
               ImageUtil.compress(cloudFileDTO.getInputStream(), outputStream, width, height);
           }
        }
    }

    /**
     * 图片显示接口
     * @param id 图片ID
     * @return
     */
    @Override
    public void showImage(String id, OutputStream outputStream) throws IOException {
        FileCopyUtils.copy(this.download(id).getInputStream(), outputStream);
    }
}
