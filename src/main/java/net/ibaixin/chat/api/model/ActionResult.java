package net.ibaixin.chat.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * web请求的返回实体，以json形式返回
 * @author huanghui1
 *
 */
@JsonInclude(Include.NON_NULL)
public class ActionResult {
	

	/**
	 * 请求成功
	 */
	public static final int CODE_SUCCESS = 99;
	
	/**
	 * 请求、处理失败
	 */
	public static final int CODE_ERROR = 100;
	
	/**
	 * 错误的请求参数
	 */
	public static final int CODE_ERROR_PARAM = 101;
	
	
	/**
	 * 返回码
	 */
	private int resultCode;
	
	/**
	 * 返回的id，一般用于添加
	 */
	private String id;
	
	/**
	 * 返回的内容,限于文本内容
	 */
	private String data;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ActionResult(int resultCode, String id, String data) {
		super();
		this.resultCode = resultCode;
		this.id = id;
		this.data = data;
	}

	public ActionResult(int resultCode, String data) {
		super();
		this.resultCode = resultCode;
		this.data = data;
	}

	public ActionResult() {
		super();
	}
}
