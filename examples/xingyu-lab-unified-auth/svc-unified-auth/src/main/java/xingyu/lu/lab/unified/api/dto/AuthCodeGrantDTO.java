package xingyu.lu.lab.unified.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import java.util.Map;

/**
 * @author xingyu.lu
 * @create 2021-04-26 14:17
 **/
@Data
@NoArgsConstructor
public class AuthCodeGrantDTO {

    @NonNull
    private long appId;
    @NonNull
    private long unifiedUserId;
    @NonNull
    private String code;
    @NonNull
    private String sign;


    public boolean paramCheck() {
        return 0 != this.getAppId() &&
                0 != this.getUnifiedUserId() &&
                !StringUtils.isBlank(this.getCode()) &&
                !StringUtils.isBlank(this.getSign());
    }

    public static AuthCodeGrantDTO loadParam(Map<String, Object> reqParamMap) {
        AuthCodeGrantDTO dto = new AuthCodeGrantDTO();
        dto.setAppId(Long.parseLong(String.valueOf(reqParamMap.get("appId"))));
        dto.setUnifiedUserId(Long.parseLong(String.valueOf(reqParamMap.get("unifiedUserId"))));
        dto.setCode((String.valueOf(reqParamMap.get("code"))));
        dto.setSign((String.valueOf(reqParamMap.get("sign"))));
        return dto;
    }

}
