package xingyu.lu.lab.applet.entity;

import lombok.Data;

@Data
public class WXAccessToken {

    private  String accessToken;
    private  Integer expiresIn;

}
