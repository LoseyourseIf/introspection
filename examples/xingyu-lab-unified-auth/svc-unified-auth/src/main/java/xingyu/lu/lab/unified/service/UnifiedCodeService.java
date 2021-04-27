package xingyu.lu.lab.unified.service;

import xingyu.lu.lab.unified.api.dto.AuthCodeGrantDTO;
import xingyu.lu.lab.unified.api.dto.AuthUserDTO;
import xingyu.lu.lab.unified.domain.UnifiedCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UnifiedCodeService extends IService<UnifiedCode> {

    UnifiedCode createAndSaveCode(AuthUserDTO dto);


    UnifiedCode getUnifiedAuthCode(AuthCodeGrantDTO dto);
}
