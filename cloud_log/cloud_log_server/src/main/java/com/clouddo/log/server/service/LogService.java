package com.clouddo.log.server.service;


import com.clouddo.log.common.model.LogModel;

public interface LogService {

	void save(LogModel logDO);

//	PageDO<LogModel> queryList(Query query);

	int remove(Long id);

	int batchRemove(Long[] ids);
}
