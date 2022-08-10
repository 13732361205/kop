package com.github.kop.bbs.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.github.kop.bbs.repo.mapper.BbsUserFollowerListMapper;
import com.github.kop.bbs.module.entity.BbsUserFollowerList;
import com.github.kop.bbs.service.BbsUserFollowerListService;
@Service
public class BbsUserFollowerListServiceImpl extends ServiceImpl<BbsUserFollowerListMapper, BbsUserFollowerList> implements BbsUserFollowerListService{

    @Override
    public int updateBatchSelective(List<BbsUserFollowerList> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<BbsUserFollowerList> list) {
        return baseMapper.batchInsert(list);
    }
}