package xingyu.lu.lab.unified.api.dto;

import lombok.Data;
import lombok.NonNull;
import xingyu.lu.lab.unified.domain.UnifiedCode;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import java.util.Map;

/**
 * @author xingyu.lu
 * @create 2021-04-22 16:15
 **/
@Data
public class AuthUserDTO {

    @NonNull
    private String userName;
    @NonNull
    private String password;

    private long appId;

    private String redirectUrl;

    private Map<String, Object> reqParamMap;
}
