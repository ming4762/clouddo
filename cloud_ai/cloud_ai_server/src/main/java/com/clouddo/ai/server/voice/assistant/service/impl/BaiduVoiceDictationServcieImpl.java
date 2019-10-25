package com.clouddo.ai.server.voice.assistant.service.impl;

import com.baidu.aip.speech.AipSpeech;
import com.bootdo.common.media.audio.AudioWrapper;
import com.cloudd.commons.auth.service.AuthService;
import com.clouddo.ai.server.voice.assistant.service.VoiceDictationServcie;
import com.clouddo.ai.server.voice.assistant.util.BaiduVoiceUtil;
import com.clouddo.commons.common.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhongming
 * @since 3.0
 * 2018/6/8下午4:19
 */
@Service("baiduVoiceDictationServcieImpl")
public class BaiduVoiceDictationServcieImpl extends AuthService implements VoiceDictationServcie {

    //注入百度语音sdk
    @Autowired
    private AipSpeech aipSpeech;

    //语音听写服务
    @Override
    public String voiceDictation(MultipartFile voice) throws Exception {      //转换语音将wav转为pem
        //转换文件为PEM
        String path = this.transWavToPem(voice);
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("cuid", getUserId() == null ? UUIDGenerator.INSTANCE.getUUID() : getUserId());
        options.put("dev_pid", 1536);
        Map<String, Object> result = aipSpeech.asr(path, "pcm", 16000, options).toMap();
        List<String> results = BaiduVoiceUtil.analysisVoiceDictation(result);
        if(results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    /**
     * 将wav转为pem
     * @param voice
     * @return
     * @throws Exception
     */
    private String transWavToPem(MultipartFile voice) throws Exception {
        return AudioWrapper.of(voice.getInputStream())
                .setInputType("wav")
                .setOutputType("pcm")
                .addOption("acodec", "pcm_s16le")
                .addOption("f", "s16le")
                .addOption("ac", "1")
                .addOption("ar", "16000")
                .asFile();
    }
}
