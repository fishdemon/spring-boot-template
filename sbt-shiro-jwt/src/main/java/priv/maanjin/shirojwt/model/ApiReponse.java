package priv.maanjin.shirojwt.model;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author anjin.ma
 * @date 2019/7/23
 * @description  用于返回前端json数据的工具类
 */
public class ApiReponse {

	// 状态码
	private int status;
	// 提示信息
	private String message;
	// 封装有效数据
	private Map<String, Object> data = new HashMap<String, Object>();

	public static ApiReponse ok() {
		ApiReponse result = new ApiReponse();
		result.setStatus(200);
		result.setMessage("success");
		return result;
	}
	
	public static ApiReponse okContent(Object content) {
		ApiReponse result = new ApiReponse();
		result.setStatus(200);
		result.setMessage("success");
		result.getData().put("content", content);
		return result;
	}
	
	public static <T> ApiReponse okPage(IPage<T> page) {
		ApiReponse result = new ApiReponse();
		result.setStatus(200);
		result.setMessage("success");
		result.getData().put("content", page.getRecords());
		result.getData().put("total", page.getTotal());
		return result;
	}

	public static ApiReponse fail() {
		ApiReponse result = new ApiReponse();
		result.setStatus(400);
		result.setMessage("fail");
		return result;
	}

	public static ApiReponse noPermission() {
		ApiReponse result = new ApiReponse();
		result.setStatus(401);
		result.setMessage("no permission");
		return result;
	}

	public static ApiReponse error() {
		ApiReponse result = new ApiReponse();
		result.setStatus(500);
		result.setMessage("error");
		return result;
	}

	public static ApiReponse code(int code){
		ApiReponse result = new ApiReponse();
		result.setStatus(code);
		result.setMessage("exception");
		return result;
	}

	public ApiReponse add(String key, Object value) {
		this.data.put(key, value);
		return this;
	}
	
	public ApiReponse addContent(Object value) {
		this.data.put("content", value);
		return this;
	}
	
	public ApiReponse addTotal(Integer value) {
		this.data.put("total", value);
		return this;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
