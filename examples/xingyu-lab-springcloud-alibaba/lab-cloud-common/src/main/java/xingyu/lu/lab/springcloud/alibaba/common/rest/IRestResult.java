package xingyu.lu.lab.springcloud.alibaba.common.rest;

public interface IRestResult {

	/**
	 * Get result code.
	 * @return result code
	 */
	Integer getCode();

	/**
	 * Get result message.
	 * @return result message
	 */
	String getMessage();

}
