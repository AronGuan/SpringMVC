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
	//重定向
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


	//都会转成ModelAndView对象，借助视图解析器得到最终的视图对象(View)物理视图
	@RequestMapping("/testViewAndViewResolver")
	public String testViewAndViewResolver(){
		System.out.println(":testViewAndViewResolver");
		return SUCCESS;
	}
	
	/**
	 * 1. 有@ModelAttribute标记的方法，会在每个目标方法执行之前被SpringMVC调用
	 * 2. @ModelAttribute注解也可以来修饰目标方法POJO类型的入参，其value属性值有如下的作用:
	 * 1) SpringMVC会使用value属性值在implicitModel中查找对应的对象，若存在则会直接传入到目标方法的入参中
	 * 2) SpringMVC会一value为key,POJO类型的对象为value，存入到request中
	 * @param id
	 * @param map
	 */
	//用于更新部分数据
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id,
			Map<String,Object> map){
		System.out.println("modelAttribute method");
		if(id != null){
			//模拟从数据库中获取对象
			User user = new User(1, "tom", "123456","tom@atguigu", 12);
			System.out.println("从数据库中获取一个对象:" + user );
			map.put("user", user);
		}
	}
	/**
	 * 运行流程:
	 * 1. 执行@ModelAttribute注解修饰的方法:从数据库 中取出对象,把对象法如到Map中，键为:User
	 * 2. SpringMVC从Map中取出User对象，并把表单的请求参数赋给该User对象的对应属性.
	 * 3. SpringMVC把 上述对象传入目标方法的参数
	 * 
	 *  注意: 在@ModelAttribute修饰的方法中，放入到Map时的键需要和目标方法入参类型的第一个字母
	 *  小写的字符串一致！都是"user"
	 *  
	 *  SpringMVC确定目标方法POJO类型入参的过程
	 *  1. 确定一个key:
	 *  1)若目标方法的POJO类型的参数没有使用@@ModelAttribute作为修饰，则key为POJO类名第一个字母的小写
	 *  2)若使用@@ModelAttribute来修饰,则key为@@ModelAttribute注解的value属性值。
	 *  2. 在implicitModel中查找key对应的对象，若存在，则作为入参传入
	 *  1)若在@@ModelAttribute标记的方法中在Map中保存过，且key和1确定的key一直，则会获取到
	 *  3. 若implicitModel中不存在key对应的对象，则检查当前的Handler是否使用@SessioinAttributes注解修饰，
	 *  若使用了该注解，且@SessionAttributes注解的value属性值中包含了key,则会从HttpSession中来获取key所
	 *  对应的value值，若存在则直接传入到目标方法的入参中，若不存在则将抛出异常。
	 *  4. 若Handler没有标识@SessioinAttributes注解或@SessioinAttributes注解的value值中不包含key，则会
	 *  通过反射来创建POJO类型的参数，传入目标方法的参数
	 *  5. SpringMVC会把key和POJO类型的对象到保存implicitModel中，进而保存到request中。
	 *  
	 *  
	 *  源代码分析的流程
	 *  1. 调用@ModelAttribute注解修饰的方法,实际上把@ModelAttributes方法中Map中的数据放在了implicitModel中
	 *	2.解析请求处理器的目标参数，实际上该目标参数来自于WebDataBinder对象的target属性
	 *	1).创建WebDataBinder对象:
	 *	确定objectName 属性:若传入的attrName属性值为"",则objectName为类名第一个字母小写.
	 *	注意:attrName，若目标方法的POJO属性使用了@ModelAttribute来修饰，则attrName值即为@ModelAttribute
	 *	的value属性值
	 *	
	 *	确定target属性: 
	 *	>在implicitModel中查找attrName对应的属性值。若存在，ok
	 *	>若不存在:则验证当前Handler是否使用了@SessionAttributes进行修饰，若使用了，则尝试从Session中
	 *	获取attrName所对应的属性值,若session中没有对应的属性值，则抛出异常。
	 *	>若Handler没有使用@SessionAttributes进行修饰,或@SessionAttributes中没有使用value值指定的key
	 *	和attrName相匹配，则通过反射创建POJO对象
	 *	
	 *	2).SpringMVC把表单的请求参数赋给了WebDataBinder的target对应的属性。
	 *	3).SpringMVC会把WebDataBinder的attrName和target给到implicitModel，近而传到request域对象中
	 *	4).把WebDataBinder的target作为参数传递给目标方法的入参
	 * @param user
	 * @return
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(@ModelAttribute("user") User user){
		System.out.println("修改: " + user);
		return SUCCESS;
	}
	
	/**
	 * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外(实际使用的是value属性值)，
	 * 还可以通过模型属性的对象类型指定那些模型属性需要放到会话中(实际上使用的是types属性值)
	 * 注意:该注解只能放在类的上面，而不能放在方法上
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
	 * 目标方法可以添加Map类型(实际上也可以是Model类型或ModelMap类型)的参数
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
	 * 目标方法的返回值可以是ModelAndView类型
	 * 其中可以包含视图和模型信息
	 * SpringMVC 会把ModelAndView的model中数据放入到request域对象中。
	 * @return
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView(){
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		//添加模型数据到ModelAndView中
		modelAndView.addObject("time",new Date());
		return modelAndView;
	}
	
	
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
		//out.write("hello springmvc") 会在页面上打印
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
