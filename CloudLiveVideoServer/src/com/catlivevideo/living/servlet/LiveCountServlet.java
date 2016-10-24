package com.catlivevideo.living.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
 * Servlet implementation class LiveCountServlet
 */
@WebServlet("/livecount")
public class LiveCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LiveCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		String isLive = request.getParameter("isLive");
		UserDao uDao = new UserDapImpl();
		int nPage = 0;
		int nSize = 0;
		int nIsLive = 0;
		ResponseObject<List<User>> result = null;
		if(page!=null&&size!=null){
			nPage = Integer.parseInt(page);
			nSize = Integer.parseInt(size);
			if(isLive!=null){
				nIsLive = Integer.parseInt(isLive);
				List<User> liveList = uDao.getLiveList(nPage, nSize, nIsLive);
				long count = uDao.getCount(nIsLive);
				if(liveList!=null&&liveList.size()>0){
					result = new ResponseObject<List<User>>(1, liveList);
					result.setPage(nPage);
					result.setSize(nSize);
					result.setCount((long) Math.ceil(count/nSize));
				}else{
					if(nIsLive==1){
						result = new ResponseObject<List<User>>(0, "没有更多正在直播信息");
					}else{
						result = new ResponseObject<List<User>>(0, "没有更多未直播信息");
					}
					
				}
			}else{
				List<User> allLiveList = uDao.getAllLiveList(nPage, nSize);
				long allCount = uDao.getAllCount();
				if(allLiveList!=null&&allLiveList.size()>0){
					result = new ResponseObject<List<User>>(1, allLiveList);
					result.setPage(nPage);
					result.setSize(nSize);
					result.setCount((long) Math.ceil(allCount/nSize));
				}else{
					result = new ResponseObject<List<User>>(0, "没有更多直播信息");
				}
			}
			
				
		}
		PrintWriter writer = response.getWriter();
		writer.print(new Gson().toJson(result));
		writer.flush();
		writer.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
