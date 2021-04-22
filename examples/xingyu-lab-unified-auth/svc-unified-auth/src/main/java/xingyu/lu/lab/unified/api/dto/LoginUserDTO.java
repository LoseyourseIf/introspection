package xingyu.lu.lab.unified.api.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * @author xingyu.lu
 * @create 2021-04-22 16:15
 **/
@Data
public class LoginUserDTO {
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String appId;
}
