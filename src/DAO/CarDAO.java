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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


import VO.CarConfirmVo;
import VO.CarListVo;
import VO.CarOrderVo;

//DB와 연결하여 비즈니스로직 처리 하는 클래스 
public class CarDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	//커넥션풀 생성 후 커넥션 객체 얻는 생성자
	public CarDAO() {
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
	
	
	//선택한  option 차량 종류에 따른 차량 조회 
	public Vector getCategoryList(String option) {
		
		Vector vector = new Vector();
		
		//조회된 한행의 차량정보를 저장할 CarListVo객체의 참조변수
		CarListVo vo = null;
		
		try {
			//DB접속 : 커넥션풀에 만들어져 있는 커넥션 얻기
			con = ds.getConnection();
			
			//DB의 carlist테이블 저장된 선택한  Option의 차량을 조회 하는 SELECT문장을 sql변수에 저장
			String sql = "SELECT * FROM CARLIST WHERE carcategory='"+ option+"'";
			
			//SELECT문장을 DB의 carlist테이블에 전송해서 조회할 PreparedStatement객체 얻기
			pstmt = con.prepareStatement(sql);
			
			//SELECT문장을 실행하여 조회된 데이터들을 ReusltSet에 담아 반환 받기
			rs = pstmt.executeQuery();
			
			//반복문을 활용하여 ResultSet객체에 조회된 한줄 정보씩 얻어와
			//CarListVo객체의 각변수에 저장 후 
			//CarListVO객체를 Vector배열에 추가 하여 담습니다.
			while (rs.next()) {
				//ResultSet객체 내부에 조회된 한줄 정보를 얻어 하나의 CarListVo객체의 각변수에 저장
				vo = new CarListVo(rs.getInt("carno"), 
							       rs.getString("carname"),
							       rs.getString("carcompany"),
							       rs.getInt("carprice"),
							       rs.getInt("carusepeople"),
							       rs.getString("carinfo"),
							       rs.getString("carimg"),
							       rs.getString("carcategory"));
				//Vector배열에 CarListVO객체 하나 씩 추가
				vector.add(vo);
			}		
		}catch(Exception e) {
			System.out.println("getAllCarList메소드 에서  SQL오류 : " + e);
		}finally {
			closeResource(); //자원 해제
		}
		return vector;//Vector배열을 CarController서블릿으로 반환!
		
		
	}
	
	
	//모든 차량 조회 
	public Vector getAllCarList() {
		
		Vector vector = new Vector();
		
		//조회된 한행의 차량정보를 저장할 CarListVo객체의 참조변수
		CarListVo vo = null;
		
		try {
			//DB접속 : 커넥션풀에 만들어져 있는 커넥션 얻기
			con = ds.getConnection();
			
			//DB의 carlist테이블 저장된 모든 차량을 조회 하는 SELECT문장을 sql변수에 저장
			String sql = "SELECT * FROM CARLIST";
			
			//SELECT문장을 DB의 carlist테이블에 전송해서 조회할 PreparedStatement객체 얻기
			pstmt = con.prepareStatement(sql);
			
			//SELECT문장을 실행하여 조회된 데이터들을 ReusltSet에 담아 반환 받기
			rs = pstmt.executeQuery();
			
			//반복문을 활용하여 ResultSet객체에 조회된 한줄 정보씩 얻어와
			//CarListVo객체의 각변수에 저장 후 
			//CarListVO객체를 Vector배열에 추가 하여 담습니다.
			while (rs.next()) {
				//ResultSet객체 내부에 조회된 한줄 정보를 얻어 하나의 CarListVo객체의 각변수에 저장
				vo = new CarListVo(rs.getInt("carno"), 
							       rs.getString("carname"),
							       rs.getString("carcompany"),
							       rs.getInt("carprice"),
							       rs.getInt("carusepeople"),
							       rs.getString("carinfo"),
							       rs.getString("carimg"),
							       rs.getString("carcategory"));
				//Vector배열에 CarListVO객체 하나 씩 추가
				vector.add(vo);
			}		
		}catch(Exception e) {
			System.out.println("getAllCarList메소드 에서  SQL오류 : " + e);
		}finally {
			closeResource(); //자원 해제
		}
		return vector;//Vector배열을 CarController서블릿으로 반환!
	}
	
	
	
	//클릭한 차량이미지의 carno차번호를 매개변수로 전달 받아  차량 조회 
	public CarListVo getOneCar(int carno) {
				
		//조회된 한행의 차량정보를 저장할 CarListVo객체의 참조변수
		CarListVo vo = null;
		
		try {
			//DB접속 : 커넥션풀에 만들어져 있는 커넥션 얻기
			con = ds.getConnection();
			
			//DB의 carlist테이블 저장된 차량 한대의 정보를 조회 하는데...
			//매개변수로 전달받은 carno차번호에 해당하는 차량 조회 하는 SELECT문장
			String sql = "SELECT * FROM CARLIST WHERE carno='"+ carno+"'";
			
			//SELECT문장을 DB의 carlist테이블에 전송해서 조회할 PreparedStatement객체 얻기
			pstmt = con.prepareStatement(sql);
			
			//SELECT문장을 실행하여 조회된 데이터들을 ReusltSet에 담아 반환 받기
			rs = pstmt.executeQuery();
			
			//조건문을 활용하여 ResultSet객체에 조회된 한줄 정보를 얻어와
			//CarListVo객체의 각변수에 저장 후 
			if (rs.next()) {
				//ResultSet객체 내부에 조회된 한줄 정보를 얻어 하나의 CarListVo객체의 각변수에 저장
				vo = new CarListVo(rs.getInt("carno"), 
							       rs.getString("carname"),
							       rs.getString("carcompany"),
							       rs.getInt("carprice"),
							       rs.getInt("carusepeople"),
							       rs.getString("carinfo"),
							       rs.getString("carimg"),
							       rs.getString("carcategory"));
			}		
		}catch(Exception e) {
			System.out.println("getOneCar메소드 에서  SQL오류 : " + e);
		}finally {
			closeResource(); //자원 해제
		}
		return vo;//조회된 차량 한대정보가 저장된 CarListVO객체를 CarController서블릿으로 반환!
			
	}

	
	//로그인한 회원의 HP를 조회 해서 제공하는 메소드 
	public String memberHp(HttpSession session) {		
		
		String hp = null;		
		
		//로그인 한 회원 id로  HP를 조회 하기 위한 select문의 where 조건값 
		String id = (String)session.getAttribute("id");		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select hp from member where id=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			hp = rs.getString("hp");
			
		} catch (Exception e) {
			System.out.println("memberHp메소드 내부에서 SELECT문 실행 오류 ");
			e.printStackTrace();
			
		} finally {
			closeResource();
		}
		
		return hp;
	}
	
	
	//매개변수로 예약할 정보가 저장된 CarOrderVO객체를 전달 받아 
	//DB의 CarOrder예약정보를 저장하는 테이블에  INSERT 하는 메소드 
	public void insertCarOrder(CarOrderVo vo, HttpSession session) {
		
		//로그인한 회원 아이디로 HP를 조회 해옴 
		String hp = memberHp(session);
		System.out.println(hp);
		
		String sql = null;
		
		String id = (String)session.getAttribute("id");
		
		System.out.println(id);
	
		System.out.println(vo.getMemberpass());
	
		try {
			//커넥션 풀에 만들어져 있는 DB와 미리 연결을 맺은 Connection객체 빌려오기 
			//요약 DB연결
			con = ds.getConnection();
			
				
			if(id == null) {//비회원 예약
				
				    sql = "insert into non_carorder("
				    	+ "non_orderid,"
	                    + "carno,"
	                    + "carqty,"
	                    + "carreserveday,"
	                    + "carbegindate,"
	                    + "carins,"
	                    + "carwifi,"
	                    + "carnave,"
	                    + "carbabyseat,"
	                    + "memberphone,"
	                    + "memberpass)"
	                    + "values("
	                    + "non_carorder_non_orderid.nextval,"
	                    + "?,?,?,?,?,?,?,?,?,?)";
						
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, vo.getCarno());
				pstmt.setInt(2, vo.getCarqty());
				pstmt.setInt(3, vo.getCarreserveday());
				pstmt.setString(4, vo.getCarbegindate());
				pstmt.setInt(5, vo.getCarins());
				pstmt.setInt(6, vo.getCarwifi());
				pstmt.setInt(7, vo.getCarnave());
				pstmt.setInt(8, vo.getCarbabyseat());
				pstmt.setString(9, vo.getMemberphone());
				pstmt.setString(10, vo.getMemberpass());
				
				//PreparedStatement실행객체메모리에 설정된 insert전체 문장을 
				//DB의 테이블에 실행!
				pstmt.executeUpdate();
				
				
			}else {//회원 예약 
			
			    sql = "insert into carorder("
			    		+ "orderid,"
					    + "id,"
	                    + "carno,"
	                    + "carqty,"
	                    + "carreserveday,"
	                    + "carbegindate,"
	                    + "carins,"
	                    + "carwifi,"
	                    + "carnave,"
	                    + "carbabyseat,"
	                    + "memberphone,"
	                    + "memberpass)"
	                    + "values("
	                    + "carorder_orderid.nextval,"
	                    + "?,?,?,?,?,?,?,?,?,?,?)";
						
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, vo.getCarno());
				pstmt.setInt(3, vo.getCarqty());
				pstmt.setInt(4, vo.getCarreserveday());
				pstmt.setString(5, vo.getCarbegindate());
				pstmt.setInt(6, vo.getCarins());
				pstmt.setInt(7, vo.getCarwifi());
				pstmt.setInt(8, vo.getCarnave());
				pstmt.setInt(9, vo.getCarbabyseat());
				pstmt.setString(10, hp);
				pstmt.setString(11, vo.getMemberpass());
				
				//PreparedStatement실행객체메모리에 설정된 insert전체 문장을 
				//DB의 테이블에 실행!
				pstmt.executeUpdate();
			}
			
			
						
		} catch (Exception e) {
			System.out.println("insertCarOrder메소드 내부에서  SQL실행 오류 " + e);
			e.printStackTrace();
		} finally {
			closeResource(); //자원해제
		}
	}//insertCarOrder메소드 끝

	
	//입력한 휴대폰번호와 비밀번호를 매개변수로 전달받아 그에 해당하는 예약한 정보들을 조회하는 메소드 
	public Vector<CarConfirmVo> getAllCarOrder(String memberphone, String memberpass) {
	
		//조회된 CarOrderVo객체들을 저장할 가변길이 배열 
		Vector<CarConfirmVo> v = new Vector<CarConfirmVo>();
		
		//조회된 한 행의 정보(하나의 렌트 정보)를 저장할 변수 선언
		CarConfirmVo vo = null;
		
		try {
			//커넥션풀에서 커넥션 빌려오기
			con = ds.getConnection();
			
			//SELECT문
			//설명: 예약한 날짜가 현재 날짜 보다 크고? 예약당시 입력한 휴대폰번호와 비밀번호로 예약한 
			//     렌트 정보들을 조회 하는데... carorder테이블과  carlist테이블을 연결(NATURAL JOIN)해서
			//     정보들을 조회 합니다.
			String sql = "SELECT * FROM carorder natural join carlist "
					   + "WHERE sysdate < TO_DATE(carbegindate, 'YYYY-MM-DD') AND "
					   + "memberphone=? AND memberpass=?";
					   
			// https://gent.tistory.com/448        TO_DATE함수 참고.
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberphone);
			pstmt.setString(2, memberpass);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				vo = new CarConfirmVo();
				
				vo.setOrderid(rs.getInt("ORDERID")); //예약 ID
				vo.setCarqty(rs.getInt("CARQTY")); //대여 수량
				vo.setCarreserveday(rs.getInt(4));// 대여 기간 
				vo.setCarbegindate(rs.getString(5));//대여 시작 날짜
				vo.setCarins(rs.getInt(6));		//보험 옵션 적용 여부 
				vo.setCarwifi(rs.getInt(7));	//무선WIFI 옵션 적용 여부 
				vo.setCarnave(rs.getInt(8));	//네비 옵션 적용 여부 
				vo.setCarbabyseat(rs.getInt(9));// 베이비 시트 옵션 적용 여부 
				vo.setCarname(rs.getString(12)); //예약한 차량명
				vo.setCarprice(rs.getInt(14));//예약한 차량 하루 예약 가격
				vo.setCarimg(rs.getString(17)); //예약한 차량 이미지 명 
				
						
				//백터 배열에 VO추가
				v.add(vo);
			}
				
		} catch (Exception e) {
			
			System.out.println("getAllCarOrder메소드 내부에서 SQL실행 오류  : " + e);
			e.printStackTrace();
			
		} finally {
			closeResource();//자원해제
		}
		
		return v;  //CarController로 백터 반환
	}

	
	//예약정보 하나를 조회 하는 메소드 
	public CarConfirmVo getOneOrder(int orderid) {
		
		//조회된 한행의 차량정보를 저장할 CarConfirmVo객체의 참조변수
		CarConfirmVo vo = null;
		
		try {
			//DB접속 : 커넥션풀에 만들어져 있는 커넥션 얻기
			con = ds.getConnection();
			
			//DB의 carorder테이블 예약된 차량 한대의 정보를 조회 하는데...
			//매개변수로 전달받은 orderid에 해당하는 차량 조회 하는 SELECT문장
			String sql = "SELECT * FROM CARORDER WHERE orderid='"+ orderid+"'";
			
			//SELECT문장을 DB의 carorder테이블에 전송해서 조회할 PreparedStatement객체 얻기
			pstmt = con.prepareStatement(sql);
			
			//SELECT문장을 실행하여 조회된 데이터들을 ReusltSet에 담아 반환 받기
			rs = pstmt.executeQuery();
			
			//조건문을 활용하여 ResultSet객체에 조회된 한줄 정보를 얻어와
			//CarConfirmVo객체의 각변수에 저장 후 
			if (rs.next()) {
				//ResultSet객체 내부에 조회된 한줄 정보를 얻어 하나의 CarConfirmVo객체의 각변수에 저장
				vo = new CarConfirmVo();
				vo.setOrderid(orderid);
				vo.setCarbegindate(rs.getString("carbegindate")); //대여 시작 날짜
				vo.setCarreserveday(rs.getInt("carreserveday")); //대여 기간
				vo.setCarins(rs.getInt("carins"));//보험 적용 여부
				vo.setCarwifi(rs.getInt("carwifi"));//무선 WIFI적용 여부
				vo.setCarnave(rs.getInt("carnave"));//네비 적용 여부 
				vo.setCarbabyseat(rs.getInt("carbabyseat"));//베이비시트 적용 여부 
				vo.setCarqty(rs.getInt("carqty"));//대여 수량
				
			}		
		}catch(Exception e) {
			System.out.println("getOneOrder메소드 에서  SQL오류 : " + e);
		}finally {
			closeResource(); //자원 해제
		}
		return vo;//조회된 차량 한대정보가 저장된 CarConfirmVo객체를 CarController서블릿으로 반환!
			
	}

	
	//입력한 예약정보를 수정 ! 하는 메소드 
	public int carOrderUpdate(HttpServletRequest request) {
		
		int result = 0; //수정성공시 1이 저장되고 , 수정 실패하면 0이 저장될 변수 
		
		try {
			//커넥션 풀에서 DB와 미리 연결을 맺어 만들어져 있는 Connection객체 빌려오기 
			//요약 : DB접속
			con = ds.getConnection();
			
			//UPDATE구문
			//-> 예약한 아이디 와 예약당시 입력했던 비밀번호와 일치하는 하나의 예약정보를 수정시키자
			String sql = "UPDATE carorder set"
					   + " carbegindate=?,"
					   + " carreserveday=?,"
					   + " carins=?,"
					   + " carwifi=?,"
					   + " carbabyseat=?,"
					   + " carnave=?,"
					   + " carqty=?"
					   + " WHERE orderid=? AND memberpass=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("carbegindate"));
			pstmt.setInt(2, Integer.parseInt(request.getParameter("carreserveday")));
			pstmt.setInt(3, Integer.parseInt(request.getParameter("carins")));
			pstmt.setInt(4, Integer.parseInt(request.getParameter("carwifi")));
			pstmt.setInt(5, Integer.parseInt(request.getParameter("carbabyseat")));
			pstmt.setInt(6, Integer.parseInt(request.getParameter("carnave")));
			pstmt.setInt(7, Integer.parseInt(request.getParameter("carqty")));
			pstmt.setInt(8, Integer.parseInt(request.getParameter("orderid")));
			pstmt.setString(9, request.getParameter("memberpass"));
			
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			
			System.out.println("carOrderUpdate메소드 내부에서 SQL실행 오류 ");
			e.printStackTrace();
			
		} finally {
			//자원해제
			closeResource();
		}
		
		return result;
	}

	//예약정보 삭제(취소)를 위해 예약아이디와 입력한 비밀번호를 매개변수로 받아서
	//그에 해당되는 예약정보를 DB에서 삭제 시키는 메소드
	//삭제에 성공하면 1을 반환, 실패하면 0을 반환
	public int OrderDelete(int orderid, String memberpass) {
		
		int result = 0;
		
		try {
			con = ds.getConnection();
			
			String sql = "delete from carorder where orderid=? and memberpass=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderid);
			pstmt.setString(2, memberpass);
			
			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			System.out.println("OrderDelete메소드 내부에서 SQL실행 오류 ");
			e.printStackTrace();
		} finally {
			//자원해제
			closeResource();
		}	
		
		return   result;
	}
		

}//CarDAO클래스 끝





//DELETE문은?  테이블에 저장된 데이터들을 삭제하는 구문이다
//테이블 자체를 삭제하려면? DROP문을 사용 해야 한다.

//문법1 -> DELETE FROM 삭제할테이블명  WHERE 조건열명=조건값;
//설명1 -> 조건열에 해당하는 조건값이 참인  행을 삭제 합니다.

//문법2 -> DELETE FROM 삭제할테이블명;
//설명2 -> 테이블에 저장된 모든 행을 삭제 합니다.

//문법3 -> DROP TABLE 삭제할테이블명;    
//설명3 -> 테이블 삭제 시킴!!   테이블에 저장된 모든 데이터 다~~ 삭제됨.




