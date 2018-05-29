package xingyu.lu.review.basic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 基础数据类型
 *
 * @author xingyu.lu
 * @create 2018-03-05 17:34
 **/
public class BasicDataType {

    private byte aByte;         //  8位，最大存储数据量是255，存放的数据范围是-128~127之间。
    private Byte bByte;         // Byte，包装类
    private short aShort;       // 16位，最大数据存储量是65536，数据范围是-32768~32767之间。
    private Short bShort;       // Short,包装类
    private int aInt;          // 32位，最大数据存储容量是2的32次方减1，数据范围是负的2的31次方到正的2的31次方减1。
    private Integer bInt;       // Integer 包装类
    private long aLong;         // 64位，最大数据存储容量是2的64次方减1，数据范围为负的2的63次方到正的2的63次方减1。
    private Long bLong;         // Long 包装类
    private float aFloat;       // 32位，数据范围在3.4e-45~1.4e38，直接赋值时必须在数字后加上f或F。
    private Float bFloat;       // Float 包装类
    private double aDouble;     // 64位，数据范围在4.9e-324~1.8e308，赋值时可以加d或D也可以不加。
    private Double bDouble;     // Double 包装类
    private boolean aBoolean;   // 1 字节 true false。
    private Boolean bBoolean;   // Boolean 包装类
    private char aChar;         // 2 字节 16位，存储Unicode码，用单引号赋值
    private Character bChar;    // Character 包装类

    public static void main(String[] args) {
        BasicDataType basicDataType = new BasicDataType();
        Class<?> clz = basicDataType.getClass();
        Field[] fields = clz.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field.getGenericType());
            try {
                Method m = (Method) basicDataType.getClass()
                        .getMethod("get" + field.getName());

                System.out.println(m.invoke(basicDataType));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * (╯‵□′)╯︵┻━┻
     * 首字母大写最快方式
     *
     * @author xingyu.lu
     * @date 18/5/29 14:51
     */
    private String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    public byte getaByte() {
        return aByte;
    }

    public BasicDataType setaByte(byte aByte) {
        this.aByte = aByte;
        return this;
    }

    public Byte getbByte() {
        return bByte;
    }

    public BasicDataType setbByte(Byte bByte) {
        this.bByte = bByte;
        return this;
    }

    public short getaShort() {
        return aShort;
    }

    public BasicDataType setaShort(short aShort) {
        this.aShort = aShort;
        return this;
    }

    public Short getbShort() {
        return bShort;
    }

    public BasicDataType setbShort(Short bShort) {
        this.bShort = bShort;
        return this;
    }

    public int getaInt() {
        return aInt;
    }

    public BasicDataType setaInt(int aInt) {
        this.aInt = aInt;
        return this;
    }

    public Integer getbInt() {
        return bInt;
    }

    public BasicDataType setbInt(Integer bInt) {
        this.bInt = bInt;
        return this;
    }

    public long getaLong() {
        return aLong;
    }

    public BasicDataType setaLong(long aLong) {
        this.aLong = aLong;
        return this;
    }

    public Long getbLong() {
        return bLong;
    }

    public BasicDataType setbLong(Long bLong) {
        this.bLong = bLong;
        return this;
    }

    public float getaFloat() {
        return aFloat;
    }

    public BasicDataType setaFloat(float aFloat) {
        this.aFloat = aFloat;
        return this;
    }

    public Float getbFloat() {
        return bFloat;
    }

    public BasicDataType setbFloat(Float bFloat) {
        this.bFloat = bFloat;
        return this;
    }

    public double getaDouble() {
        return aDouble;
    }

    public BasicDataType setaDouble(double aDouble) {
        this.aDouble = aDouble;
        return this;
    }

    public Double getbDouble() {
        return bDouble;
    }

    public BasicDataType setbDouble(Double bDouble) {
        this.bDouble = bDouble;
        return this;
    }

    public boolean getaBoolean() {
        return aBoolean;
    }

    public BasicDataType setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
        return this;
    }

    public Boolean getbBoolean() {
        return bBoolean;
    }

    public BasicDataType setbBoolean(Boolean bBoolean) {
        this.bBoolean = bBoolean;
        return this;
    }

    public char getaChar() {
        return aChar;
    }

    public BasicDataType setaChar(char aChar) {
        this.aChar = aChar;
        return this;
    }

    public Character getbChar() {
        return bChar;
    }

    public BasicDataType setbChar(Character bChar) {
        this.bChar = bChar;
        return this;
    }

}
