package pers.aron.springmvc.handlers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pers.aron.springmvc.entities.User;

@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {

	private static final String SUCCESS = "success"; 
	/**
	 * 使用Servlet原声的 API 作为目标方法的参数
	 * 具体支持一下类型
	 * HttpServletRequest
	 * HttpServletReponse
	 * HttpSession
	 * java.security.Principal
	 * Locale
	 * InputStream
	 * OutputStream
	 * Reader
	 * Writer
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/testServletAPI")
	public String testServletAPI(HttpServletRequest request,
			HttpServletResponse response){
		System.out.println("testServletAPI," + request+ "," + response);
		//out.write("hello springmvc") 会在页面上打印方法
		return SUCCESS;
	}
	
	
	
	/**
	 * springMVC 会按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值，支持级联属性
	 * @param user
	 * @return
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user){
		System.out.println("testPojo:" + user);
		return SUCCESS;
	}
	
	/**
	 * 了解:
	 * @CookieValue:映射一个Cookie值，属性同@RequestParam
	 * @param sessionID
	 * @return
	 */
	@RequestMapping("/tesCookieValue")
	public String tesCookieValue(@CookieValue("JSESSIONID")String sessionID){
		System.out.println("tesCookieValue:sessionId:" + sessionID);
		return SUCCESS;
	}
	
	/**映射请求头信息
	 * 用法同@RequestHeader
	 * @param al
	 * @return
	 */
	@RequestMapping("/testReuestHeader")
	public String testReuestHeader(@RequestHeader(value="Accept-Language")String al){
		System.out.println("testReuestHeaderm,Accept-Language:" + al);
		return SUCCESS;
	}
	/**
	 * @RequestParam来映射请求参数。
	 * value值即请求参数的参数名
	 * required该参数是否必须,默认为true
	 * defaultValue请求参数的默认值
	 * @param un
	 * @param age
	 * @return
	 */
	@RequestMapping(value="/testRequestParam")
	public String testRequestParam(@RequestParam(value="username")String un,
					@RequestParam(value="age",required=false,defaultValue="0")int age){
		System.out.println("testRequestParam,username:" +un +" age:" + age);
		return SUCCESS;
	}
	
	/**
	 * Rest 风格的URL。
	 * 以CRUD为例：
	 * 新增:/order POST
	 * 修改:/order/1 PUT		update?id=1
	 * 获取：/order/1 GET get?id=1
	 * 删除:/order/1 DELETE delete?id=1
	 * 
	 * 如何发送PUT请求和DELETE请求呢
	 * 1.需要配置HiddenHttpMethodFilter
	 * 2.需要发送POST请求
	 * 3.需要在发送POST请求时携带一个name="_method"的隐藏域，值为DELETE或PUT
	 * 
	 * 在SpringMVC的目标方法中如何得到id呢？
	 * 使用@PathVariable注解
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.PUT)
	public String testRestGETPut(@PathVariable Integer id){
		System.out.println("testRest put:" + id);
		return SUCCESS;
	}
	
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.DELETE)
	public String testRestGETDelete(@PathVariable Integer id){
		System.out.println("testRest Delete:" + id);
		return SUCCESS;
	}
	
	@RequestMapping(value="/testRest",method=RequestMethod.POST)
	public String testRest(){
		System.out.println("testRest POST");
		return SUCCESS;
	}
	
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.GET)
	public String testRestGET(@PathVariable Integer id){
		System.out.println("testRest GET:" + id);
		return SUCCESS;
	}
	 /**
	  * @PathVariable可以来映射URL中的占位符到目标方法的参数中。
	  * @param id
	  * @return
	  */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id){
		System.out.println("testPathVariable:" + id);
		return SUCCESS;
	}
	
	/**
	 * 1.?:匹配文件中的一个字符
	 * 2.*匹配文件名中的任意字符
	 * 3.**：**匹配多层路径
	 * @return
	 */
	@RequestMapping("/testAntPath/*/abc")
	public String testAntPath(){
		System.out.println("testAntPath");
		return SUCCESS;
	}
	
	/**
	 * 了解:可以使用params和headers来更加精确的映射请求，params和header支持简单的表达式
	 * @return
	 */
	@RequestMapping(value="/testParamsAndHeaders",
			params={"username","age!=10"},headers={"Accept-Language=zh-CN,zh;q=0.8"})
	public String testParamsAndHeaders(){
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}
	
	
	/**
	 * 常用:使用method属性来指定请求方式
	 * @return
	 */
	@RequestMapping(value="/testmethod",method=RequestMethod.POST)
	public String testMethod(){
		System.out.println("testMethod");
		return SUCCESS;
	}
	
	/**
	 * 1.@RequestMapping 除了修饰 方法，还可修饰类
	 * @return
	 */
	@RequestMapping("/testRequestMapping")
	public String hello(){
		System.out.println("testRequestMapping");
		return SUCCESS;
	}
}
