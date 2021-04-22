package xingyu.lu.lab.unified.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    private Long unified_keypair_id;

    /**
     * 
     */
    @TableField(value = "unified_app_id")
    private Long unified_app_id;

    /**
     * 
     */
    @TableField(value = "app_pub_key")
    private String app_pub_key;

    /**
     * 
     */
    @TableField(value = "app_pri_key")
    private String app_pri_key;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date create_time;

    /**
     * 
     */
    @TableField(value = "create_by")
    private Long create_by;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Date update_time;

    /**
     * 
     */
    @TableField(value = "update_by")
    private Long update_by;

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
        return (this.getUnified_keypair_id() == null ? other.getUnified_keypair_id() == null : this.getUnified_keypair_id().equals(other.getUnified_keypair_id()))
            && (this.getUnified_app_id() == null ? other.getUnified_app_id() == null : this.getUnified_app_id().equals(other.getUnified_app_id()))
            && (this.getApp_pub_key() == null ? other.getApp_pub_key() == null : this.getApp_pub_key().equals(other.getApp_pub_key()))
            && (this.getApp_pri_key() == null ? other.getApp_pri_key() == null : this.getApp_pri_key().equals(other.getApp_pri_key()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUnified_keypair_id() == null) ? 0 : getUnified_keypair_id().hashCode());
        result = prime * result + ((getUnified_app_id() == null) ? 0 : getUnified_app_id().hashCode());
        result = prime * result + ((getApp_pub_key() == null) ? 0 : getApp_pub_key().hashCode());
        result = prime * result + ((getApp_pri_key() == null) ? 0 : getApp_pri_key().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", unified_keypair_id=").append(unified_keypair_id);
        sb.append(", unified_app_id=").append(unified_app_id);
        sb.append(", app_pub_key=").append(app_pub_key);
        sb.append(", app_pri_key=").append(app_pri_key);
        sb.append(", create_time=").append(create_time);
        sb.append(", create_by=").append(create_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", enabled=").append(enabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}