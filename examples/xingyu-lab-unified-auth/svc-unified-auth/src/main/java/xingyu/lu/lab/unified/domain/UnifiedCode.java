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
 * @TableName unified_code
 */
@TableName(value ="unified_code")
@Data
public class UnifiedCode implements Serializable {
    /**
     * 
     */
    @TableId(value = "unified_code_id")
    private Long unifiedCodeId;

    /**
     * 
     */
    @TableField(value = "unified_app_id")
    private Long unifiedAppId;

    /**
     * 
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 
     */
    @TableField(value = "unified_code")
    private String unifiedCode;

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
        UnifiedCode other = (UnifiedCode) that;
        return (this.getUnifiedCodeId() == null ? other.getUnifiedCodeId() == null : this.getUnifiedCodeId().equals(other.getUnifiedCodeId()))
            && (this.getUnifiedAppId() == null ? other.getUnifiedAppId() == null : this.getUnifiedAppId().equals(other.getUnifiedAppId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUnifiedCode() == null ? other.getUnifiedCode() == null : this.getUnifiedCode().equals(other.getUnifiedCode()))
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
        result = prime * result + ((getUnifiedCodeId() == null) ? 0 : getUnifiedCodeId().hashCode());
        result = prime * result + ((getUnifiedAppId() == null) ? 0 : getUnifiedAppId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUnifiedCode() == null) ? 0 : getUnifiedCode().hashCode());
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
        sb.append(", unifiedCodeId=").append(unifiedCodeId);
        sb.append(", unifiedAppId=").append(unifiedAppId);
        sb.append(", userName=").append(userName);
        sb.append(", unifiedCode=").append(unifiedCode);
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