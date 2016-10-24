package com.catlivevideo.living.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catlivevideo.living.dao.UserDao;
import com.catlivevideo.living.dao.impl.UserDapImpl;
import com.catlivevideo.living.entity.ResponseObject;
import com.catlivevideo.living.entity.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class UserSerlvlet
 */
@WebServlet("/user")
public class UserSerlvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSerlvlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String flag = request.getParameter("flag");
		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");
		UserDao uDao = new UserDapImpl();
		PrintWriter writer = response.getWriter();
		ResponseObject<User> result = null;
		if("register".equalsIgnoreCase(flag)){
			if(userName!=null&&!userName.isEmpty()&&pwd!=null&&!pwd.isEmpty()){
				User user = uDao.register(userName, pwd);
				if(user!=null){
					result = new ResponseObject<User>(1, "注册成功", user);
					
				}else{
					result = new ResponseObject<User>(0, "用户已存在");
				}
				
			}else{
				result = new ResponseObject<User>(0, "同户名和密码不能为空");
			}
			writer.print(new Gson().toJson(result));
			writer.flush();
			writer.close();
		
		}else if("login".equalsIgnoreCase(flag)){
			if(userName!=null&&!userName.isEmpty()&&pwd!=null&&!pwd.isEmpty()){
				User user = uDao.login(userName, pwd);
				if(user!=null){
					result = new ResponseObject<User>(1, "登陆成功", user);
					
				}else{
					result = new ResponseObject<User>(0, "用用户名或密码错误");
				}
				
			}else{
				result = new ResponseObject<User>(0, "同户名和密码不能为空");
			}
			writer.print(new Gson().toJson(result));
			writer.flush();
			writer.close();
			
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	  

}
