package CONTROLLER;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CarDAO;
import DAO.MemberDAO;
import VO.MemberVo;



@WebServlet("/member/*") 
public class MemberController extends HttpServlet {
	
	//MemberDAO객체를 저장할 참조변수
	MemberDAO memberdao;
	
	@Override
	public void init() throws ServletException {
		
		memberdao = new MemberDAO();
	}
		
	@Override
	protected void doGet(HttpServletRequest request, 
						 HttpServletResponse response) 
						 throws ServletException, IOException {
		doHandle(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, 
						 HttpServletResponse response) 
						 throws ServletException, IOException {
		doHandle(request, response);
	}
	
	
	protected void doHandle(HttpServletRequest request, 
							HttpServletResponse response) 
							 throws ServletException, IOException {
	
		//1.한글처리
		request.setCharacterEncoding("UTF-8");
		//1.1 웹브라우저로 응답할 데이터 종류 설정
		response.setContentType("text/html; charset=utf-8");
		//1.2 웹브라우저와 연결된 출력 스트림 통로 만들기
		PrintWriter out = response.getWriter();
		
		//1.3 서블릿으로 요청한 주소를 request에서 얻기
		String action = request.getPathInfo();//2단계 요청 주소 
		System.out.println("요청한 주소 : " + action);
		
		// /join.me <- 회원가입시 입력하는 화면 요청 주소!
		// /joinIdCheck.me <- 아이디 중복 확인 요청 주소!
		// /joinPro.me <- 회원가입 요청 주소!
		// /login.me <- 로그인 요청을 위해 아이디와 비밀번호를 입력하는 화면 요청!
		// /loginPro.me <- 로그인 요청 주소!
		// /logout.me <- 로그아웃 요청 주소!
		
		
		//1.4 조건에 따라서  포워딩 또는 보여줄 VIEW주소 경로를 저장할 변수
		String nextPage = null;
		
		//1.5 요청한 중앙화면  뷰 주소를 저장할 변수 
		String center = null;
		
		switch (action) {
		 	//회원가입 시 입력하는 화면 요청!
			case "/join.me": 	
				center = request.getParameter("center"); //members/join.jsp  중앙화면뷰 주소 얻기
				request.setAttribute("center", center);//members/join.jsp  중앙화면뷰 주소 바인딩 
				nextPage = "/CarMain.jsp";
				break;
			//아이디 중복 체크 요청!	
			case "/joinIdCheck.me":
				//입력한 아이디 얻기
				String id = request.getParameter("id");
					  
				//입력한 아이디가 DB에 저장되어 있는지 중복 체크 작업 
				//true -> 중복 , false -> 중복아님  둘중 하나를 반환 받음
				boolean result = memberdao.overlappedId(id);
				
				//아이디 중복결과를 다시 한번 확인 하여 조건값을 
				//join.jsp파일과 연결된 join.js파일에 작성해 놓은
				//success:function의 data매개변수로 웹브라우저를 거쳐 보냅니다!
				if(result == true) {
					out.write("not_usable");
					return;
					
				}else {
					out.write("usable");
					return;
				}
			
			//회원가입 요청 주소를 받았을때!!!	
			case "/joinPro.me":
				//요청한 값 얻기
				String user_id = request.getParameter("id");
				String user_pass = request.getParameter("pass");
				String user_name = request.getParameter("name");
				String user_age = request.getParameter("age");
				String user_gender = request.getParameter("gender");
				System.out.println(user_gender); 
				
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				String address3 = request.getParameter("address3");
				String address4 = request.getParameter("address4");
				String address5 = request.getParameter("address5");
				String user_address = address1+address2+address3+address4+address5;
				
				String user_email = request.getParameter("email");
				String user_tel = request.getParameter("tel");
				String user_hp = request.getParameter("hp");

				MemberVo vo = new MemberVo(user_id, 
										   user_pass, 
										   user_name, 
										   Integer.parseInt(user_age), 
										   user_gender, 
										   user_address, 
										   user_email, 
										   user_tel, 
										   user_hp);   
				memberdao.inserMember(vo);
				
				
				nextPage="/CarMain.jsp";
				
				break;
			
			//로그인 요청 화면 
			case "/login.me":
				
				//중앙화면 주소 바인딩
				request.setAttribute("center", "members/login.jsp");
				
				//전체 메인화면 주소 저장
				nextPage = "/CarMain.jsp";
				
				break;
			
			//로그인 요청 주소!	
			case "/loginPro.me": 	
				
				//요청한 값 얻기
				String login_id = request.getParameter("id");
				String login_pass = request.getParameter("pass");
				
				//요청한 값을 이용해 클라이언트의 웹브라우저로 응답할 값을 마련
				//요약 : DB작업 등의 비즈니스로직처리
				//check변수에 저장되는 값이 1이면   아이디, 비밀번호 맞음
				//					  0이면  아이디맞음, 비밀번호 틀림
				//                    -1이면 아이디 틀림 , 비밀번호 맞음 
				int check = memberdao.userCheck(login_id,login_pass);
				
				if(check == 0) { //아이디는 맞고, 비밀번호 틀림
					out.println("<script>");
					out.println(" window.alert('비밀번호 틀림');");
					out.println(" history.go(-1);");
					out.println("</script>");
					return;
					
				}else if(check == -1) {//아이디 틀림 , 비밀번호 맞음 	
					out.println("<script>");
					out.println(" window.alert('아이디 틀림');");
					out.println(" history.back();");
					out.println("</script>");
					return;				
				}
				
				//check = 1  아이디 맞음, 비밀번호 맞음
				//로그인 처리를 위해 session메모리 영역에 세션값 저장 후 포워딩 
				
				//session메모리 생성
				HttpSession session = request.getSession();
				//session메모리에 입력한 아이디 바인딩(저장)
				session.setAttribute("id", login_id);
				
				//메인화면 VIEW 주소
				nextPage = "/CarMain.jsp";
				
				break;
			
			//로그아웃요청을 받았을때...
			case "/logout.me" :
				//기존에 생성했던 session메모리 얻기 
				HttpSession session_ = request.getSession();
				session_.invalidate(); //세션에 저장된 아이디 제거 
				
				//메인화면 VIEW 주소
				nextPage = "/CarMain.jsp";	
				break;
						
			default:
				break;
		}
		
		
		//포워딩 (디스패처 방식)
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
		
		
		
	}	
	
}











