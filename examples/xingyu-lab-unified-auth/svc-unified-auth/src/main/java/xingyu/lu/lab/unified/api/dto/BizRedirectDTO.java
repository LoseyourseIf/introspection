package xingyu.lu.lab.unified.api.dto;

/**
 * @author xingyu.lu
 * @create 2021-04-25 16:10
 **/
public class BizRedirectDTO {

    private String code;
    private boolean requireConfirm;
    private String platformSign;
    private String redirectUrl;

    public boolean isRequireConfirm() {
        return requireConfirm;
    }

    public BizRedirectDTO setRequireConfirm(boolean requireConfirm) {
        this.requireConfirm = requireConfirm;
        return this;
    }

    public String getCode() {
        return code;
    }

    public BizRedirectDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getPlatformSign() {
        return platformSign;
    }

    public BizRedirectDTO setPlatformSign(String platformSign) {
        this.platformSign = platformSign;
        return this;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public BizRedirectDTO setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public static BizRedirectDTO getInstance() {
        return new BizRedirectDTO();
    }

}
