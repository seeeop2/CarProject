package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import VO.CarConfirmVo;
import VO.CarListVo;
import VO.CarOrderVo;
import VO.MemberVo;

//DB와 연결하여 비즈니스로직 처리 하는 클래스 
public class MemberDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	//커넥션풀 생성 후 커넥션 객체 얻는 생성자
	public MemberDAO() {
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

	
	//아이디 중복 체크 !
	public boolean overlappedId(String id) {//입력한 아이디를 매개변수 id로 받는다
		
		boolean result = false;
		
		try {
			
			con = ds.getConnection();
			
			//오라클의 decode()함수를 이용하여 서블릿에서 전달되는
			//입력한 ID에 해당하는 데이터를 검색하여 true 또는 false를 반환하는데
			//검색한 갯수가 1(검색한 레코드가 존재하면)이면 'true'를 반환,
			//존재하지 않으면 'false'를 문자열로 반환하여 조회합니다.
			String sql = "select decode(count(*),1,'true','false') ";
				   sql	+= "as result from member";
				   sql  += " where id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			rs.next();//조회된 제목줄에 커서(화살표)가 있다가 조회된 줄로 내려가 위치함
			
			String value = rs.getString("result");//"true"
			
			//문자열 "true" 또는 "false"를   Boolean자료형으로 형변환 하여 저장
			result = Boolean.parseBoolean(value);//true 또는 false
						
			//true -> 아이디 중복
			//false-> 아이디가 DB에 없으므로 중복 아님 
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeResource();//자원해제
		}
	
		return result;//true 또는 false를  MemberController로 반환 
	}

	public void inserMember(MemberVo vo) {
	
		try {
			//커넥션 풀에 만들어져 있는 DB와 미리 연결을 맺은 Connection객체 빌려오기 
			//요약 DB연결
			con = ds.getConnection();
			
			//insert문장 완성하기
			String sql = "INSERT INTO MEMBER (ID, PASS, NAME, REG_DATE, AGE, GENDER, ADDRESS, EMAIL, TEL, HP) " 
									+ "values(?,   ?,    ?,    sysdate,  ?,   ?,       ?,       ?,    ?,   ?) ";
                         
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPass());
			pstmt.setString(3, vo.getName());
			pstmt.setInt(4, vo.getAge());
			pstmt.setString(5, vo.getGender());
			pstmt.setString(6, vo.getAddress());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getTel());
			pstmt.setString(9, vo.getHp());
			
			//PreparedStatement실행객체메모리에 설정된 insert전체 문장을 
			//DB의 테이블에 실행!
			pstmt.executeUpdate();
						
		} catch (Exception e) {
			System.out.println("insertMember메소드 내부에서  SQL실행 오류 " + e);
			e.printStackTrace();
		} finally {
			closeResource(); //자원해제
		}
		
	}

	
	//입력한 아이디와 비밀번호를 매개변수로 받아 DB의 테이블에 저장되어 있는지 확인 하는 메소드
	public int userCheck(String login_id, String login_pass) {
		
		int check = -1;  // 1  -> 아이디 , 비밀번호 맞음
					     // 0  -> 아이디 맞음 , 비밀번호 틀림 
						 // -1  -> 아이디 틀림, 비밀번호 맞음 
		
		
		try {
			//DB접속
			con = ds.getConnection();
			//매개변수 login_id로 받는 입력한 아아디에 해당되는 행을 조회 SELECT 문
			String sql = "select * from member where id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, login_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//입력한 아이디로 조회한 행이 있으면?(아이디가 있고?)
				//입력한 비밀번호와  조회된 비밀번호와 비교 해서 있으면?(비밀번호가 있으면?)
				if(login_pass.equals( rs.getString("pass")) ) {
					check = 1;
				}else {//아이디는 맞고, 비밀번호 틀림
					check = 0;
				}
			}else {//아이디가 틀림
				check = -1;
			}
		} catch (Exception e) {
			System.out.println("userCheck메소드 내부에서 SQL문 실행 오류 ");
			e.printStackTrace();
	
		} finally {
			closeResource();
		}
		return check;
	}

	//로그인한 사람의 아이디를 매개변수로 받아 member테이블에서 email열값,name열값, id열값 조회 
	public MemberVo memberOne(String memberid) {
		
		MemberVo membervo = null;
		
		try {
				con = ds.getConnection();
				
				pstmt = con.prepareStatement("select email, name, id from member where id=?");
				pstmt.setString(1, memberid);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					membervo = new MemberVo();
					membervo.setEmail(rs.getString("email"));
					membervo.setName(rs.getString("name"));
					membervo.setId(rs.getString("id"));
				}
		}catch(Exception e) {
			
			System.out.println("memberOne메소드 내부에서  SQL문 실행 오류");
			e.printStackTrace();
			
		}finally {
			closeResource();
		}
		
		return membervo;
	}

	
	//렌트 예약을 하기 위해
	//로그인한 사람의 회원 아이디, 비밀번호를 member테이블에서 조회 하여 제공 해주는 메소드 
	public MemberVo memberOneIdPass(String id) {
		MemberVo membervo = null;
		
		try {
				con = ds.getConnection();
				
				pstmt = con.prepareStatement("select id, pass from member where id=?");
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					membervo = new MemberVo();
					membervo.setId(rs.getString("id"));
					membervo.setPass(rs.getString("pass"));
				
				}
		}catch(Exception e) {
			
			System.out.println("memberOneIdPass메소드 내부에서  SQL문 실행 오류");
			e.printStackTrace();
			
		}finally {
			closeResource();
		}
		
		return membervo;
	}	

			

}//MemberDAO클래스 끝













