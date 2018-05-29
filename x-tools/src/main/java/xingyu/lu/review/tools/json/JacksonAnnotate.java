package xingyu.lu.review.tools.json;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/*
 * @Describe JsonIdentityInfo 解决序列化循环引用问题
 */
@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_identification")

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")

/*
 * @deprecated see spring.jackson.default-property-inclusion=non_null
 * @Describe JsonSerialize.Inclusion.NON_NULL 只序列化非空
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JacksonAnnotate {
}
