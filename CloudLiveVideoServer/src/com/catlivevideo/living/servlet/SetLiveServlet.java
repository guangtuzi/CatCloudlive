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
 * Servlet implementation class SetLiveServlet
 */
@WebServlet("/setlive")
public class SetLiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetLiveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String isLiving = request.getParameter("isLiving");
		String accountID = request.getParameter("accountID");
		UserDao uDao = new UserDapImpl();
		if(isLiving!=null&&accountID!=null){
			int nIsLiving = Integer.parseInt(isLiving);
			uDao.setLive(accountID,nIsLiving);
			PrintWriter writer = response.getWriter();
			if(nIsLiving==0){
				writer.print("关闭直播");
			}else{
				writer.print("开始直播");
			}
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
