package com.bit.guestbook.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.bit.guestbook.vo.*;

public class GuestbookDAO 
{
	private Connection getConnection() throws SQLException
	{
		Connection conn = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Load Success");
			
			// 2. Connection 얻기
			String url = "jdbc:oracle:thin:@localhost:1521:testDB";
			conn = DriverManager.getConnection(url, "webdev", "webdev");
			System.out.println("JDBC Connection Success");
		}
		catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void insert(GuestbookVO vo)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = getConnection();
			
			// 3. Statement 준비
			String sql = "insert into guestbook values(GUESTBOOK_SEQ.nextval, ?, ?, ?, sysdate)";
			pstmt = conn.prepareStatement(sql);
			
			// 4. Statement 바인딩
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getMsg());
			
			// 5. Query 실행
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}
		finally {
			// 6. 사용 자원 정리
			try {
				if(pstmt != null)
					pstmt.close();
			} catch(SQLException e) {
				System.out.println("SQLException");
				e.printStackTrace();
			}
		}
	}
	
	public void delete(GuestbookVO vo)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = getConnection();
			
			// 3. Statement 준비
			String sql = "delete from guestbook where no=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPwd());
			
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}
		finally {
			// 6. 사용 자원 정리
			try {
				if(pstmt != null)
					pstmt.close();
			} catch(SQLException e) {
				System.out.println("SQLException");
				e.printStackTrace();
			}
		}
	}
	
	public List<GuestbookVO> getList()
	{
		List<GuestbookVO> guestbookList = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try
		{
			conn = getConnection();
			
			// 3. Statement 생성
			stmt = conn.createStatement();
			
			// 4. Query 실행
			String sql = "select no, name, message, to_char(reg_date, 'YYYY-MM-DD HH:MI:SS') from guestbook order by reg_date desc";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String msg = rs.getString(3);
				String regDate = rs.getString(4);
				
				GuestbookVO vo = new GuestbookVO();
				vo.setNo(no);
				vo.setName(name);
				vo.setMsg(msg);
				vo.setRegDate(regDate);
				
				guestbookList.add(vo);
			}
			
			return guestbookList;
			
		} catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}
		finally {
			// 6. 사용 자원 정리
			try {
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {
				System.out.println("SQLException");
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
