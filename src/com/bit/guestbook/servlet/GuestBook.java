package com.bit.guestbook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.guestbook.dao.GuestbookDAO;
import com.bit.guestbook.vo.GuestbookVO;

@WebServlet("/gb")
public class GuestBook extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		request.setCharacterEncoding("UTF-8");
		String actionName = "";
		actionName = request.getParameter("a");
		if("add".equals(actionName))
		{
			String name = request.getParameter("name");
			String pwd = request.getParameter("password");
			String msg = request.getParameter("content");

			GuestbookVO vo = new GuestbookVO();
			vo.setName(name);
			vo.setPwd(pwd);
			vo.setMsg(msg);
			
			GuestbookDAO dao = new GuestbookDAO();
			dao.insert(vo);
			
			response.sendRedirect("/guestBook2/gb");
		} else if("deleteform".equals(actionName)) {
			// forwarding, request 연장
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);
		} else if("delete".equals(actionName)) {
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			
			GuestbookVO vo = new GuestbookVO();
			vo.setNo(Long.parseLong(no));
			vo.setPwd(password);
			
			GuestbookDAO dao = new GuestbookDAO();
			dao.delete(vo);
			
			response.sendRedirect("/guestBook2/gb");
		} else {
			/* index(default) action */
			GuestbookDAO dao = new GuestbookDAO();
			List<GuestbookVO> list = dao.getList();

			// view에 전달할 객체를 request 범위에 저장
			request.setAttribute("list", list);
			
			// forwarding, request 연장
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
