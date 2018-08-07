package com.clouddo.log.server.service.impl;

import com.clouddo.log.common.model.LogModel;
import com.clouddo.log.server.mapper.LogMapper;
import com.clouddo.log.server.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
	@Autowired
	LogMapper logMapper;

	@Async
	@Override
	public void save(LogModel logDO) {
		 logMapper.save(logDO);
	}

//	@Override
//	public PageDO<LogModel> queryList(Query query) {
//		int total = logMapper.count(query);
//		List<LogModel> logs = logMapper.list(query);
//		PageDO<LogModel> page = new PageDO<>();
//		page.setTotal(total);
//		page.setRows(logs);
//		return page;
//	}

	@Override
	public int remove(Long id) {
		int count = logMapper.remove(id);
		return count;
	}

	@Override
	public int batchRemove(Long[] ids){
		return logMapper.batchRemove(ids);
	}
}
