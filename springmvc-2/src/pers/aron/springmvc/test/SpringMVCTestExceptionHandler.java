package pers.aron.springmvc.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class SpringMVCTestExceptionHandler {

	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handlerArithmeticException(Exception ex){
		System.out.println("-->≥ˆ“Ï≥£¡À: " + ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception",ex);
		return mv;
	}
}
