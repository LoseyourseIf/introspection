package xingyu.lu.lab.unified.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName unified_user
 */
@TableName(value ="unified_user")
@Data
public class UnifiedUser implements Serializable {
    /**
     * 
     */
    @TableId(value = "unified_user_id")
    private Long unified_user_id;

    /**
     * 
     */
    @TableField(value = "user_name")
    private String user_name;

    /**
     * 
     */
    @TableField(value = "user_pwd")
    private String user_pwd;

    /**
     * 
     */
    @TableField(value = "non_locked")
    private Boolean non_locked;

    /**
     * 
     */
    @TableField(value = "non_expired")
    private Boolean non_expired;

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
        UnifiedUser other = (UnifiedUser) that;
        return (this.getUnified_user_id() == null ? other.getUnified_user_id() == null : this.getUnified_user_id().equals(other.getUnified_user_id()))
            && (this.getUser_name() == null ? other.getUser_name() == null : this.getUser_name().equals(other.getUser_name()))
            && (this.getUser_pwd() == null ? other.getUser_pwd() == null : this.getUser_pwd().equals(other.getUser_pwd()))
            && (this.getNon_locked() == null ? other.getNon_locked() == null : this.getNon_locked().equals(other.getNon_locked()))
            && (this.getNon_expired() == null ? other.getNon_expired() == null : this.getNon_expired().equals(other.getNon_expired()))
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
        result = prime * result + ((getUnified_user_id() == null) ? 0 : getUnified_user_id().hashCode());
        result = prime * result + ((getUser_name() == null) ? 0 : getUser_name().hashCode());
        result = prime * result + ((getUser_pwd() == null) ? 0 : getUser_pwd().hashCode());
        result = prime * result + ((getNon_locked() == null) ? 0 : getNon_locked().hashCode());
        result = prime * result + ((getNon_expired() == null) ? 0 : getNon_expired().hashCode());
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
        sb.append(", unified_user_id=").append(unified_user_id);
        sb.append(", user_name=").append(user_name);
        sb.append(", user_pwd=").append(user_pwd);
        sb.append(", non_locked=").append(non_locked);
        sb.append(", non_expired=").append(non_expired);
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