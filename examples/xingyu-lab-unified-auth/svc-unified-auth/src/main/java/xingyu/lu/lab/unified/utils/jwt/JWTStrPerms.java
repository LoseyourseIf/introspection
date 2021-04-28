package xingyu.lu.lab.unified.utils.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xingyu.lu.lab.unified.utils.map.MPUtil;

import java.util.List;
import java.util.Map;

/**
 * @author xingyu.lu
 * @create 2021-04-28 14:02
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTStrPerms {

    private List<String> userRoles;
    private List<String> sysStrPermissions;
    private List<String> menuStrPermissions;
    private List<String> btnStrPermissions;
    private List<String> dataStrPermissions;


    public Map<String, Object> strPermsMap() throws Exception {
        return MPUtil.pojo2Map(this);
    }
}
