package pers.aron.springmvc.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//返回个客户端的状态码和说明
@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="用户名和密码不匹配")
public class UserNameNotMatchPasswordException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
