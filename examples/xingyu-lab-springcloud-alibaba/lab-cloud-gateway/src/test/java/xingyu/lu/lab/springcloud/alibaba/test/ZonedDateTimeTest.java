package xingyu.lu.lab.springcloud.alibaba.test;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description: ZonedDateTime
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-05-29  10:27
 */
public class ZonedDateTimeTest {

    @Test
    public void getZonedDateTimeStr() {
        System.out.println("ZonedDateTime ToString");
        System.out.println(ZonedDateTime.now().toString());
        ZonedDateTime zdt = ZonedDateTime.parse("2024-05-29T10:28:48+08:00[Asia/Shanghai]");
        System.out.println("ZonedDateTime DateTimeFormatter ISO_ZONED_DATE_TIME");
        System.out.println(zdt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

    }
}
