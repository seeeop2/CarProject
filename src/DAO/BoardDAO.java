package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import VO.BoardVo;
import VO.CarConfirmVo;
import VO.CarListVo;
import VO.CarOrderVo;
import VO.MemberVo;

//DB와 연결하여 비즈니스로직 처리 하는 클래스 
public class BoardDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	//커넥션풀 생성 후 커넥션 객체 얻는 생성자
	public BoardDAO() {
		//context.xml파일에 설정한
		//Resource태그에 적힌  DataSource커넥션풀 객체 받아오기	
		try {
			//웹프로젝트의 디렉터리에 접근하기 위한 객체 생성
			Context ctx = new InitialContext();
			//커넥션풀 얻기 
			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
		
		} catch (Exception e) {
			System.out.println("DB연결 실패! - " + e);
		}	
	}

	//자원해제 기능
	private void closeResource() {
		if(con != null)try {con.close();} catch (Exception e) {e.printStackTrace();}
		if(pstmt != null)try {pstmt.close();} catch (Exception e) {e.printStackTrace();}
		if(rs != null)try {rs.close();} catch (Exception e) {e.printStackTrace();}		
	}

	
	//입력한 새글 정보를 DB에 추가 하는 메소드 
	//추가에 성공하면 1을 반환 실패하면 0을 반환
	public int insertBoard(BoardVo vo) {
		
		int result = 0;
		
		String sql = null;
		
		try {
			//커넥션풀에서 커넥션 얻기
			//DB접속
			con = ds.getConnection();
			
			//두번째 부터 입력되는 주글들의 b_group열에 저장된 값을 1증가 시킨다.  //주글 추가  규칙2
			sql = "update board set b_group = b_group + 1";
			
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			
			//insert SQL문 만들기 //b_group , b_level  0  0 으로 insert 규칙3
			sql = "insert into board "
			    + "(b_idx, b_id, b_pw, b_name, "
			    + " b_email, b_title, b_content, "
			    + " b_group, b_level, b_date, b_cnt) "
			    + " values(border_b_idx.nextVal,"
			    + " ?, ?, ?, ?, ?, ?, 0, 0, sysdate, 0)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getB_id());
			pstmt.setString(2, vo.getB_pw());
			pstmt.setString(3, vo.getB_name());
			pstmt.setString(4, vo.getB_email());
			pstmt.setString(5, vo.getB_title());
			pstmt.setString(6, vo.getB_content());
			
			result = pstmt.executeUpdate(); //insert
			
		}catch(Exception e) {
			
			System.out.println("insertBoard메소드에서 SQL실행 오류");
			e.printStackTrace();
			
		}finally {
	
			closeResource();
		}

		return result;
	}

	
	//현재 게시판 DB에 저장된 글의 총 수 조회 하는 메소드
	public int getTotalRecord() {
		
		//조회된 글의 글수 저장
		int total = 0;
		
		String sql = "select count(*) as cnt from board";
				
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			total = rs.getInt("cnt");
			 
		}catch (Exception e) {
			System.out.println("getTotalRecord메소드에서 오류");
			e.printStackTrace();
		}finally {
			closeResource();
		}
	
		return total;
	}
	
	
	
	//현재 게시판 DB에 저장된 글의 총 수 조회 하는 메소드
	public int getTotalRecord(String key, String word) {
		
		//조회된 글의 글수 저장
		int total = 0;
		
		String sql = null;
		
		if(!word.equals("")) {//검색어를 입력했다면?
			
			if(key.equals("titleContent")) {//검색기준값  제목+내용을 선택했다면? 
			
				sql = "select count(*) as cnt from board "
					+ " where b_title like '%"+ word + "%' or"
						  + " b_content like '%"+ word+"%'";
				
				
			}else {//"name" 검색기준값 작성자를 선택했다면?
			
				sql = "select count(*) as cnt from board "
					+ " where b_name like '%"+ word + "%'";				
			}
			
		}else {//검색어 입력 안했다면?
			
			sql = "select count(*) as cnt from board";
		}
			
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			total = rs.getInt("cnt");
			 
		}catch (Exception e) {
			System.out.println("getTotalRecord메소드에서 오류");
			e.printStackTrace();
		}finally {
			closeResource();
		}
	
		return total;
	}
	
	
	//현재 게시판 DB에 있는 모든 글들을 조회 하는 메소드 
	public ArrayList boardListAll() {
		
		String sql = "select * from board order by b_group asc";
		
		ArrayList list = new ArrayList();
			
		try {
				con = ds.getConnection();
							
				pstmt = con.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				//조회된 Result의 정보를 한행 단위로 꺼내서
				//BoardVo객체에 한행씩 저장 후
				//BoardVo객체들을? ArrayList배열에 하나씩 추가해서 저장
				while(rs.next()) {
					
					BoardVo vo = new BoardVo(rs.getInt("b_idx"), 
							                 rs.getString("b_id"),
							                 rs.getString("b_pw"), 
							                 rs.getString("b_name"), 
							                 rs.getString("b_email"), 
							                 rs.getString("b_title"), 
							                 rs.getString("b_content"), 
							                 rs.getInt("b_group"), 
							                 rs.getInt("b_level"), 
							                 rs.getDate("b_date"), 
							                 rs.getInt("b_cnt"));
					list.add(vo); 
				}					
		}catch(Exception e) {
			
			System.out.println("boardListAll메소드 내부에서 SQL오류");
			e.printStackTrace();	
			
		}finally {
			closeResource();
		}
		return list;
	}
	
	
	
	
	
	
	//현재 게시판 DB에 있는 모든 글들을 조회 하는 메소드 
	//조건 : 선택한 검색기준값과  입력한 검색어 단어를 이용해 글들을 조회 !
	public ArrayList boardList(String key, String word) {
		
		String sql = null;
		
		ArrayList list = new ArrayList();
			
		
		if(!word.equals("")) {//검색어를 입력했다면?
			
			if(key.equals("titleContent")) {//검색기준값  제목+내용을 선택했다면? 
			
				sql = "select * from board "
					+ " where b_title like '%"+ word + "%' or"
						  + " b_content like '%"+ word+"%' order by b_group asc";
			
			}else {//"name" 검색기준값 작성자를 선택했다면?
			
				sql = "select * from board "
					+ " where b_name like '%"+ word + "%' order by b_group asc";				
			}
			
		}else {//검색어를 입력하지 않았다면?
			//모든 글 조회   
			//조건-> b_dix열의 글번호 데이터들을 기준으로 해서 내림 차순으로 정렬 후 조회 !
			sql = "select * from board order by b_group asc";
			
			//참고. 정렬 조회 -> order by 정렬기준열명  desc또는asc
			// desc 내림 차순 정렬 
			// asc 오름 차순 정렬
			
		}
			
		try {
				con = ds.getConnection();
							
				pstmt = con.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				//조회된 Result의 정보를 한행 단위로 꺼내서
				//BoardVo객체에 한행씩 저장 후
				//BoardVo객체들을? ArrayList배열에 하나씩 추가해서 저장
				while(rs.next()) {
					
					BoardVo vo = new BoardVo(rs.getInt("b_idx"), 
							                 rs.getString("b_id"),
							                 rs.getString("b_pw"), 
							                 rs.getString("b_name"), 
							                 rs.getString("b_email"), 
							                 rs.getString("b_title"), 
							                 rs.getString("b_content"), 
							                 rs.getInt("b_group"), 
							                 rs.getInt("b_level"), 
							                 rs.getDate("b_date"), 
							                 rs.getInt("b_cnt"));
					list.add(vo); 
				}					
		}catch(Exception e) {
			
			System.out.println("boardList메소드 내부에서 SQL오류");
			e.printStackTrace();	
			
		}finally {
			closeResource();
		}
		return list;
	}

	
	//글번호 를 매개변수로 받아 글하나 조회 하는 메소드 
	public BoardVo boardRead(String b_idx) {
		
		String sql = "select * from board where b_idx=?";
		
		BoardVo vo = null;
			
		try {
				con = ds.getConnection();
							
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,  Integer.parseInt(b_idx) );
				
				rs = pstmt.executeQuery();
				
				//조회된 Result의 정보를 한행 단위로 꺼내서
				//BoardVo객체에 한행의 정보를 저장합니다.
				if(rs.next()) {				
							vo = new BoardVo(rs.getInt("b_idx"), 
							                 rs.getString("b_id"),
							                 rs.getString("b_pw"), 
							                 rs.getString("b_name"), 
							                 rs.getString("b_email"), 
							                 rs.getString("b_title"), 
							                 rs.getString("b_content"), 
							                 rs.getInt("b_group"), 
							                 rs.getInt("b_level"), 
							                 rs.getDate("b_date"), 
							                 rs.getInt("b_cnt"));				
				}					
		}catch(Exception e) {
			
			System.out.println("boardRead메소드 내부에서 SQL오류");
			e.printStackTrace();	
			
		}finally {
			closeResource();
		}
		return vo;
	}

	
	//글 수정 삭제를 위해 입력받은 비밀번호를 매개변수로 받아  DB에 저장되어 있는지 비교하여 확인 
	public boolean passCheck(String b_idx_, String password) {
		
		boolean result = false;
		
		try {
			
			con = ds.getConnection();
			
			String sql = "select * from board  "
					   + "where b_pw=? and b_idx=? order by b_idx desc"; 
						
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, b_idx_);
			
			rs = pstmt.executeQuery();
			
			//조회가 되면 비밀번호가 있다는 뜻
			if(rs.next()) {
				result = true;
			}else {
				result = false;	
			}
										
			//true -> 비밀번호가  DB에 있음
			//false-> 비밀번호가 DB에 없음  
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeResource();//자원해제
		}
	
		return result;//true 또는 false를  BoardController로 반환 
	}

	//글을 수정 하는 메소드 
	public int updateBoard(String idx_, String email_, String title_, String content_) {
		
		int result = 0;
		
		try {
				con = ds.getConnection();//DB연결
				
				String sql = "update board set b_email='" + email_ + "',"
						                   + " b_title='" + title_ + "',"
						                   + " b_content='" + content_ + "'"
						                   + " where b_idx='"+idx_+"'";
							 
//				String sql = "update board set b_email=?, b_title=?, b_content=?"
//						   + " where b_idx=?";
				
//				update 수정할테이블명 set 수정열명=수정할값, 수정열명=수정할값
//				where 조건열명=조건열값;
				
				pstmt = con.prepareStatement(sql);
				
				result = pstmt.executeUpdate();

		}catch(Exception e) {
			System.out.println("updateBoard메소드 내부에서 UPDATE문 실행 오류");
			e.printStackTrace();
		}finally {
			closeResource();
		}
		
		return result;
	}

	//삭제할 글번호를 매개변수로 받아 DB에저장된 글을 DELETE하는 메소드 
	public String deleteBoard(String delete_idx) {
		
		String result = null; //"삭제성공" 또는 "삭제실패"  를 저장 
		
		try {
			//1. 커넥션풀(DataSource)에서 Connection객체 얻기
			con = ds.getConnection();
			
			//2. DELETE 문 만들기 
			//-> 매개변수로 전달 받는  id에 해당되는 회원 삭제 시키는 DELETE문 
			String query = "DELETE FROM board WHERE b_idx=?";
			//문법    DELETE FROM 삭제할테이블명 WHERE 조건열=조건값;
			
			//3. DELETE SQL문을 실행할  PreparedStatement객체 얻기
			pstmt = con.prepareStatement(query);
			//3.1. ? 기호 대신 값 설정
			pstmt.setInt(1, Integer.parseInt(delete_idx));
			
			//4. DELETE SQL문 DB로 전송하여 실행!
			//-> executeUpdate메소드는 삭제에 성공하면 1을 반환 실패하면 0을 반환 함 
			result = String.valueOf(pstmt.executeUpdate());
			
			if(result.equals("1")) {
				result = "삭제성공";
			}else{
				result = "삭제실패";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			//5. 자원해제 
			try {
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return result;//응답할값 서블릿으로 전달 
	
	}

	
	
	//답변글 DB에 추가 하는 메소드 
	public void replyInsertBoard(String super_b_idx,
								 String reply_id,
								 String reply_name, 
							     String reply_email,
							     String reply_title,
							     String reply_content,
							     String reply_pass) {	
		
		String sql = null;
		
		try {
			 con = ds.getConnection();
			 
			 //1. 부모글의 글번호를 이용해  b_group열의 값과 , b_level열의 값을 조회
			 sql = "SELECT b_group, b_level from board where b_idx=?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, Integer.parseInt(super_b_idx));
			 rs = pstmt.executeQuery();
			 rs.next();
			 String b_group = rs.getString("b_group");//부모글의 그룹번호 
			 String b_level = rs.getString("b_level");//부모글의 들여쓰기 정도 레벨값 
			
			 //답변 글 추가 규칙1.
			 //2. 부모글의 b_group(그룹번호)보다 큰 그룹번호를 가진 글이 있다면 
			 //   1증가 하여 UPDATE시켜야 합니다.
			 sql = "UPDATE board set b_group = b_group + 1 WHERE b_group > ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, Integer.parseInt(b_group));
			 pstmt.executeUpdate();
			
			 //답변 글 추가 
			 //규칙2. 부모글의 b_group열의 값에 1더한값을 insert 
			 //규칙3. 부모글의 b_level열의 값에 1더한값을 insert
			 //3. 답변글 내용 DB에 추가 	
			 
			//답변글 insert SQL문 만들기 
			sql = "insert into board "
			    + "(b_idx, b_id, b_pw, b_name, "
			    + " b_email, b_title, b_content, "
			    + " b_group, b_level, b_date, b_cnt) "
			    + " values(border_b_idx.nextVal,"
			    + " ?, ?, ?, ?, ?, ?, ?, ?, sysdate, 0)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reply_id);//답변글 작성자 ID 
			pstmt.setString(2, reply_pass); //작성하는 답변글 비밀번호 
			pstmt.setString(3, reply_name); //답변글 작성자 명
			pstmt.setString(4, reply_email);//답변글을 작성하는 사람의 이메일
			pstmt.setString(5, reply_title);//작성하는 답변글 제목
			pstmt.setString(6, reply_content);//작성하는 답변글 내용 
			pstmt.setInt(7, Integer.parseInt(b_group) + 1); //답변글의  b_group열의 값은
															//주글의 b_group에  1 더한값 
			pstmt.setInt(8, Integer.parseInt(b_level) + 1); //답변글의  b_level열의 값은
															//주글의 b_level에  1 더한값 

			pstmt.executeUpdate(); //insert		 
					 	 
		} catch (Exception e) {
			
			System.out.println("replyInsertBoard 메소드 내부에서 SQL 실행 오류 ");
			e.printStackTrace();
			
		} finally {
			closeResource();
		}

	}
			

}//BoardDAO클래스 끝













