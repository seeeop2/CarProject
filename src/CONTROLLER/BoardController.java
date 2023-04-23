package CONTROLLER;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BoardDAO;
import DAO.MemberDAO;
import VO.BoardVo;
import VO.MemberVo;




//게시판 관련 기능 요청이 들어 오면 호출되는 사장님(컨트롤러)
@WebServlet("/board1/*") 
public class BoardController extends HttpServlet {
	
	//BoardDAO객체를 저장할 참조변수
	BoardDAO boarddao;
	//MemberDAO객체를 저장할 참조변수
	MemberDAO memberdao;
	
	@Override
	public void init() throws ServletException {
		
		boarddao = new BoardDAO();
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
		
		// /write.bo  <- 새글 작성 화면 요청 주소!
		
		// /writePro.bo <- 입력한 새글 정보를 DB에 추가 요청 주소 !
		
		// /list.bo <- DB에 저장된 글목록을 조회 하여 보여주는 요청 주소!
			
		//1.4 조건에 따라서  포워딩 또는 보여줄 VIEW주소 경로를 저장할 변수
		String nextPage = null;
		
		//1.5 요청한 중앙화면  뷰 주소를 저장할 변수 
		String center = null;
		
		//1.6 BoardVo객체를 저장할 참조변수
		BoardVo vo = null;
		
		//1.7 ArrayList배열 저장할 참조변수
		ArrayList list = null;
		
		//1.8 (글 개수 조회)
		int count = 0;
		
		
		switch (action) {
		 	//새글  입력하는 화면 요청!
			case "/write.bo": 		
			
				HttpSession session = request.getSession();
					String memberid		= (String)session.getAttribute("id");
				
				//새글 을 입력하는 화면에 로그인한 회원의  이름, 아이디, 이메일을  보여주기 위해
				//member테이블에서 SELECT하여 가져와 합니다. 
				MemberVo membervo = memberdao.memberOne(memberid);
				
				//페이징 처리 를 위해 담는다.
				request.setAttribute("nowPage", request.getParameter("nowPage"));
				request.setAttribute("nowBlock", request.getParameter("nowBlock"));
				
				request.setAttribute("membervo", membervo);
				request.setAttribute("center", "board/write.jsp");
				nextPage = "/CarMain.jsp";
				break;
			
			//입력한 새글 정보를 DB에 추가 해줘~ 요청!
			case "/writePro.bo":
			//요청한 값 얻기 
				String writer = request.getParameter("w");
				String id = request.getParameter("i");
				String email = request.getParameter("e");
				String title = request.getParameter("t");
				String content = request.getParameter("c");
				String pass = request.getParameter("p");
			//요청한 값을 BoardVo객체의 각변수에 저장
				vo = new BoardVo();
				vo.setB_name(writer);
				vo.setB_id(id);
				vo.setB_email(email);
				vo.setB_title(title);
				vo.setB_content(content);
				vo.setB_pw(pass);
			//응답할 값 마련(DB에 새글 정보를 INSERT 후  성공하면 추가 메세지 마련)
				//1 -> DB에 새글 정보 추가 성공
				//0 -> DB에 새글 정보 추가 실패 
				int result = boarddao.insertBoard(vo);
				// "1" 또는 "0"
				String go = String.valueOf(result);
				
				//write.jsp
				//($.ajax()메소드 내부의 
				// success:function(data)의 data매개변수로 전달)
				if(go.equals("1")) {
					out.print(go);
				}else {
					out.print(go);
				}
				
				return;		
				
			//게시판 모든 글 조회 요청	
			case "/list.bo":	
					
				//요청한 값을 이용해  응답할 값을 마련
				//(글조회)
				list = boarddao.boardListAll();
				//(글 개수 조회)
				count = boarddao.getTotalRecord();
				//list.jsp페이지의 페이징 처리 부분에서
				//이전 또는 다음 또는 각 페이지번호를 클릭했을때... 요청받는 값얻기
				String nowPage = request.getParameter("nowPage");
				String nowBlock = request.getParameter("nowBlock");
				System.out.println(nowPage + "페이지번호");
				System.out.println(nowBlock + "블럭위치번호");		
				
				request.setAttribute("center", "board/list.jsp");
				request.setAttribute("list", list);
				request.setAttribute("count", count);
				
				//페이징 처리 를 위해 담는다.
				request.setAttribute("nowPage", nowPage);
				request.setAttribute("nowBlock", nowBlock);
				
				
				
				nextPage = "/CarMain.jsp";
				
				break;
	
				  //검색기준값, 검색어 입력하여 게시판 모든 글 조회 요청	
			case "/searchlist.bo":	
				
				//요청한 값 얻기(검색을 위해 선택한 option의 값 하나, 입력한 검색어)
				String key = request.getParameter("key");//검색기준값
				String word = request.getParameter("word");//검색어 
				
				//요청한 값을 이용해  응답할 값을 마련
				//(글조회)
				list = boarddao.boardList(key, word);
				//(글 개수 조회)
				count = boarddao.getTotalRecord(key, word);
				
				request.setAttribute("center", "board/list.jsp");
				request.setAttribute("list", list);
				request.setAttribute("count", count);
				
				nextPage = "/CarMain.jsp";
				
				break;				
				
			//글제목을 눌러 글을 조회한 후 보여주는 중앙 화면 요청!	
			case "/read.bo":	
				//list.jsp페이지에서 전달하여 요청한 3개의 값을 얻자
				String b_idx = request.getParameter("b_idx");
				String nowPage_ = request.getParameter("nowPage");
				String nowBlock_ = request.getParameter("nowBlock");
				
				//글번호 (b_idx)를 이용해 수정 또는 삭제 를 위해 DB로부터 조회하기
				vo = boarddao.boardRead(b_idx);
				
				
				request.setAttribute("center", "board/read.jsp"); //중앙화면 주소 
				request.setAttribute("vo", vo);//글번호로 조회한 글하나의 정보  
				
				request.setAttribute("nowPage", nowPage_); //중앙화면 read.jsp로 전달을 위해 
				request.setAttribute("nowBlock", nowBlock_);
				request.setAttribute("b_idx", b_idx);
				
				nextPage = "/CarMain.jsp";
				
				break;
				
			case "/password.do": //글을 수정하기 위해 입력한 비밀번호가 DB에 저장되어 있는지 요청!
				
				//글에대한 글번호와  입력한 비밀번호 얻기
				String b_idx_ = request.getParameter("b_idx");
				String password = request.getParameter("pass");
				
				//DB작업
				boolean resultPass = boarddao.passCheck(b_idx_,password);
				
				System.out.println(resultPass);
				
				//입력한 비밀번호를 다시 한번 확인 하여 입력한 비밀번호가 DB에 저장되어 있는지 확인  
				//read.jsp에 $.ajax로 작성해 놓은 부분!!!!
				//success:function의 data매개변수로 웹브라우저를 거쳐 보냅니다!
				if(resultPass == true) {
					out.write("비밀번호맞음");
					return;
					
				}else {
					out.write("비밀번호틀림");
					return;
				}
				
			case "/updateBoard.do": //글 수정 요청주소를 받았을때...
				
				//수정시 입력한 정보들을 request에서 꺼내오기
				//요청한 값 얻기
				String idx_ = request.getParameter("idx");//글번호
				String email_ = request.getParameter("email");//수정을 위해 입력한 이메일
				String title_ = request.getParameter("title");//수정을 위해 입력한 제목
				String content_ = request.getParameter("content");//수정을 위해 입력한 글내용
				
				//요청한 값을 DB에 UPDATE시키기
				//그리고 UPDATE에 성공하면 1을 반환 실패하면 0을 반환
				int result_ = boarddao.updateBoard(idx_, email_, title_, content_);
				
				if(result_ == 1) {
					out.print("수정성공");
					return;
				}else {
					out.print("수정실패");
					return;
				}
			
			case "/deleteBoard.bo"://글 삭제요청주소를 받았을때..
				//삭제할 글번호 얻기
				String delete_idx = request.getParameter("b_idx");
				
				//DELETE작업
				String result__ = boarddao.deleteBoard(delete_idx);
					
				System.out.println(result__); //"삭제성공"
				
				if(result__.equals("삭제성공")) {
					//"삭제성공" $.ajax쪽으로 전달  read.jsp로~~
					out.print(result__);
					return;
				}else {
					//"삭제실패" $.ajax쪽으로 전달   read.jsp로~~
					out.print(result__);
					return;	
				}
			
			case "/reply.do" :  //답변작성할수 있는 화면 요청	
				
				//주글(부모글)의 글번호를 얻는다
				String b_idx__ = request.getParameter("b_idx");
				 
				//session영역을 얻고  session영역에 로그인한 아이디를 얻어 
				//아이디에 해당되는 답변글 작성자명과  이메일주소를 조회후 가져옵니다. 
				HttpSession session_reply = request.getSession();
			
				String id_reply = (String)session_reply.getAttribute("id");
				
				MemberVo membervo_ = null;
				
				if(id_reply != null) {
					
					membervo_ = memberdao.memberOne(id_reply);
					
				}
				//조회된 답변글을 작성하는 사람의 이름과 이메일주소가 저장된 MemberVo를 
				//request에 바인딩 합니다.
				request.setAttribute("membervo_", membervo_);
				//주글 글번호  바인딩
				request.setAttribute("b_idx", b_idx__);
				//중앙화면(답변글을 작성할수 있는 화면) View 주소 바인딩
				request.setAttribute("center", "board/reply.jsp");
				
				nextPage = "/CarMain.jsp";
				
				break;
				
			case "/replyPro.do" : 	//답변글 DB에 추가 요청!
				
				// 주글(부모글) 글번호  + 작성한 답변글 내용 얻기
				String super_b_idx = request.getParameter("super_b_idx");//부모글번호
				
				
				String reply_id = request.getParameter("id");//답변글 작성자의 ID
				String reply_name = request.getParameter("writer");//답변글 작성자명 
				String reply_email = request.getParameter("email"); //답변글을 작성자 이메일
				String reply_title = request.getParameter("title"); //답변글 제목 
				String reply_content = request.getParameter("content");//답변글 내용
				String reply_pass = request.getParameter("pass");//답변글 비밀번호 
				
				//DB에  입력한 답변글을 추가
				boarddao.replyInsertBoard(super_b_idx, 
										  reply_id,
										  reply_name,
										  reply_email,
										  reply_title,
										  reply_content,
										  reply_pass);
				//답변글 추가 성공 후 
				//다시~~ 전체글을 조회 하기 위해 요청주소를 nextPage변수에 담아서 
				nextPage = "/board1/list.bo";
				
				
			default:
				break;
		}
			
		//포워딩 (디스패처 방식)
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
		
		
		
	}	
	
}











