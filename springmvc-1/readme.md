练习内容  
springMVC的基本流程  
1. web.xml配置
2. springmvc.xml(扫描包，视图解析器配置)
3. URL映射(@RequestMapping)

@RequestMapping
1. 属性value,method,params,header的使用
2. URL匹配
3. Rest风格,CRUD，post,get,put,delete,
HiddenHttpMethodFilter过滤器
URL值映射，通过@PathVariable  
请求参数的映射方式:@RequestParam  

请求头的映射方式:@RequestHeader  

@CookieValue:映射一个Cookie值，属性同@RequestParam  

使用POJO对象绑定请求参数值，参数为对象就行了  

使用Servlet API作为入参

模型数据  
ModelAndView,Map及Model，SessionAttributes,ModelAttribute

ModelAndView分析