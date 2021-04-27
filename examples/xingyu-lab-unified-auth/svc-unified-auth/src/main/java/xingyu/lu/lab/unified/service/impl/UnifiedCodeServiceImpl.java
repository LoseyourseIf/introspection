package xingyu.lu.lab.unified.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xingyu.lu.lab.unified.api.dto.AuthCodeGrantDTO;
import xingyu.lu.lab.unified.api.dto.AuthUserDTO;
import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import xingyu.lu.lab.unified.domain.UnifiedCode;
import xingyu.lu.lab.unified.service.UnifiedCodeService;
import xingyu.lu.lab.unified.mapper.UnifiedCodeMapper;
import org.springframework.stereotype.Service;
import xingyu.lu.lab.unified.utils.date.DateUtil;
import xingyu.lu.lab.unified.utils.random.RandomCodeUtil;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
@Service
public class UnifiedCodeServiceImpl extends ServiceImpl<UnifiedCodeMapper, UnifiedCode>
        implements UnifiedCodeService {

    @Resource
    private UnifiedCodeMapper unifiedCodeMapper;

    public UnifiedCode createAndSaveCode(AuthUserDTO dto) {

        String code = RandomCodeUtil.getRandomString(RandomCodeUtil.RANDOM_CODE_32);

        UnifiedCode unifiedCode = new UnifiedCode();
        unifiedCode.setUnifiedCode(code);
        unifiedCode.setNonUsed(true);
        unifiedCode.setUnifiedAppId(dto.getAppId());
        unifiedCode.setUserName(dto.getUserName());
        unifiedCode.setExpireAt(DateUtil.dateAdd(new Date(), Calendar.MINUTE, 30));

        //TODO 手动认证(外部应用)还是静默认证(内部应用)

        unifiedCode.setNonConfirm(false);
        unifiedCode.setEnabled(true);

        int insertCode = unifiedCodeMapper.insert(unifiedCode);

        if (insertCode > 0) {
            return unifiedCode;
        } else {
            return null;
        }
    }


    @Override
    public UnifiedCode getUnifiedAuthCode(AuthCodeGrantDTO dto) {

        QueryWrapper<UnifiedCode> codeQueryWrapper = Wrappers.<UnifiedCode>query()
                .eq("unified_app_id", dto.getAppId())
                .eq("unified_user_id", dto.getUnifiedUserId())
                .eq("unified_code", dto.getCode())
                .eq("enabled", 1);


        return unifiedCodeMapper.selectOne(codeQueryWrapper);
    }
}




