package spring.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.domain.User;
import spring.service.user.UserService;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	public UserController() {
		// TODO Auto-generated constructor stub
		System.out.println("==>UserController default Constructor call.....");
	}
	
	@RequestMapping("/logon.do")
	public ModelAndView logon() {
		System.out.println("\n:: ==> logon() start....");
		
	String message = "[logon()] 아이디,패스워드 3자이상 입력하세요.";
	
		//System.out.println("\n:: ==> logon() start....");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/logon.jsp");
		modelAndView.addObject("message", message);
		
		System.out.println("\n:: ==> logon() end....");
		return modelAndView;
	}
	
	@RequestMapping("/home.do")
	public ModelAndView home() {
		
		System.out.println("\n:: ==> home() start....");
		String message = "[home() ]WELCOME";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/home.jsp");
		modelAndView.addObject("message",message);
		
		System.out.println("\n:: ==> home() end....");
		
		return modelAndView;
	}
	@RequestMapping(value="/logonAction.do", method=RequestMethod.GET)
	public ModelAndView logonAction() {
		
		System.out.println("\n:: ==> logonAction() start....");
		
		//String message = "[home() ]WELCOME";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/logon.do");
	
		
		System.out.println("\n:: ==> logonAction() end....");
		
		return modelAndView;
	}
	@RequestMapping(value="/logonAction.do", method=RequestMethod.POST)
	public ModelAndView logonAction(@ModelAttribute("user") User user,
									HttpSession session) throws Exception{
		
		System.out.println("[logonAction() method=RequestMethod.POST start....");
		String viewName = "/user/logon.jsp";
		
//		UserDAO userDAO = new UserDAO();
//		userDAO.getUser(user);
		User returnUser = userService.getUser(user.getUserId());
		if(returnUser.getPassword().equals(user.getPassword())) {
			returnUser.setActive(true);
			user=returnUser;
		}
		
		if(user.isActive()) {
			viewName = "/user/home.jsp";
			session.setAttribute("sessionUser",user);
		}
	//}
	System.out.println("[action :"+viewName+"]");
	
	String message = null;
	if(viewName.equals("/user/home.jsp")) {
		message = "[logonAction() ] WELCOME";
	}else {
		message = "[logonAction()]아이디,패스워드 3자이상 입력하세요.";
	}
	
	
	ModelAndView modelAndView = new ModelAndView();
	modelAndView.setViewName(viewName);
	modelAndView.addObject("message",message);
	
	//System.out.println("[logonAction() end.....]\n");
	System.out.println("[logonAction() method=RequestMethod.POST end....");
	return modelAndView;

	}
	
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpSession session) {
		
		System.out.println("\n:: ==> logout() start....");
		
		session.removeAttribute("sessionUser");
		
		String message = "[Logout()] 아이디,패스워드 3자이상 입력하세요.";
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/logon.jsp");
		modelAndView.addObject("message", message);
		System.out.println("\n:: ==> logout() end....");
		
		return modelAndView;
	}

}
