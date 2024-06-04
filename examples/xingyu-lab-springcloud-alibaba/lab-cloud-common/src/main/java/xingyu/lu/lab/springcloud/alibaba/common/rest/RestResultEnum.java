package xingyu.lu.lab.springcloud.alibaba.common.rest;

/**
 * Integrated Example common result enum class.
 * @author TrevorLink
 */
public enum RestResultEnum implements IRestResult {

	/**
	 * return success result.
	 */
	SUCCESS(2001, "接口调用成功"),
	/**
	 * return business common failed.
	 */
	COMMON_FAILED(2003, "接口调用失败");

	private Integer code;

	private String message;

	RestResultEnum() {
	}

	RestResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
