package xingyu.lu.lab.unified.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xingyu.lu
 * @create 2021-04-25 16:10
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizRedirectDTO {

    private String code;
    private boolean requireConfirm;
    private String platformSign;
    private String redirectUrl;
}
