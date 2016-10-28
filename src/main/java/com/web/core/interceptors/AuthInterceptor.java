package com.web.core.interceptors;

import com.web.util.RegExpUtil;
import com.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 权限拦截器
 * 
 * @author 曲国龙
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		//1.判断不需要过滤的URL
		if(url.matches(RegExpUtil.resourceFile) || excludeUrls.contains(url)){
			return true;
		}
		//2.判断用户是否登录
		if(null == WebUtils.getUser(request)){
//			response.sendRedirect(request.getContextPath()+"/");
			response.getWriter().write("<script>window.top.location=\""+request.getContextPath()+"/"+"\"</script>");
			return false;
		}
		//3.判断用户是否有权限访问URL
		if(WebUtils.getPrivilege(request).contains(requestUri) ||"admin".equals(WebUtils.getUser(request).getUsername())){
			return true;
		}
		//TODO 需要添加跳转到无权限页面
//		request.setAttribute("error", "对不起，权限不足无法执行该操作！");

		return false;
	}

	/**
	 * 转发
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "forword")
	public ModelAndView forword(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("loginController.do?login"));
	}

	@SuppressWarnings("unused")
	private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("webpage/login/timeout.jsp").forward(request, response);
	}

}
