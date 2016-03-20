package pers.aron.springmvc.handlers;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import pers.aron.springmvc.entities.User;

@SessionAttributes(value={"school"},types={String.class})
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {
	private static final String SUCCESS = "success"; 
	//�ض���
	@RequestMapping("/testRedirect")
	public String testRedirect(){
		System.out.println("testRedirect");
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/testView")
	public String testView(){
		System.out.println("testView");
		return "helloView";
	}


	//����ת��ModelAndView���󣬽�����ͼ�������õ����յ���ͼ����(View)������ͼ
	@RequestMapping("/testViewAndViewResolver")
	public String testViewAndViewResolver(){
		System.out.println(":testViewAndViewResolver");
		return SUCCESS;
	}
	
	/**
	 * 1. ��@ModelAttribute��ǵķ���������ÿ��Ŀ�귽��ִ��֮ǰ��SpringMVC����
	 * 2. @ModelAttributeע��Ҳ����������Ŀ�귽��POJO���͵���Σ���value����ֵ�����µ�����:
	 * 1) SpringMVC��ʹ��value����ֵ��implicitModel�в��Ҷ�Ӧ�Ķ������������ֱ�Ӵ��뵽Ŀ�귽���������
	 * 2) SpringMVC��һvalueΪkey,POJO���͵Ķ���Ϊvalue�����뵽request��
	 * @param id
	 * @param map
	 */
	//���ڸ��²�������
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id,
			Map<String,Object> map){
		System.out.println("modelAttribute method");
		if(id != null){
			//ģ������ݿ��л�ȡ����
			User user = new User(1, "tom", "123456","tom@atguigu", 12);
			System.out.println("�����ݿ��л�ȡһ������:" + user );
			map.put("user", user);
		}
	}
	/**
	 * ��������:
	 * 1. ִ��@ModelAttributeע�����εķ���:�����ݿ� ��ȡ������,�Ѷ����絽Map�У���Ϊ:User
	 * 2. SpringMVC��Map��ȡ��User���󣬲��ѱ����������������User����Ķ�Ӧ����.
	 * 3. SpringMVC�� ����������Ŀ�귽���Ĳ���
	 * 
	 *  ע��: ��@ModelAttribute���εķ����У����뵽Mapʱ�ļ���Ҫ��Ŀ�귽��������͵ĵ�һ����ĸ
	 *  Сд���ַ���һ�£�����"user"
	 *  
	 *  SpringMVCȷ��Ŀ�귽��POJO������εĹ���
	 *  1. ȷ��һ��key:
	 *  1)��Ŀ�귽����POJO���͵Ĳ���û��ʹ��@@ModelAttribute��Ϊ���Σ���keyΪPOJO������һ����ĸ��Сд
	 *  2)��ʹ��@@ModelAttribute������,��keyΪ@@ModelAttributeע���value����ֵ��
	 *  2. ��implicitModel�в���key��Ӧ�Ķ��������ڣ�����Ϊ��δ���
	 *  1)����@@ModelAttribute��ǵķ�������Map�б��������key��1ȷ����keyһֱ������ȡ��
	 *  3. ��implicitModel�в�����key��Ӧ�Ķ������鵱ǰ��Handler�Ƿ�ʹ��@SessioinAttributesע�����Σ�
	 *  ��ʹ���˸�ע�⣬��@SessionAttributesע���value����ֵ�а�����key,����HttpSession������ȡkey��
	 *  ��Ӧ��valueֵ����������ֱ�Ӵ��뵽Ŀ�귽��������У������������׳��쳣��
	 *  4. ��Handlerû�б�ʶ@SessioinAttributesע���@SessioinAttributesע���valueֵ�в�����key�����
	 *  ͨ������������POJO���͵Ĳ���������Ŀ�귽���Ĳ���
	 *  5. SpringMVC���key��POJO���͵Ķ��󵽱���implicitModel�У��������浽request�С�
	 *  
	 *  
	 *  Դ�������������
	 *  1. ����@ModelAttributeע�����εķ���,ʵ���ϰ�@ModelAttributes������Map�е����ݷ�����implicitModel��
	 *	2.��������������Ŀ�������ʵ���ϸ�Ŀ�����������WebDataBinder�����target����
	 *	1).����WebDataBinder����:
	 *	ȷ��objectName ����:�������attrName����ֵΪ"",��objectNameΪ������һ����ĸСд.
	 *	ע��:attrName����Ŀ�귽����POJO����ʹ����@ModelAttribute�����Σ���attrNameֵ��Ϊ@ModelAttribute
	 *	��value����ֵ
	 *	
	 *	ȷ��target����: 
	 *	>��implicitModel�в���attrName��Ӧ������ֵ�������ڣ�ok
	 *	>��������:����֤��ǰHandler�Ƿ�ʹ����@SessionAttributes�������Σ���ʹ���ˣ����Դ�Session��
	 *	��ȡattrName����Ӧ������ֵ,��session��û�ж�Ӧ������ֵ�����׳��쳣��
	 *	>��Handlerû��ʹ��@SessionAttributes��������,��@SessionAttributes��û��ʹ��valueֵָ����key
	 *	��attrName��ƥ�䣬��ͨ�����䴴��POJO����
	 *	
	 *	2).SpringMVC�ѱ����������������WebDataBinder��target��Ӧ�����ԡ�
	 *	3).SpringMVC���WebDataBinder��attrName��target����implicitModel����������request�������
	 *	4).��WebDataBinder��target��Ϊ�������ݸ�Ŀ�귽�������
	 * @param user
	 * @return
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(@ModelAttribute("user") User user){
		System.out.println("�޸�: " + user);
		return SUCCESS;
	}
	
	/**
	 * @SessionAttributes ���˿���ͨ��������ָ����Ҫ�ŵ��Ự�е�������(ʵ��ʹ�õ���value����ֵ)��
	 * ������ͨ��ģ�����ԵĶ�������ָ����Щģ��������Ҫ�ŵ��Ự��(ʵ����ʹ�õ���types����ֵ)
	 * ע��:��ע��ֻ�ܷ���������棬�����ܷ��ڷ�����
	 * @param map
	 * @return
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String,Object> map){
		//User user = new User("Tom","123456","tom@atguigu.com",15);
		//map.put("user", user);
		map.put("school", "atguigu");
		return SUCCESS;
	}
	
	/**
	 * Ŀ�귽���������Map����(ʵ����Ҳ������Model���ͻ�ModelMap����)�Ĳ���
	 * @param map
	 * @return
	 */
	@RequestMapping("/testMap")
	public String testMap(Map<String,Object> map){
		System.out.println(map.getClass().getName());
		map.put("names",Arrays.asList("Tom","Jerry","Mike"));
		return SUCCESS;
	}
	
	/**
	 * Ŀ�귽���ķ���ֵ������ModelAndView����
	 * ���п��԰�����ͼ��ģ����Ϣ
	 * SpringMVC ���ModelAndView��model�����ݷ��뵽request������С�
	 * @return
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView(){
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		//���ģ�����ݵ�ModelAndView��
		modelAndView.addObject("time",new Date());
		return modelAndView;
	}
	
	
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
		//out.write("hello springmvc") ����ҳ���ϴ�ӡ
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
