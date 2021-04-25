package xingyu.lu.lab.unified.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xingyu.lu.lab.unified.api.dto.LoginUserDTO;
import xingyu.lu.lab.unified.domain.UnifiedCode;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import xingyu.lu.lab.unified.mapper.UnifiedCodeMapper;
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

    @Resource
    private UnifiedCodeMapper unifiedCodeMapper;

    @DS(value = "auth-ds")
    @Override
    public ResultModel<UnifiedUser> unifiedLogin(LoginUserDTO dto) {

        QueryWrapper<UnifiedUser> queryWrapper = Wrappers.<UnifiedUser>query()
                .eq("user_name", dto.getUserName())
                .eq("user_pwd", dto.getPassword());

        UnifiedUser user = unifiedUserMapper.selectOne(queryWrapper);


        String appId = dto.getAppId();

        //TODO 根据 AppId 获取 分配的公私钥
        //TODO 根据 AppId 获取重定向地址
        //TODO 生成 CODE 存库
        UnifiedCode unifiedCode = new UnifiedCode();
        unifiedCode.setUserName(dto.getUserName());

        if (null == user) {
            return ResultModel.commonError("用户名或密码错误！");
        }
        //TODO 带参重定向
        return ResultModel.success(user);
    }
}




