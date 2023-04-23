package CONTROLLER;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CarDAO;
import DAO.MemberDAO;
import VO.CarConfirmVo;
import VO.CarListVo;
import VO.CarOrderVo;
import VO.MemberVo;

//http://localhost:8090/CarProject/car/Main  입력하여 요청하면  CarMain.jsp화면이 나옵니다.

@WebServlet("/car/*")
public class CarController extends HttpServlet {

	//CarDAO객체를 저장할 참조변수 선언
	CarDAO cardao;
	//MemberDAO객체를 저장할 참조변수 선언  ----
	MemberDAO memberdao;
	
	@Override
	public void init() throws ServletException {
		cardao = new CarDAO();
		memberdao = new MemberDAO(); // ----
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
		
		//클라이언트의 웹브라우저로 응답할 데이터 종류 설정 및 문자처리방식 UTF-8설정 
		response.setContentType("text/html; charset=utf-8");
		
		//1.한글처리 
		request.setCharacterEncoding("UTF-8");
		//1.1 클라이언트가 요청한 2단계요청주소 request객체에서 얻기
		String action = request.getPathInfo();
		System.out.println("2단계 요청 주소 : " + action);
		// /Main <- 메인화면 요청
		// /bb   <- 예약하기 화면 요청
		// /CarList.do  <- 전체차량 조회 요청!
		// /carcategory.do <- 선택한 차량 유형별 조회 요청!
		// /CarInfo.do <- carno번호에 해당하는 차량 한대의 정보 조회 요청!
		// /CarOption.do <- 렌트 예약을 위해 옵션을 선택할 화면 요청!
		// /CarOptionResult.do <- 추가한 옵션 금액 + 기본 금액 계산 요청!
		// /CarOrder.do <- 예약 요청 !
		// /cc  <- 예약확인하기위해 예약당시 입력했던 휴대폰 번호, 비밀번호를 
		//         입력하여 예약확인을 요청하는 디자인 View를 요청!
		// /CarReserveConfirm.do <- 입력한 휴대폰 번호와 비밀번호로 예약한 렌트 정보 조회요청!
		// /update.do <- 예약한 정보 수정 하기위해 예약한 아이디로 예약정보 조회 요청!
		// /updatePro.do <- 입력한 정보 수정 요청!
		// /freeBoard.bo <- 게시판 리스트 화면 요청!
		
		//보여줄 View경로 또는 요청 주소 저장할 변수
		String nextPage = null;
		
		if(action.equals("/Main")) {//메인화면 요청!
			
			nextPage = "/CarMain.jsp";
		
		}else if(action.equals("/bb")) {//예약하기 화면 요청!
			
			//중앙 화면 요청한 주소 얻기
			//CarReservation.jsp
			String center = request.getParameter("center");
			
			//request에 CarReservation.jsp 저장(바인딩)
			request.setAttribute("center", center);
			
			//뷰주소 저장
			nextPage = "/CarMain.jsp";
				
		}else if(action.equals("/CarList.do")) {//전체 차량 조회 요청!
			
			//응답할 값 마련
			//CarDAO객체의 getAllCarList()메소드를 호출하여
			//DB에 저장된 모든 차량정보들을 각각 CarListVO객체에 저장 후 
			//각각의 CarListVO객체를 Vector배열에 담아 반환 받습니다.
			Vector vector = cardao.getAllCarList();
			
			//View화면에 조회된 백터의 정보를 보여주기 위해 
			//request내장객체 영역에 바인딩
			request.setAttribute("v", vector);
			request.setAttribute("center","CarList.jsp"); //중앙 View 주소 바인딩 
			
			//뷰주소 저장
			nextPage = "/CarMain.jsp";
			
		//선택한 소형 중형 대형 중 하나의 값으로 차량 정보 조회 요청!
		}else if(action.equals("/carcategory.do")) {
			
			//소형(Small), 중형(Mid), 대형(Big)중  선택한 <option>의 값 하나 얻기
			String cartegory = request.getParameter("carcategory");
			
			//선택한 차량 종류에 따른 차량 조회를 위해 
			//CarDAO객체의 메소드 호출!
			Vector vector = cardao.getCategoryList(cartegory);
			
			//View화면에 조회된 백터의 정보를 보여주기 위해 
			//request내장객체 영역에 바인딩
			request.setAttribute("v", vector);
			request.setAttribute("center","CarList.jsp"); //중앙 View 주소 바인딩 
			
			//뷰주소 저장
			nextPage = "/CarMain.jsp";
			
		}else if(action.equals("/CarInfo.do")) {//carno번호를 이용해 차 조회 요청
			
			//CarList.jsp화면에서 차 이미지를 클릭했을때..
			//차량 한대 정보를 조회 하기 위해 carno정보를 request에서 얻자
			int carno = Integer.parseInt(request.getParameter("carno"));        
			
			//carno차번호를 이용해  DB에 저장된 차량 한대의 정보를 조회 하기 위해
			//CarDAO객체의 getOneCar(int carno)메소드 호출시  차번호 전달함
			//조회한 차량 한줄 정보를 CarListVo객체의 각변수에 담아 반환 받자
			CarListVo vo = cardao.getOneCar(carno);
			
			//조회된 한대 차량 정보를 View페이지로 전달해 출력하기 위해 
			//request객체에 바인딩
			request.setAttribute("vo", vo);
			//View 중앙화면 주소 request객체에 바인딩
			request.setAttribute("center", "CarInfo.jsp");
			
			
			nextPage = "/CarMain.jsp"; 
			
		}else if(action.equals("/CarOption.do")) {//옵션 선택 화면 요청!
			
			//4가지 요청 값  request에서 얻기
			//대여수량, 예약할 차번호, 차이미지이름, 대여금액
//			String carqty = request.getParameter("carqty");
//			String carno = request.getParameter("carno");
//			String carimg = request.getParameter("carimg");
//			String carprice = request.getParameter("carprice");
			
			//View 중앙화면(옵션 선택할수 있는 화면) request객체에 바인딩 
			request.setAttribute("center", "CarOption.jsp");
			
			nextPage="/CarMain.jsp";		
			
		}else if(action.equals("/CarOptionResult.do")) {//예약 금액 계산 요청
			
		//CarOption.jsp 에서 요청한 값들 얻기
			//예약할 차번호
			int carno = Integer.parseInt(request.getParameter("carno"));
			//대여 시작 날짜
			String carbegindate = request.getParameter("carbegindate");		
			//대여수량
			int carqty = Integer.parseInt(request.getParameter("carqty"));
			//대여금액(한대 당 렌트 가격)
			int carprice = Integer.parseInt(request.getParameter("carprice"));
			//대여기간
			int carreserveday = Integer.parseInt(request.getParameter("carreserveday"));
			//보험 적용 여부값
			int carins = Integer.parseInt(request.getParameter("carins"));
			//무선 WIFI 적용 여부값
			int carwifi = Integer.parseInt(request.getParameter("carwifi"));
			//네비게이션 적용 여부값
			int carnave = Integer.parseInt(request.getParameter("carnave"));
			//베이비시트 적용 여부값
			int carbabyseat = Integer.parseInt(request.getParameter("carbabyseat"));
			
		//응답할 값 마련 (렌트할 금액 최종 계산,비즈니스로직처리)	
			//차량 기본금액 계산 = 대여수량 * 대여가격(차 한대당 렌트가격) * 대여기간
			int totalreserve = carqty * carprice * carreserveday;
			//추가 한 옵션 금액 계산 = 각종 옵션에 대여기간과 대여수량을 곱해서 계산
			int totaloption = 
				((carins * carreserveday) + (carwifi * carreserveday) + (carbabyseat * carreserveday))
				* 10000 * carqty;
			
		//응답할 값을 View(CarOrder.jsp)중앙화면에 보여주기  위해 일단!
		//CarOrderVo객체의 각변수에 저장
			CarOrderVo vo = new CarOrderVo();
					   vo.setCarno(carno);
					   vo.setCarqty(carqty);
					   vo.setCarreserveday(carreserveday);
					   vo.setCarins(carins);
					   vo.setCarwifi(carwifi);
					   vo.setCarnave(carnave);
					   vo.setCarbabyseat(carbabyseat);
					   vo.setCarbegindate(carbegindate);
					   
		//그리고 위 CarOrderVO 객체 , 차량 기본금액 계산, 추가 한 옵션 금액 계산
		//request객체에 바인딩 해서   View(CarOrder.jsp)로 전달 해야 합니다.
			request.setAttribute("vo", vo);
			request.setAttribute("totalreserve", totalreserve);
			request.setAttribute("totaloption", totaloption);

//----------------------------------
			//로그인했는지 안했는지 판단 해서  예약 결제 중앙화면을 다르게 보여주기 위해
			//session영역을 얻고  session영역에 로그인한 아이디가 저장되어 있는지 없는지 판단 하여
			//중앙화면 을 달리 해준다.
			HttpSession session = request.getSession();
			
			String id = (String)session.getAttribute("id");
			
			if(id == null) {
				
				request.setAttribute("center", "CarOrder.jsp"); //중앙화면
				
			}else {
				
				//결제 하는 화면에 로그인한 회원의 아이디와 비밀번호를  보여주기 위해
				//member테이블에서 SELECT하여 가져와 합니다. 
				MemberVo membervo = memberdao.memberOneIdPass(id);
				request.setAttribute("membervo", membervo);//바인딩 
				request.setAttribute("center", "LoginCarOrder.jsp");//중앙화면

			}
//----------------------------------			
			
			nextPage = "/CarMain.jsp";
		
		}else if(action.equals("/CarOrder.do")) {//예약 요청!
			
			//예약할 정보를 DB에 추가 해야 합니다.
			//그러므로 request에 저장된 예약할 정보를 모두 꺼내어서
			
			//예약할 차번호
			int carno = Integer.parseInt(request.getParameter("carno"));
			//대여 시작 날짜
			String carbegindate = request.getParameter("carbegindate");		
			//대여수량
			int carqty = Integer.parseInt(request.getParameter("carqty"));
			//대여기간
			int carreserveday = Integer.parseInt(request.getParameter("carreserveday"));
			//보험 적용 여부값
			int carins = Integer.parseInt(request.getParameter("carins"));
			//무선 WIFI 적용 여부값
			int carwifi = Integer.parseInt(request.getParameter("carwifi"));
			//네비게이션 적용 여부값
			int carnave = Integer.parseInt(request.getParameter("carnave"));
			//베이비시트 적용 여부값
			int carbabyseat = Integer.parseInt(request.getParameter("carbabyseat"));
			
			//CarOrderVo객체의 각변수에 저장 후 
			CarOrderVo vo = new CarOrderVo();
					   vo.setCarno(carno);
					   vo.setCarqty(carqty);
					   vo.setCarreserveday(carreserveday);
					   vo.setCarbegindate(carbegindate);
					   vo.setCarins(carins);
					   vo.setCarwifi(carwifi);
					   vo.setCarnave(carnave);
					   vo.setCarbabyseat(carbabyseat);
							
			//로그인 하고 예약 하느냐 비회원으로 예약 하느냐!!!!!!
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");
			
			if(id == null) {//비회원으로 예약할떄~~
				//비회원 예약을 위해 입력했던 핸드폰 번호
				String memberphone = request.getParameter("memberphone");
				//비회원 예약을 위해 입력했던 비밀번호 
				String memberpass = request.getParameter("memberpass");
				System.out.println("-----------미로그인" + memberpass);
				
				vo.setMemberphone(memberphone);
			    vo.setMemberpass(memberpass);
			   
			    
			}else {//로그인하여 회원으로 예약할때~
				
				String memberId = request.getParameter("memberId");
				String memberpass = request.getParameter("memberpass");
				System.out.println("-----------로그인" + memberpass);
				
				vo.setId(memberId);
				vo.setMemberpass(memberpass);

			}
			

			//CarDAO객체의 insertCarOrder메소드 호출시~ 매개변수로 전달하여
			//DB의 CarOrder테이블에 insert  추가 시킵니다.
			cardao.insertCarOrder(vo,session);
			
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('예약되었습니다.');" 
			                    + " location.href='" + request.getContextPath()
			                    + "/car/CarList.do';" 
                  + "</script>");

			return;
//			
		
		//예약확인을 위해 휴대폰,비밀번호 입력할수 있는 화면 요청!	
		}else if(action.equals("/cc")) {
			
			//중앙 화면 요청한 주소 얻기
			//CarReserveConfirm.jsp
			String center = request.getParameter("center");
			
			//request에 CarReserveConfirm.jsp 저장(바인딩)
			request.setAttribute("center", center);
			
			//뷰주소 저장
			nextPage = "/CarMain.jsp";					
			
		}else if(action.equals("/CarReserveConfirm.do")) {//예약한 정보 조회 요청!
			
			//요청한 값얻기
			//입력한 휴대폰번호, 비밀번호
			String memberphone = request.getParameter("memberphone");
			String memberpass = request.getParameter("memberpass");
			
			//예약한 정보를 조회 하기 위해 CarDAO객체의 getAllCarOrder메소드 호출시!!!!!!!!!
			//매개변수로 입력한 휴대번호와 비밀번호를 전달 하여 SELECT문장을 만든 뒤
			//조회한 정보들을  각각 CarConfirmVo객체에 담아 Vector배열에 최종 저장후 반환 받자
			Vector<CarConfirmVo> vector = cardao.getAllCarOrder(memberphone,memberpass);
			
			//예약한 정보를 VIEW화면에 보여 주기 위해 
			//먼저 request객체에 Vector를 바인딩 합니다.
			request.setAttribute("v", vector);
		//추가	
			request.setAttribute("memberphone", memberphone);
			request.setAttribute("memberpass", memberpass);

			//중앙화면 VIEW(예약한 정보를 보여줄 VIEW) 주소 또한 request에 바인딩 합니다.
			request.setAttribute("center", "CarReserveResult.jsp");
			
			
			nextPage = "/CarMain.jsp";
		
		}else if(action.equals("/update.do")) { //예약 정보 수정을 위해 예약한 정보 조회 요청!
			
			//요청한 값 얻기
			int orderid = Integer.parseInt(request.getParameter("orderid"));
			String carimg = request.getParameter("carimg");
			
			//추가 시작
			String memberphone = request.getParameter("memberphone");
			//추가 끝
			
			//예약 아이디를 이용해 예약한 정보를 DB에서 조회하기 위해
			//CarDAO객체의 getOneOrder메소드 호출할때 매개변수로 orderid를 전달 하여 조회 해 오자
			CarConfirmVo vo = cardao.getOneOrder(orderid);
						 vo.setCarimg(carimg);
			
			//View중앙화면에 보여주기 위해 request에  vo를 바인딩
			request.setAttribute("vo", vo);
			
			//추가 시작
			request.setAttribute("memberphone", memberphone);
			//추가 끝

			
			//View중앙화면의 주소를 request에 바인딩
			request.setAttribute("center", "CarConfirmUpdate.jsp");
			
			nextPage="/CarMain.jsp";
		
		}else if(action.equals("/updatePro.do")) {//예약 정보 수정 요청!
			
			//수정을 위해 입력한 정보들을 request내장객체 메모리 영역에 저장되어 있으므로
			//DB에 UPDATE시키기 위해  CarDAO객체의 carOrderUpdate메소드 호출할때....
			//request객체 메모리를 전달해 UPDATE시키자
			int result = cardao.carOrderUpdate(request);
		
		//추가 시작
			String memberphone = request.getParameter("memberphone");
			int orderid = Integer.parseInt(request.getParameter("orderid"));
			String carimg = request.getParameter("carimg");		
		//추가 끝
			
			PrintWriter pw = response.getWriter();
			
			if(result == 1) {//수정 성공
				
				
				pw.print("<script>" + "  alert('예약정보가 수정 되었습니다.');" 
				                    + " location.href='" + request.getContextPath()
									+"/car/update.do?orderid="+orderid+"&carimg="+carimg+"&memberphone="+memberphone+"'"
	                  + "</script>");

				return;
				
			}else {
				
				pw.print("<script>"
						+ " alert('예약정보 수정 실패!');"
						+ " history.back();"
						+ "</script>");
				return;
			}
			
		}else if(action.equals("/delete.do")) {//예약 삭제를 위해 비밀번호를 입력하는 화면 요청!
			
			
			//orderid와  memberphone은 request.getParameter메소드를 호출하지 않고 
			//맨아래의 디스패처 방식으로 포워딩시 바로 공유해서 전달하여 사용하게 된다.
			
			String center = request.getParameter("center");
			
			request.setAttribute("center", center); //Delete.jsp
			
			nextPage = "/CarMain.jsp";
			
		
		}else if(action.equals("/DeletePro.do")) {//예약 정보 삭제 요청!
			
			//요청한 값 얻기
			//삭제할 예약아이디, 삭제를 위해 입력한 비밀번호 
			int orderid = Integer.parseInt( request.getParameter("orderid")  );
			String memberphone = request.getParameter("memberphone");
			String memberpass = request.getParameter("memberpass");
			
			
			//응답할 값 마련 
			//예약정보를 삭제(취소)하기 위해 CarDAO객체의 OrderDelete메소드 호출할떄...
			//매개변수로 삭제할 예약아이디와 입력한 비밀번호 전달하여 DB에서 DELETE시키자
			//삭제에 성공하면 OrderDelete메소드의 반환값은 삭제에 성공한 레코드 개수 1을 반환 받고
			//실패하면 0을 반환 받습니다.
			int result = cardao.OrderDelete(orderid,memberpass);
			
			System.out.println(result);
			
			
			PrintWriter pw = response.getWriter();
			
			if(result == 1) {//삭제 성공
				
				
				pw.print("<script>" 
							+ "  alert('예약정보가 삭제 되었습니다.');" 
							+ " location.href='" + request.getContextPath()
		                    + "/car/CarReserveConfirm.do?memberphone="+memberphone+"&memberpass="+memberpass+"';" 
	                  + "</script>");

				return;
				
			}else {
				
				pw.print("<script>"
						+ " alert('예약정보 삭제 실패!');"
						+ " history.back();"
						+ "</script>");
				return;
			}	
			
		//게시판 리스트 중앙화면 View를 요청 하는 주소를 받았을떄...	
		}else if(action.equals("/freeBoard.bo")) {
			
			//   board/list.jsp
			String center = request.getParameter("center");
			
			request.setAttribute("center", center);
			
			//메인화면 재요청 을 위해 VIEW주소 저장
			nextPage = "/CarMain.jsp";
					
		//네이버 검색 요청이 들어 왔을때..	
		}else if(action.equals("/NaverSearchAPI.do")) {
					
			//1.인증 정보 설정
			 String clientId = "FZbGHciOKD19JhjmFPxu"; //애플리케이션 클라이언트 아이디
		     String clientSecret = "Eaok0CAkZq"; //애플리케이션 클라이언트 시크릿
			
		    //2.검색 조건 설정
		     	int startNum = 0; //검색 시작 위치 
		     
		        String text = null;//검색어 
		        
		        try {
		        	//검색 시작 위치 받아오기 
		        	startNum = Integer.parseInt(request.getParameter("startNum"));
		        	//검색어 받아오기
		        	String searchText = request.getParameter("keyword");
		        	
		        	//검색어는 한글 깨찜을 방지 하기위해 문자 처리 방식을 UTF-8로 인코딩 합니다.
		            text = URLEncoder.encode(searchText, "UTF-8");
		            
		        } catch (UnsupportedEncodingException e) {
		        	
		            throw new RuntimeException("검색어 인코딩 실패",e);
		        }
		
		        
		        
		        
		      //3. API 주소 조합 
		        //검색결과 데이터를 JSON으로 받기 위한 API입니다
		        //검색어를 쿼스트링으로 보내는데 여기에는 display와 start매개변수도 추가했습니다.
		        //display속성은 한번에 가져올 검색 결과의 개수이며,
		        //start속성은 검색 시작 위치 입니다. 
		        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text
		        		      + "&display=10&start=" + startNum;    // JSON 결과
		        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과
		
		       
		      //4. API 호출 
		       
		        Map<String, String> requestHeaders = new HashMap<>();
		      //클라이언트의 아이디와 시크릿을 요청 헤더로 전달해 
		        requestHeaders.put("X-Naver-Client-Id", clientId);
		        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		        
		       //API를 호출합니다.  
		        String responseBody = get(apiURL,requestHeaders);
		
		        //검색결과를 콘솔에 출력하고
		        System.out.println(responseBody); 

		        
		        //검색 결과 바인딩
		        request.setAttribute("searchData", responseBody);
		        //중앙 VIEW
				request.setAttribute("center", "CarSearchResult.jsp");
				//메인화면 재요청 을 위해 VIEW주소 저장
				nextPage = "/CarMain.jsp";
		}

	
		//포워딩 (디스패처 방식)
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	
	}//doHandle()메소드 끝 
	
	//------------네이버 검색 OPEN API에서 사용 되는 메소드들 ---------------
	private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream()); 
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
	//------------네이버 검색 OPEN API에서 사용 되는 메소드들 ---------------
	
	

}//클래스 끝












