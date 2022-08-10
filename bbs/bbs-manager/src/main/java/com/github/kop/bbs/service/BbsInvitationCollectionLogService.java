package com.github.kop.bbs.service;

import java.util.List;
import com.github.kop.bbs.module.entity.BbsInvitationCollectionLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BbsInvitationCollectionLogService extends IService<BbsInvitationCollectionLog> {


    int updateBatchSelective(List<BbsInvitationCollectionLog> list);

    int batchInsert(List<BbsInvitationCollectionLog> list);

}


