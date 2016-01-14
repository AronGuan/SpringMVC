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
	 * ʹ��Servletԭ���� API ��ΪĿ�귽���Ĳ���
	 * ����֧��һ������
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
		//out.write("hello springmvc") ����ҳ���ϴ�ӡ����
		return SUCCESS;
	}
	
	
	
	/**
	 * springMVC �ᰴ�����������POJO�����������Զ�ƥ�䣬�Զ�Ϊ�ö����������ֵ��֧�ּ�������
	 * @param user
	 * @return
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user){
		System.out.println("testPojo:" + user);
		return SUCCESS;
	}
	
	/**
	 * �˽�:
	 * @CookieValue:ӳ��һ��Cookieֵ������ͬ@RequestParam
	 * @param sessionID
	 * @return
	 */
	@RequestMapping("/tesCookieValue")
	public String tesCookieValue(@CookieValue("JSESSIONID")String sessionID){
		System.out.println("tesCookieValue:sessionId:" + sessionID);
		return SUCCESS;
	}
	
	/**ӳ������ͷ��Ϣ
	 * �÷�ͬ@RequestHeader
	 * @param al
	 * @return
	 */
	@RequestMapping("/testReuestHeader")
	public String testReuestHeader(@RequestHeader(value="Accept-Language")String al){
		System.out.println("testReuestHeaderm,Accept-Language:" + al);
		return SUCCESS;
	}
	/**
	 * @RequestParam��ӳ�����������
	 * valueֵ����������Ĳ�����
	 * required�ò����Ƿ����,Ĭ��Ϊtrue
	 * defaultValue���������Ĭ��ֵ
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
	 * Rest ����URL��
	 * ��CRUDΪ����
	 * ����:/order POST
	 * �޸�:/order/1 PUT		update?id=1
	 * ��ȡ��/order/1 GET get?id=1
	 * ɾ��:/order/1 DELETE delete?id=1
	 * 
	 * ��η���PUT�����DELETE������
	 * 1.��Ҫ����HiddenHttpMethodFilter
	 * 2.��Ҫ����POST����
	 * 3.��Ҫ�ڷ���POST����ʱЯ��һ��name="_method"��������ֵΪDELETE��PUT
	 * 
	 * ��SpringMVC��Ŀ�귽������εõ�id�أ�
	 * ʹ��@PathVariableע��
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
	  * @PathVariable������ӳ��URL�е�ռλ����Ŀ�귽���Ĳ����С�
	  * @param id
	  * @return
	  */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id){
		System.out.println("testPathVariable:" + id);
		return SUCCESS;
	}
	
	/**
	 * 1.?:ƥ���ļ��е�һ���ַ�
	 * 2.*ƥ���ļ����е������ַ�
	 * 3.**��**ƥ����·��
	 * @return
	 */
	@RequestMapping("/testAntPath/*/abc")
	public String testAntPath(){
		System.out.println("testAntPath");
		return SUCCESS;
	}
	
	/**
	 * �˽�:����ʹ��params��headers�����Ӿ�ȷ��ӳ������params��header֧�ּ򵥵ı��ʽ
	 * @return
	 */
	@RequestMapping(value="/testParamsAndHeaders",
			params={"username","age!=10"},headers={"Accept-Language=zh-CN,zh;q=0.8"})
	public String testParamsAndHeaders(){
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}
	
	
	/**
	 * ����:ʹ��method������ָ������ʽ
	 * @return
	 */
	@RequestMapping(value="/testmethod",method=RequestMethod.POST)
	public String testMethod(){
		System.out.println("testMethod");
		return SUCCESS;
	}
	
	/**
	 * 1.@RequestMapping �������� ����������������
	 * @return
	 */
	@RequestMapping("/testRequestMapping")
	public String hello(){
		System.out.println("testRequestMapping");
		return SUCCESS;
	}
}
