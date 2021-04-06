package xingyu.lu.individual.rpc.grpc.module;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingyu.lu
 * @create 2021-01-12 16:10
 **/
@Data
public class User {

    private String userName;
    private int age;
    private String gender;
    private List<Education> edu;
    private List<Work> works;

    public static User getInstance() {
        User user = new User();
        user.setUserName("张三");
        user.setAge(18);
        user.setGender("男");

        List<Education> educations = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            Education education = new Education();
            education.setSchool("school" + i);
            education.setType("type" + i);
            education.setMajor("major" + i);
            education.setDegree("degree" + i);
            educations.add(education);
        }
        user.setEdu(educations);

        List<Work> works = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            Work work = new Work();
            work.setCompanyName("CompanyName" + i);
            work.setJobs("Jobs" + i);
            work.setPosition("Position" + i);
            List<Projects> projects = new ArrayList<>();
            for (int j = 0; j <= 3; j++) {
                Projects p = new Projects();
                p.setName("Projects" + i + j);
                p.setFrameworks(new String[]{"Frameworks" + i, "Frameworks" + j});
                p.setLanguages(new String[]{"Languages" + i, "Languages" + j});
                projects.add(p);
            }
            work.setProjectList(projects);
            works.add(work);
        }
        user.setWorks(works);

        return user;
    }
}
