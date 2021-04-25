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
 * @TableName unified_app_keys
 */
@TableName(value ="unified_app_keys")
@Data
public class UnifiedAppKeys implements Serializable {
    /**
     * 
     */
    @TableId(value = "unified_keypair_id")
    private Long unifiedKeypairId;

    /**
     * 
     */
    @TableField(value = "unified_app_id")
    private Long unifiedAppId;

    /**
     * 
     */
    @TableField(value = "app_pub_key")
    private String appPubKey;

    /**
     * 
     */
    @TableField(value = "app_pri_key")
    private String appPriKey;

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
        UnifiedAppKeys other = (UnifiedAppKeys) that;
        return (this.getUnifiedKeypairId() == null ? other.getUnifiedKeypairId() == null : this.getUnifiedKeypairId().equals(other.getUnifiedKeypairId()))
            && (this.getUnifiedAppId() == null ? other.getUnifiedAppId() == null : this.getUnifiedAppId().equals(other.getUnifiedAppId()))
            && (this.getAppPubKey() == null ? other.getAppPubKey() == null : this.getAppPubKey().equals(other.getAppPubKey()))
            && (this.getAppPriKey() == null ? other.getAppPriKey() == null : this.getAppPriKey().equals(other.getAppPriKey()))
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
        result = prime * result + ((getUnifiedKeypairId() == null) ? 0 : getUnifiedKeypairId().hashCode());
        result = prime * result + ((getUnifiedAppId() == null) ? 0 : getUnifiedAppId().hashCode());
        result = prime * result + ((getAppPubKey() == null) ? 0 : getAppPubKey().hashCode());
        result = prime * result + ((getAppPriKey() == null) ? 0 : getAppPriKey().hashCode());
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
        sb.append(", unifiedKeypairId=").append(unifiedKeypairId);
        sb.append(", unifiedAppId=").append(unifiedAppId);
        sb.append(", appPubKey=").append(appPubKey);
        sb.append(", appPriKey=").append(appPriKey);
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