package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.MemberDAO;


@WebServlet("/")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public MemberController() {
        super();
        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}
	
	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context = request.getContextPath(); //tomcat의 context path 가져온다. server.xml의 /HRD_1234 
		String command = request.getServletPath(); //경로의 맨 끝 파일명을 가져온다.-> http://localhost:8090/HRD_1234/index.jsp
		String site = null;
		
		System.out.println(context + ", " + command);
		
		MemberDAO member = new MemberDAO();
		
		switch(command) {
		case "/home":
			site = "index.jsp";
			break;
		case "/insert":
			site = member.insert(request, response);
			break;
		case "/list":
			site = member.selectAll(request, response);
			break;
		case "/add":
			site = member.nextCustno(request, response);
			break;
		case "/modify":
			site = member.modify(request, response);
			break;
		case "/update":
			int result1 = member.update(request, response);
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			if(result1 == 1) {  //update success
				out.println("<script>");
				out.println("alert('회원수정이 완료 되어습니다!'); location.href= '" + context +"'; ");  //location.href= '/hrd_1234';
				out.println("</script>");
				out.flush();
			}else {
				out.println("<script>");
				out.println("alert('수정 실패!'); location.href= '" + context +"'; ");  //location.href= '/hrd_1234';
				out.println("</script>");
				out.flush();
			}
			break;
		}
		getServletContext().getRequestDispatcher("/" + site).forward(request, response);
		
	}


}
