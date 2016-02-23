package com.bit.guestbook.dao.test;

import java.util.List;

import com.bit.guestbook.dao.GuestbookDAO;
import com.bit.guestbook.vo.GuestbookVO;

public class GuestbookDAOTest 
{
	public static void main(String[] args)
	{
		selectTest();
		deleteTest();
	}

	public static void insertTest()
	{
		GuestbookDAO dao = new GuestbookDAO();
		GuestbookVO vo = new GuestbookVO();
		vo.setName("khk");
		vo.setPwd("1234");
		vo.setMsg("hello world");
		
		dao.insert(vo);
		
		System.out.println("insertSuc");
	}
	
	public static void selectTest()
	{
		GuestbookDAO dao = new GuestbookDAO();
		List<GuestbookVO> guestbookList = dao.getList();
		for(GuestbookVO vo : guestbookList)
			System.out.println(vo);
		
		System.out.println("selectSuc");
	}
	
	public static void deleteTest()
	{
		GuestbookDAO dao = new GuestbookDAO();
		GuestbookVO vo = new GuestbookVO();
		vo.setNo(Long.parseLong("8"));
		vo.setPwd("1234");
		dao.delete(vo);
		System.out.println("deleteSuc");
	}
}
