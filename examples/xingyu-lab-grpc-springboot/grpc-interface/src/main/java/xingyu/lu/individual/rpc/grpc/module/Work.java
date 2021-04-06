package xingyu.lu.individual.rpc.grpc.module;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xingyu.lu
 * @create 2021-01-15 15:56
 **/
@Data
public class Work {
    private String companyName;
    private String position;
    private String jobs;
    private List<Projects> projectList;
}
