package com.clouddo.news.server.service.impl;


import com.cloudd.commons.auth.model.User;
import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.news.server.dto.CloudCommentDTO;
import com.clouddo.news.server.dto.CloudFollowDTO;
import com.clouddo.news.server.dto.CloudNewsDTO;
import com.clouddo.news.server.feign.UserService;
import com.clouddo.news.server.mapper.CloudNewsMapper;
import com.clouddo.news.server.model.CloudNews;
import com.clouddo.news.server.service.CloudNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;


/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
public class CloudNewsServiceImpl implements CloudNewsService {
	
	@Resource
	private CloudNewsMapper cloudNewsMapper;

	@Autowired
	private UserService userService;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<CloudNewsDTO> list(Map<String, Object> parameterSet) {
        List<CloudNewsDTO> cloudNewsDTOList = this.cloudNewsMapper.list(parameterSet);
        //获取创建者、修改人信息
        Set<String> userIdList = new HashSet<String>();
        for(CloudNewsDTO cloudNewsDTO : cloudNewsDTOList) {
            if(!StringUtils.isEmpty(cloudNewsDTO.getAuthorId())) {
                userIdList.add(cloudNewsDTO.getAuthorId());
            }
            if(!StringUtils.isEmpty(cloudNewsDTO.getModifierId())) {
                userIdList.add(cloudNewsDTO.getModifierId());
            }
        }
        //查询用户
        Map<String, User> userMap = this.mapUserById(userIdList);
        if(userMap != null) {
            //遍历新闻数据,添加用户信息
            for(CloudNewsDTO cloudNewsDTO : cloudNewsDTOList) {
                cloudNewsDTO.setAuthor(userMap.get(cloudNewsDTO.getAuthorId()));
                cloudNewsDTO.setModifier(userMap.get(cloudNewsDTO.getModifier()));
            }
        }
        return cloudNewsDTOList;
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<CloudNews> deleteObjects) {
        int deleteNum = this.cloudNewsMapper.delete(deleteObjects);
        return deleteNum;
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(String id) {
        List<CloudNews> cloudNewsList = new ArrayList<CloudNews>();
        CloudNews cloudNews = new CloudNews();
        cloudNews.setNewsId(id);
        cloudNewsList.add(cloudNews);
        return this.cloudNewsMapper.delete(cloudNewsList);
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveOrUpdate(CloudNews object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getNewsId() == null || "".equals(object.getNewsId())) {
            object.setNewsId(UUIDGenerator.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            CloudNews object1 = this.cloudNewsMapper.get(object);
            if(object1 == null) {
                num = this.insert(object);
                returnData.put("message", "insert");
                returnData.put("number", num);
            } else {
                num = this.update(object);
                returnData.put("message", "update");
                returnData.put("number", num);
            }
        }
        return returnData;
    }

    /**
    * 批量插入
    * @param insertObjects 要插入的实体集合
    * @return 插入的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsert(List<CloudNews> insertObjects) {
        return this.cloudNewsMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(CloudNews object) {
    List<CloudNews> objects = new ArrayList<CloudNews>();
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(CloudNews object) {
    List<CloudNews> objects = new ArrayList<CloudNews>();
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdate(List<CloudNews> objects) {
        return this.cloudNewsMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public CloudNewsDTO get(CloudNews object) {
        CloudNewsDTO cloudNewsDTO = this.cloudNewsMapper.get(object);
        if(cloudNewsDTO == null) {
            return null;
        }
        //遍历对象获取用户信息
        Set<String> userIdList = new HashSet<String>();
        //添加作者信息
        if(!StringUtils.isEmpty(cloudNewsDTO.getAuthorId())) {
            userIdList.add(cloudNewsDTO.getAuthorId());
        }
        //添加修改者ID
        if(StringUtils.isEmpty(cloudNewsDTO.getModifierId())) {
            userIdList.add(cloudNewsDTO.getModifierId());
        }
        //获取评论信息的人员信息
        //TODO 待完善 如果评论跟帖数量多会导致性能下降，考虑新闻、评论分两次请求查询，评论采用分页的方式
        if(cloudNewsDTO.getCloudCommentDTOList() == null || cloudNewsDTO.getCloudCommentDTOList().size() == 0 || StringUtils.isEmpty(cloudNewsDTO.getCloudCommentDTOList().get(0).getCommentId())) {
            cloudNewsDTO.setCloudCommentDTOList(null);
        } else {
            //遍历跟帖信息
            for(CloudCommentDTO cloudCommentDTO : cloudNewsDTO.getCloudCommentDTOList()) {
                userIdList.add(cloudCommentDTO.getUserId());
                //获取跟帖信息对应的人员信息
                if(cloudCommentDTO.getCloudFollowList() == null || cloudCommentDTO.getCloudFollowList().size() == 0 || StringUtils.isEmpty(cloudCommentDTO.getCloudFollowList().get(0).getFollowId())) {
                    cloudCommentDTO.setCloudFollowList(null);
                } else {
                    for(CloudFollowDTO cloudFollowDTO : cloudCommentDTO.getCloudFollowList()) {
                        userIdList.add(cloudFollowDTO.getUserId());
                    }
                }
            }
        }
        //查询人员信息
        Map<String, User> userMap = this.mapUserById(userIdList);
        if(userMap != null) {
            cloudNewsDTO.setAuthor(userMap.get(cloudNewsDTO.getAuthorId()));
            cloudNewsDTO.setModifier(userMap.get(cloudNewsDTO.getModifierId()));
            if(cloudNewsDTO.getCloudCommentDTOList() != null) {
                //遍历评论信息设置人员信息
                for(CloudCommentDTO cloudCommentDTO : cloudNewsDTO.getCloudCommentDTOList()) {
                    cloudCommentDTO.setCreateUser(userMap.get(cloudCommentDTO.getUserId()));
                    if(cloudCommentDTO.getCloudFollowList() != null) {
                        //遍历跟帖信息设置跟帖人
                        for(CloudFollowDTO cloudFollowDTO : cloudCommentDTO.getCloudFollowList()) {
                            cloudFollowDTO.setCreateUser(userMap.get(cloudFollowDTO.getUserId()));
                        }
                    }
                }
            }
        }

        return cloudNewsDTO;
    }


    /**
     * 通过用户ID列表获取用户信息
     * 将用户已用户ID：用户放入map中
     * @param userIdList
     * @return
     */
    private Map<String, User> mapUserById(Set<String> userIdList) {
        //查询用户
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userIdList", userIdList);
        Result<List<User>> result = this.userService.list(parameters);
        List<User> userList = result == null ? null : result.getData();
        if(userList != null) {
            //遍历用户,使用 map<userId, user>存储用户信息
            Map<String, User> userMap = new HashMap<String, User>(0);
            for(User user : userList) {
                userMap.put(user.getUserId(), user);
            }
            return userMap;
        }
        return null;
    }
}






