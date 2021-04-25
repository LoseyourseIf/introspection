package xingyu.lu.lab.unified.service;

import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UnifiedAppKeysService extends IService<UnifiedAppKeys> {

    UnifiedAppKeys getUnifiedAppKeysById(long appId);

}
