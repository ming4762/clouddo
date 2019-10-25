package com.clouddo.ai.server.voice.assistant.model.baidu;

import com.clouddo.ai.server.model.CloudIntentDO;
import com.clouddo.ai.server.pojo.dto.SlotDto;
import com.clouddo.ai.server.voice.assistant.model.baidu.response.ResponseModel;
import com.clouddo.commons.common.util.UUIDGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百度UNIT
 * 一次对话信息
 * @author zhongming
 * @since 3.0
 * 2018/6/19下午1:43
 */
public class Conversation implements Serializable {

    private static final long serialVersionUID = -1020493198031013010L;

    /**
     * 获取简要信息
     * 用于前后台交互
     * @return
     */
    public Map<String, Object> getSimpleData() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", this.status);
        data.put("message", this.message);
        data.put("conversationId", this.id);
        data.put("bootId", this.bootId);
        data.put("warnMessages", warnMessages);
        data.put("cloudIntent", cloudIntentDO);
        data.put("soltList", soltDtoList);
        return data;
    }

    /**
     * 警告信息
     */
    private List<String> warnMessages;

    /**
     * 意图信息
     */
    private CloudIntentDO cloudIntentDO;

    /**
     * 格式化后的词槽列表
     */
    private List<SlotDto> soltDtoList;

    /**
     * 本次对话的状态
     */
    private String status;

    /**
     * 本次对话的语音
     */
    private String message;

    /**
     * 会话的ID
     */
    private String id;

    /**
     * 本次对话对应的bootId
     */
    private String bootId;

    /**
     * 一次对话的所有请求信息
     */
    private List<RequestModel> requestModelList;

    /**
     * 一次会话的所有回复信息
     */
    private List<ResponseModel> responseModelList;


    /**
     * 构造函数初始化参数
     */
    public Conversation() {
        this.id = UUIDGenerator.INSTANCE.getUUID();
        this.requestModelList = new ArrayList<RequestModel>();
        this.responseModelList = new ArrayList<ResponseModel>();
    }

    public Conversation(String bootId) {
        this();
        this.bootId = bootId;
    }

    /**
     * 添加请求信息
     * @param requestModel
     */
    public void addRequest(RequestModel requestModel) {
        this.requestModelList.add(requestModel);
    }

    /**
     * 添加回复信息
     * @param responseModel
     */
    public void addResponse(ResponseModel responseModel) {
        this.responseModelList.add(responseModel);
    }

    /**
     * 获取最新的请求信息
     * @return
     */
    public RequestModel getNewestRequest() {
        if(this.requestModelList.size() > 0) {
            return this.requestModelList.get(this.requestModelList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 获取最新的回复信息
     * @return
     */
    public ResponseModel getNewestResponse() {
        if(this.responseModelList.size() > 0) {
            return this.responseModelList.get(this.responseModelList.size() - 1);
        } else {
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBootId() {
        return bootId;
    }

    public void setBootId(String bootId) {
        this.bootId = bootId;
    }

    public List<String> getWarnMessages() {
        return warnMessages;
    }

    public void setWarnMessages(List<String> warnMessages) {
        this.warnMessages = warnMessages;
    }

    public CloudIntentDO getCloudIntentDO() {
        return cloudIntentDO;
    }

    public void setCloudIntentDO(CloudIntentDO cloudIntentDO) {
        this.cloudIntentDO = cloudIntentDO;
    }

    public List<SlotDto> getSoltDtoList() {
        return soltDtoList;
    }

    public void setSoltDtoList(List<SlotDto> soltDtoList) {
        this.soltDtoList = soltDtoList;
    }
}









