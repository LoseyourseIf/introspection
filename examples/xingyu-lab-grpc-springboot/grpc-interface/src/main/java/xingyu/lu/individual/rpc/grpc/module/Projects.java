package xingyu.lu.individual.rpc.grpc.module;

import lombok.Data;

/**
 * @author xingyu.lu
 * @create 2021-01-15 16:22
 **/
@Data
public class Projects {
    private String name;
    private String[] languages;
    private String[] frameworks;
}
