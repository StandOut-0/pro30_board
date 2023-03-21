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
			String viewName = getViewName(request);//요청한 uri에서 viewname을 가져와 저장
			request.setAttribute("viewName", viewName);//파라미터 set
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
	//요청한 uri 문자열 안의 viewname만을 추출하여 전달 하게 하는 과정
	private String getViewName(HttpServletRequest request) throws Exception {
		
		//main.do로 요청했을떄 아래와 같이 나온다.
		//getContextPath: /pro30
		//request_uri: null
		//getRequestURI: /pro30/main.do
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {uri = request.getRequestURI();}

		//contextPath길이를 잰다.
		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		//프로젝트 경로와 파라미터 값을 제하는 과정
		//indexOf() 값이 존재하지 않으면 -1이 반환
		//";"와 "?"이 있는지 확인하고 있다면 end는 그 문자열내 index가 되고, 없다면 전체 길이를 가지게 된다.
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		//substring(), 위에서 필터링된 부분을 fileName에 저장한다.
		String fileName = uri.substring(begin, end);

		//.이 있다면 처음부터 그 부분까지를 잘래내고, /이 있다면 그 부분부터 시작해 잘라내 fileName을 리턴한다.
		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		if (fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/", 1), fileName.length());
		}
		return fileName;
	}
}
