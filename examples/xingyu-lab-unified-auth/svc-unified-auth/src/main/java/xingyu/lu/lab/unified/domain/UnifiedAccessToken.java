package xingyu.lu.lab.unified.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName unified_access_token
 */
@TableName(value ="unified_access_token")
@Data
public class UnifiedAccessToken implements Serializable {
    /**
     * 
     */
    @TableId(value = "unified_access_token_id")
    private Long unifiedAccessTokenId;

    /**
     * 
     */
    @TableField(value = "unified_app_id")
    private Long unifiedAppId;

    /**
     * 
     */
    @TableField(value = "access_token")
    private String accessToken;

    /**
     * 
     */
    @TableField(value = "refresh_token")
    private String refreshToken;

    /**
     * 
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 
     */
    @TableField(value = "grant_type")
    private String grantType;

    /**
     * 
     */
    @TableField(value = "not_before")
    private Date notBefore;

    /**
     * 
     */
    @TableField(value = "expire_at")
    private Date expireAt;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 
     */
    @TableField(value = "update_by")
    private Long updateBy;

    /**
     * 
     */
    @TableField(value = "enabled")
    private Boolean enabled;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UnifiedAccessToken other = (UnifiedAccessToken) that;
        return (this.getUnifiedAccessTokenId() == null ? other.getUnifiedAccessTokenId() == null : this.getUnifiedAccessTokenId().equals(other.getUnifiedAccessTokenId()))
            && (this.getUnifiedAppId() == null ? other.getUnifiedAppId() == null : this.getUnifiedAppId().equals(other.getUnifiedAppId()))
            && (this.getAccessToken() == null ? other.getAccessToken() == null : this.getAccessToken().equals(other.getAccessToken()))
            && (this.getRefreshToken() == null ? other.getRefreshToken() == null : this.getRefreshToken().equals(other.getRefreshToken()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getGrantType() == null ? other.getGrantType() == null : this.getGrantType().equals(other.getGrantType()))
            && (this.getNotBefore() == null ? other.getNotBefore() == null : this.getNotBefore().equals(other.getNotBefore()))
            && (this.getExpireAt() == null ? other.getExpireAt() == null : this.getExpireAt().equals(other.getExpireAt()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUnifiedAccessTokenId() == null) ? 0 : getUnifiedAccessTokenId().hashCode());
        result = prime * result + ((getUnifiedAppId() == null) ? 0 : getUnifiedAppId().hashCode());
        result = prime * result + ((getAccessToken() == null) ? 0 : getAccessToken().hashCode());
        result = prime * result + ((getRefreshToken() == null) ? 0 : getRefreshToken().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getGrantType() == null) ? 0 : getGrantType().hashCode());
        result = prime * result + ((getNotBefore() == null) ? 0 : getNotBefore().hashCode());
        result = prime * result + ((getExpireAt() == null) ? 0 : getExpireAt().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", unifiedAccessTokenId=").append(unifiedAccessTokenId);
        sb.append(", unifiedAppId=").append(unifiedAppId);
        sb.append(", accessToken=").append(accessToken);
        sb.append(", refreshToken=").append(refreshToken);
        sb.append(", userName=").append(userName);
        sb.append(", grantType=").append(grantType);
        sb.append(", notBefore=").append(notBefore);
        sb.append(", expireAt=").append(expireAt);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", enabled=").append(enabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}