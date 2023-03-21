package com.myspring.pro30.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.myspring.pro30.HomeController;

public class ViewNameInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			String viewName = getViewName(request);//��û�� uri���� viewname�� ������ ����
			request.setAttribute("viewName", viewName);//�Ķ���� set
		} catch (Exception e) {e.printStackTrace();}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	//getViewName
	//��û�� uri ���ڿ� ���� viewname���� �����Ͽ� ���� �ϰ� �ϴ� ����
	private String getViewName(HttpServletRequest request) throws Exception {
		
		//main.do�� ��û������ �Ʒ��� ���� ���´�.
		//getContextPath: /pro30
		//request_uri: null
		//getRequestURI: /pro30/main.do
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {uri = request.getRequestURI();}

		//contextPath���̸� ���.
		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		//������Ʈ ��ο� �Ķ���� ���� ���ϴ� ����
		//indexOf() ���� �������� ������ -1�� ��ȯ
		//";"�� "?"�� �ִ��� Ȯ���ϰ� �ִٸ� end�� �� ���ڿ��� index�� �ǰ�, ���ٸ� ��ü ���̸� ������ �ȴ�.
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		//substring(), ������ ���͸��� �κ��� fileName�� �����Ѵ�.
		String fileName = uri.substring(begin, end);

		//.�� �ִٸ� ó������ �� �κб����� �߷�����, /�� �ִٸ� �� �κк��� ������ �߶� fileName�� �����Ѵ�.
		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		if (fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/", 1), fileName.length());
		}
		return fileName;
	}
}
