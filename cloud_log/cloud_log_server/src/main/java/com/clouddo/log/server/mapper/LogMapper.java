package com.clouddo.log.server.mapper;

import com.clouddo.log.common.model.LogModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 15:45:42
 */
@Mapper
public interface LogMapper {

	LogModel get(Long id);
	
	List<LogModel> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(LogModel log);
	
	int update(LogModel log);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
