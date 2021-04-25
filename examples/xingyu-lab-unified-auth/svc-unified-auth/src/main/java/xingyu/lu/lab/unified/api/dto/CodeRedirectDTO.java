package xingyu.lu.lab.unified.api.dto;

/**
 * @author xingyu.lu
 * @create 2021-04-25 16:10
 **/
public class CodeRedirectDTO {

    private String code;
    private String codeSign;
    private String redirectUrl;

    public String getCode() {
        return code;
    }

    public CodeRedirectDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getCodeSign() {
        return codeSign;
    }

    public CodeRedirectDTO setCodeSign(String codeSign) {
        this.codeSign = codeSign;
        return this;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public CodeRedirectDTO setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public static CodeRedirectDTO getInstance() {
        return new CodeRedirectDTO();
    }

}
