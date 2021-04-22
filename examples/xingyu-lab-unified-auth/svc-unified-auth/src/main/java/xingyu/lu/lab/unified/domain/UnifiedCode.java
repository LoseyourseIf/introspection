package xingyu.lu.lab.unified.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    private Long unified_code_id;

    /**
     * 
     */
    @TableField(value = "unified_app_id")
    private Long unified_app_id;

    /**
     * 
     */
    @TableField(value = "user_name")
    private String user_name;

    /**
     * 
     */
    @TableField(value = "unified_code")
    private String unified_code;

    /**
     * 
     */
    @TableField(value = "not_before")
    private Date not_before;

    /**
     * 
     */
    @TableField(value = "expire_at")
    private Date expire_at;

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
        UnifiedCode other = (UnifiedCode) that;
        return (this.getUnified_code_id() == null ? other.getUnified_code_id() == null : this.getUnified_code_id().equals(other.getUnified_code_id()))
            && (this.getUnified_app_id() == null ? other.getUnified_app_id() == null : this.getUnified_app_id().equals(other.getUnified_app_id()))
            && (this.getUser_name() == null ? other.getUser_name() == null : this.getUser_name().equals(other.getUser_name()))
            && (this.getUnified_code() == null ? other.getUnified_code() == null : this.getUnified_code().equals(other.getUnified_code()))
            && (this.getNot_before() == null ? other.getNot_before() == null : this.getNot_before().equals(other.getNot_before()))
            && (this.getExpire_at() == null ? other.getExpire_at() == null : this.getExpire_at().equals(other.getExpire_at()))
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
        result = prime * result + ((getUnified_code_id() == null) ? 0 : getUnified_code_id().hashCode());
        result = prime * result + ((getUnified_app_id() == null) ? 0 : getUnified_app_id().hashCode());
        result = prime * result + ((getUser_name() == null) ? 0 : getUser_name().hashCode());
        result = prime * result + ((getUnified_code() == null) ? 0 : getUnified_code().hashCode());
        result = prime * result + ((getNot_before() == null) ? 0 : getNot_before().hashCode());
        result = prime * result + ((getExpire_at() == null) ? 0 : getExpire_at().hashCode());
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
        sb.append(", unified_code_id=").append(unified_code_id);
        sb.append(", unified_app_id=").append(unified_app_id);
        sb.append(", user_name=").append(user_name);
        sb.append(", unified_code=").append(unified_code);
        sb.append(", not_before=").append(not_before);
        sb.append(", expire_at=").append(expire_at);
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