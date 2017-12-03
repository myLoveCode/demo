package com.example.core.mode;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Demo;

public class ResponseJson<T> implements Serializable {

	private static final long serialVersionUID = 472122891140741274L;

	private String code;

	private String msg;

	private T data;

	public ResponseJson() {
		super();
	}

	public ResponseJson(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResponseJson(T data) {
		super();
		this.data = data;
	}

	public static <T> ResponseJson<T> createResponse(T data) {
		return new ResponseJson<T>(data);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static void main(String[] args) {
		Demo d = new Demo();
		d.setAddress("address");
		d.setAge(11);

		ResponseJson<Demo> s = ResponseJson.createResponse(d);
		System.out.println(JSON.toJSONString(s));

		ResponseJson<Boolean> s1 = new ResponseJson<>(Boolean.TRUE);
		System.out.println(JSON.toJSONString(s1));

		ResponseJson s2 = new ResponseJson("errorCode", "errorMsg");
		System.out.println(JSON.toJSONString(s2));
	}
}
