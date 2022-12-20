package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.Member;

public class MemberDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	//DB链接的防范 
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager
				.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "sys1234");
		return con;
	}
	
	//insert
	public String insert(HttpServletRequest request, HttpServletResponse response) {
		int custno = Integer.parseInt(request.getParameter("custno"));
		String custname = request.getParameter("custname");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String joindate = request.getParameter("joindate");
		String grade = request.getParameter("grade");
		String city = request.getParameter("city");
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "insert into member_tbl_02 values(?,?,?,?,to_date(?, 'yyyy-mm-dd'),?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, custno);
			ps.setString(2, custname);
			ps.setString(3, phone);
			ps.setString(4, address);
			ps.setString(5, joindate);
			ps.setString(6, grade);
			ps.setString(7, city);
			
			//insert, update,delete:성공한 레코드의 객수를 반환.
			result = ps.executeUpdate();
			
			System.out.println(result);
			
			conn.close();
			ps.close();
			
			/* if(result) {} */
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "add";
	}
	
	
	//회원번호 자동증가
	public String nextCustno(HttpServletRequest request, HttpServletResponse response) {
		try {
			conn = getConnection();
			String sql = "select max(custno)+1 custno from member_tbl_02";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			int custno = 0;
			
			if(rs.next()) custno = rs.getInt(1);
			
			request.setAttribute("custno", custno);
			
			conn.close();
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "add.jsp";
	}
	
	
	
	//회원목록조회/수정
	public String selectAll(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Member>list = new ArrayList<Member>();
		try {
			conn = getConnection();
			//太长了用sql+= "DECODE()"加起来
			String sql= "select custno, custname, phone, address, TO_CHAR(joindate, 'YYYY-MM-DD')as joindate,";
			sql+= "DECODE(grade, 'A', 'VIP', 'B', '일반', '직원') grade, city from member_tbl_02 order by custno";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {  //实行下一个，没有就跳过实行下一个。
				Member member = new Member();
				member.setCustno(rs.getInt(1));
				member.setCustname(rs.getString(2));
				member.setPhone(rs.getString(3));
				member.setAddress(rs.getString(4));
				member.setJoindate(rs.getString(5));
				member.setGrade(rs.getString(6));
				member.setCity(rs.getString(7));
				
				list.add(member);
			}
			
			request.setAttribute("list", list);
			
			conn.close();
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list.jsp";
		
	}
	
	//회원정보수정(先拿来数据 데이터를 먼저 가져와)
	public String modify(HttpServletRequest request, HttpServletResponse response) {
		try {
			conn = getConnection();
			int custno = Integer.parseInt(request.getParameter("custno"));
			
			String sql = " select custname, phone, address, TO_CHAR(joindate,'YYYY-MM-DD') joindate, grade, city ";
			sql += " from member_tbl_02 where custno=" + custno;  //换行的时候一定要空格，要不然city from 变成 cityfrom
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			Member member = new Member();
			if(rs.next()) {
				member.setCustno(custno);
				member.setCustname(rs.getString(1));
				member.setPhone(rs.getString(2));
				member.setAddress(rs.getString(3));
				member.setJoindate(rs.getString(4));
				member.setGrade(rs.getString(5));
				member.setCity(rs.getString(6));
			}
			
			request.setAttribute("member", member);
			request.setAttribute("custno", custno);
			
			conn.close();
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modify.jsp";
	} 
	
	//회원 정보 업데이트 
	public int update(HttpServletRequest request, HttpServletResponse response) {
		int custno = Integer.parseInt(request.getParameter("custno"));
		String custname = request.getParameter("custname");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String joindate = request.getParameter("joindate");
		String grade = request.getParameter("grade");
		String city = request.getParameter("city");
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "update member_tbl_02 set"
					+ " custname = ?, "
					+ " phone = ?,"
					+ " address = ?, "
					+ " joindate = to_date(?, 'YYYY-MM-DD'),"
					+ " grade = ?, "
					+ " city = ?"
					+ " where custno = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, custname);
			ps.setString(2, phone);
			ps.setString(3, address);
			ps.setString(4, joindate);
			ps.setString(5, grade);
			ps.setString(6, city);
			ps.setInt(7, custno);
			
			result = ps.executeUpdate();
			
			conn.close();
			ps.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
			   
	}
}
