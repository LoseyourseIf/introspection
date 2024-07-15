package xingyu.lu.lab.applet.entity;

import lombok.Data;

@Data
public class WXJSAPITicket {
    private Integer errCode;
    private String errMsg;
    private String ticket;
    private Integer expiresIn;
}
