package com.clouddo.ai.server.service.impl;


import com.clouddo.ai.server.mapper.CloudSlotMapper;
import com.clouddo.ai.server.model.CloudSlotDO;
import com.clouddo.ai.server.pojo.dto.SlotDto;
import com.clouddo.ai.server.service.CloudSlotService;
import com.clouddo.ai.server.voice.assistant.model.baidu.response.Slot;
import com.clouddo.commons.common.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.management.monitor.StringMonitor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
@Transactional
public class CloudSlotServiceImpl implements CloudSlotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudSlotServiceImpl.class);

	@Autowired
	private CloudSlotMapper cloudSlotMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<CloudSlotDO> list(Map<String, Object> parameterSet) {
        return this.cloudSlotMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    public int delete(List<CloudSlotDO> deleteObjects) {
        int deleteNum = this.cloudSlotMapper.delete(deleteObjects);
        return deleteNum;
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    public Map<String, Object> saveOrUpdate(CloudSlotDO object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getSlotId() == null || "".equals(object.getSlotId())) {
            object.setSlotId(UUIDGenerator.INSTANCE.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            CloudSlotDO object1 = this.cloudSlotMapper.get(object);
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
    public int batchInsert(List<CloudSlotDO> insertObjects) {
        return this.cloudSlotMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    public int insert(CloudSlotDO object) {
    List<CloudSlotDO> objects = new ArrayList<CloudSlotDO>();
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    public int update(CloudSlotDO object) {
    List<CloudSlotDO> objects = new ArrayList<CloudSlotDO>();
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    public int batchUpdate(List<CloudSlotDO> objects) {
        return this.cloudSlotMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public CloudSlotDO get(CloudSlotDO object) {
        return this.cloudSlotMapper.get(object);
    }


    /**
     * 使用从百度UNIT获取词槽信息和从数据库获取的词槽信息创建词槽数据传输对象
     * @param solt 从百度UNIT获取的词槽信息
     * @param cloudSlotDO 从数据库获取的词槽信息
     * @return 词槽数据传输对象
     */
    @Override
    public Map<String, Object> createSoltDto(Slot solt, CloudSlotDO cloudSlotDO){
        String message = "";
        Map<String, Object> data = new HashMap<String, Object>();
        //词槽名
        String soltName = solt.getName();
        //词槽值
        String soltVale = solt.getOriginalWord();
        //词槽归一化值
        String normalizedWord = solt.getNormalizedWord();
        //格式化名称
        String formatName = soltName;
        //格式化值
        Object formatValue = normalizedWord;
        //1、如果从数据库中没有找到对应的词槽信息，则格式化名称、格式化值采用百度UNIT词槽信息中的归一化值
        if(cloudSlotDO != null) {
            //2、从cloudSlotDO中获取格式化名称，如果为null，则采用词槽名称作为格式化名称
            if(!StringUtils.isEmpty(cloudSlotDO.getFormatName())) {
                formatName = cloudSlotDO.getFormatName();
            }
            //3、从cloudSlotDO中获取值格式化类、值格式化方法，并对值进行格式化，如果类或方法为null，则使用词槽归一化值作为格式化值
            if(!StringUtils.isEmpty(cloudSlotDO.getFormatValueClass()) && !StringUtils.isEmpty(cloudSlotDO.getFormatValueMethod())) {
                try {
                    //获取格式化类
                    Class clazz = Class.forName(cloudSlotDO.getFormatValueClass());
                    //获取格式化方法
                    Method method = clazz.getMethod(cloudSlotDO.getFormatValueMethod(), String.class);
                    //创建格式化类实例
                    Object object = clazz.newInstance();
                    //执行格式化方法
                    formatValue = method.invoke(object, normalizedWord);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    message = "格式化词槽值失败，未找到格式化类，类限定名：" + cloudSlotDO.getFormatValueClass();
                    LOGGER.warn("格式化词槽值失败，未找到格式化类，类限定名：{}", cloudSlotDO.getFormatValueClass());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    message = "格式化词槽值失败，未找到格式化方法，类限定名："+ cloudSlotDO.getFormatValueClass() +"，方法名：" + cloudSlotDO.getFormatValueMethod();
                    LOGGER.warn("格式化词槽值失败，未找到格式化方法，类限定名：{}，方法名：{}", cloudSlotDO.getFormatValueClass(), cloudSlotDO.getFormatValueMethod());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    message = "格式化词槽值失败，格式化类构造方法为private，类限定名：" + cloudSlotDO.getFormatValueClass();
                    LOGGER.warn("格式化词槽值失败，格式化类构造方法为private，类限定名：{}", cloudSlotDO.getFormatValueClass());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    message = "格式化词槽值失败，格式化类为接口或抽象类，类限定名：" + cloudSlotDO.getFormatValueClass();
                    LOGGER.warn("格式化词槽值失败，格式化类为接口或抽象类，类限定名：{}", cloudSlotDO.getFormatValueClass());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    message = "格式化词槽值失败，格式化方法内部报错，类限定名：" + cloudSlotDO.getFormatValueClass();
                    LOGGER.warn("格式化词槽值失败，格式化方法内部报错，类限定名：{}", cloudSlotDO.getFormatValueClass());
                }
            }

        }
        data.put("data", new SlotDto(soltName, formatName, soltVale, normalizedWord, formatValue));
        data.put("message", message);
        return data;
    }
}