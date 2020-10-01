package com.how2java.tmall.interceptor;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Category;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import com.how2java.tmall.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CategoryNamesBelowSearchInterceptor extends AbstractInterceptor{
	
	@Autowired
	CategoryService categoryService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(StrutsStatics.HTTP_REQUEST);
		ServletContext servletContext = (ServletContext)ctx.get(StrutsStatics.SERVLET_CONTEXT);
		
		String contextPath = servletContext.getContextPath();
		String uri = request.getRequestURI();
		uri = StringUtils.remove(uri, contextPath);
		if(uri.endsWith("/fore")){
			List<Category> cs = categoryService.list();
			ctx.getSession().put("cs", cs);
		}
		return invocation.invoke();
	}

}