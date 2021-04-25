package xingyu.lu.lab.unified.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import xingyu.lu.lab.unified.service.UnifiedAppKeysService;
import xingyu.lu.lab.unified.mapper.UnifiedAppKeysMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class UnifiedAppKeysServiceImpl extends ServiceImpl<UnifiedAppKeysMapper, UnifiedAppKeys>
        implements UnifiedAppKeysService {


    @Resource
    private UnifiedAppKeysMapper unifiedAppKeysMapper;

    @Override
    public UnifiedAppKeys getUnifiedAppKeysById(long appId) {
        //根据 AppId 获取 分配的公私钥
        QueryWrapper<UnifiedAppKeys> keysQueryWrapper = Wrappers.<UnifiedAppKeys>query()
                .eq("unified_app_id", appId)
                .eq("enabled", 1);

        return unifiedAppKeysMapper.selectOne(keysQueryWrapper);
    }
}




