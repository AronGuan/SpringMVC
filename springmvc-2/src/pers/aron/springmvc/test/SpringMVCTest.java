package pers.aron.springmvc.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import pers.aron.springmvc.crud.dao.EmployeeDao;
import pers.aron.springmvc.crud.entities.Employee;

@Controller
public class SpringMVCTest {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	/**
	 * DefaultHandlerException处理一些特殊的异常
	 * @return
	 */
	@RequestMapping(value="/testDefaultHandlerExceptionResolver",method=RequestMethod.POST)
	public String testDefaultHandlerExceptionResolver(){
		System.out.println("testDefaultHandlerExceptionResolver..");
		return "success";
	}
	
	@ResponseStatus(reason="测试",value=HttpStatus.NOT_FOUND)
	@RequestMapping("/testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("i")int i){
		if(i == 13){
			throw new UserNameNotMatchPasswordException();
		}
		System.out.println("testResponseStatusExceptionResolver..");
		return "success";
	}
	
	/**
	 * 1.在@ExceptionHandler方法的入参中可以加入Exception类型的参数，该参数即对应发生的异常对象
	 * 2.@ExceptionHandler方法的入参中不能传入Map,如希望把异常信息传到页面上，需要使用ModelAndView作为返回值
	 * 3.@ExceptionHandler方法标记的异常有优先级的问题
	 * 4.@ControllerAdvice:如果在当前Handler中找不到@ExceptionHandler方法来处理当前方法出现的异常
	 * 则将去@ControllerAdvice标记的类中查找@ExceptionHandler标记的方法来处理异常，用于全局
	 * @param ex
	 * @param map
	 * @return
	 */
//	@ExceptionHandler({ArithmeticException.class})
//	public ModelAndView handlerArithmeticException(Exception ex){
//		System.out.println("出异常了: " + ex);
//		ModelAndView mv = new ModelAndView("error");
//		mv.addObject("exception",ex);
//		return mv;
//	}
//	
//	@ExceptionHandler({RuntimeException.class})
//	public ModelAndView handlerArithmeticException2(Exception ex){
//		System.out.println("[出异常了]: " + ex);
//		ModelAndView mv = new ModelAndView("error");
//		mv.addObject("exception",ex);
//		return mv;
//	}
	
	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver(@RequestParam("i")int i){
		System.out.println("result: " + (10/i));
		return "success";
	}
	
	@RequestMapping("/testFileUpload")
	public String testFileUpload(@RequestParam("desc") String desc,
			@RequestParam("file") MultipartFile file) throws IOException{
		System.out.println("desc: " + desc);
		System.out.println("OriginalFilename: " + file.getOriginalFilename());
		System.out.println("InputStream: " + file.getInputStream());
		return "success";
	}
	
	@RequestMapping("/i18n")
	public String testI18n(Locale locale){
		String val=messageSource.getMessage("i18.user",null, locale);
		System.out.println(val);
		return "i18n";
	}
	
	@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException{
		byte[] body = null;
		ServletContext servletContext = session.getServletContext();
		InputStream in = servletContext.getResourceAsStream("/files/jboss.txt");
		body = new byte[in.available()];
		in.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition","attachment;filename=abc.txt");
		
		HttpStatus statusCode = HttpStatus.OK;
		
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body,headers,statusCode);
		return response;
	}
	
	//对请求@ResponseBody或者响应@RequestBody的转换
	//不能用来文件上传，因为不能区分表单域还是文件域
	@ResponseBody
	@RequestMapping("/testHttpMessageConverter")
	public String testHttpMessageConverter(@RequestBody String body){
		System.out.println(body);
		return "helloworld! " + new Date();
	}
	
	
	@RequestMapping("/testConversionServiceConverser")
	public String testConverter(@RequestParam("employee") Employee employee){
		System.out.println("save: " + employee);
		employeeDao.save(employee);
		return "redirect:/emps";
	}
	
	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson(){		
		return employeeDao.getAll();
	}
}
