package xingyu.lu.lab.unified.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xingyu.lu.lab.unified.api.dto.LoginUserDTO;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import xingyu.lu.lab.unified.service.UnifiedUserService;
import xingyu.lu.lab.unified.mapper.UnifiedUserMapper;
import org.springframework.stereotype.Service;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class UnifiedUserServiceImpl extends ServiceImpl<UnifiedUserMapper, UnifiedUser>
        implements UnifiedUserService {

    @Resource
    private UnifiedUserMapper unifiedUserMapper;

    @Override
    public ResultModel unifiedLogin(LoginUserDTO dto) {
        return null;
    }
}




