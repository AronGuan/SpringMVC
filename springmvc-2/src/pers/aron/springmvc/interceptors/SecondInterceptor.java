package pers.aron.springmvc.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecondInterceptor implements HandlerInterceptor{

	/**
	 * 渲染视图之后被调用，释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("[Secondnterceptor] afterCompletion");
		
	}

	/**
	 * 调用目标方法之后，但渲染视图之前
	 * 可以对请求域中的属性或视图做出修改
	 */
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		System.out.println("[Secondnterceptor] postHandle");
		
	}
	
	/**
	 * 该方法在目标方法之前被调用
	若返回值为true,则继续调用后续的拦截器和目标方法。
	若返回值为false,则不会再调用后续的拦截器和目标方法
	
	可以考虑做权限，日志，事务等
	 */	
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		System.out.println("[Secondnterceptor] preHandle");
		return true;
	}
	
	

}
