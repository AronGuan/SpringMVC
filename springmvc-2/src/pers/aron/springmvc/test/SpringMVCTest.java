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
	 * DefaultHandlerException����һЩ������쳣
	 * @return
	 */
	@RequestMapping(value="/testDefaultHandlerExceptionResolver",method=RequestMethod.POST)
	public String testDefaultHandlerExceptionResolver(){
		System.out.println("testDefaultHandlerExceptionResolver..");
		return "success";
	}
	
	@ResponseStatus(reason="����",value=HttpStatus.NOT_FOUND)
	@RequestMapping("/testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("i")int i){
		if(i == 13){
			throw new UserNameNotMatchPasswordException();
		}
		System.out.println("testResponseStatusExceptionResolver..");
		return "success";
	}
	
	/**
	 * 1.��@ExceptionHandler����������п��Լ���Exception���͵Ĳ������ò�������Ӧ�������쳣����
	 * 2.@ExceptionHandler����������в��ܴ���Map,��ϣ�����쳣��Ϣ����ҳ���ϣ���Ҫʹ��ModelAndView��Ϊ����ֵ
	 * 3.@ExceptionHandler������ǵ��쳣�����ȼ�������
	 * 4.@ControllerAdvice:����ڵ�ǰHandler���Ҳ���@ExceptionHandler����������ǰ�������ֵ��쳣
	 * ��ȥ@ControllerAdvice��ǵ����в���@ExceptionHandler��ǵķ����������쳣������ȫ��
	 * @param ex
	 * @param map
	 * @return
	 */
//	@ExceptionHandler({ArithmeticException.class})
//	public ModelAndView handlerArithmeticException(Exception ex){
//		System.out.println("���쳣��: " + ex);
//		ModelAndView mv = new ModelAndView("error");
//		mv.addObject("exception",ex);
//		return mv;
//	}
//	
//	@ExceptionHandler({RuntimeException.class})
//	public ModelAndView handlerArithmeticException2(Exception ex){
//		System.out.println("[���쳣��]: " + ex);
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
	
	//������@ResponseBody������Ӧ@RequestBody��ת��
	//���������ļ��ϴ�����Ϊ�������ֱ������ļ���
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
