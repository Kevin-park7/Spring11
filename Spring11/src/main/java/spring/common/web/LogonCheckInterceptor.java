package spring.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import spring.domain.User;

public class LogonCheckInterceptor extends HandlerInterceptorAdapter {
	

	public LogonCheckInterceptor() {
		// TODO Auto-generated constructor stub
		System.out.println("==> LogonCheckInterceptor() default Constructor call..........");
	}
	public boolean preHandle(HttpServletRequest request,
							HttpServletResponse response,
							Object handler)throws Exception{
		System.out.println("\n[LogonCheckInterceptor start.......");
		
		HttpSession session = request.getSession(true);
		User sessionUser = null;
		if((sessionUser = (User)session.getAttribute("sessionUser"))==null) {
			sessionUser = new User();
		}
		
		if(sessionUser.isActive()) {
			
			String uri = request.getRequestURI();
			
			if(uri.indexOf("logonAction")!=-1||uri.indexOf("logon")!=-1) {
				request.getRequestDispatcher("/user002/home.jsp").forward(request,response);
				System.out.println("[로그인 상태...로그인 후 불필요 한 요구");
				System.out.println("[LogonCheckInterceptor end.......]");
				return false;
			}
			System.out.println("[로그인 상태...]");
			System.out.println("[LogonCheckInterceptor end.......");
			return true;
		}else {//==> 미 로그인한 회원이라면
			
			//==> 로그인 시도 중...
			String uri= request.getRequestURI();
			if(uri.indexOf("logonAction")!=-1||uri.indexOf("logon")!=-1) {
				System.out.println("[로그인 시도 상태]....");
				System.out.println("[LogonCheckInterceptor end......]\n");
				return true;
			}
			request.getRequestDispatcher("/user002/logon.jsp").forward(request,response);
			System.out.println("[로그인 이전...]");
			System.out.println("LogonCheckInterceptor end......]\n");
			return false;
		}
	}
}
