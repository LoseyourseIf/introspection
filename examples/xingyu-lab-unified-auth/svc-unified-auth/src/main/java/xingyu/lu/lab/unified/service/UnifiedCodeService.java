package xingyu.lu.lab.unified.service;

import xingyu.lu.lab.unified.api.dto.LoginUserDTO;
import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import xingyu.lu.lab.unified.domain.UnifiedCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UnifiedCodeService extends IService<UnifiedCode> {

    UnifiedCode createAndSaveCode(LoginUserDTO dto);
}
