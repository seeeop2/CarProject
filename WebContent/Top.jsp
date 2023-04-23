<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
%>
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

 <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">	
	
	<!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: jQuery and Bootstrap Bundle (includes Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->
    <!--
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
    -->
	
	
	<style>
		
		#login{
			float:right;
			margin: 5px 64px 20px 0;
			font-family: Arial,Helvetica,sans-serif;
			font-size:20px;
		}
		
		/* a태그의 하이퍼링크 없애기, 글자색 설정*/
		#login a{
			text-decoration: none;
			color:#333;
		}
		
		/* a태그에 마우스를 올리는 이벤트가 발생하면  글자색 설정*/
		#login a:hover{
			color:#F90;
		}
		
		
		/* 메인 로고 이미지 div영역 스타일 주기   */
		#logo{
			float:left; 
			width:265px;
/* 			margin:60px 0 0 40px; */
		}
		
		
	</style>



<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>

	<script type="text/javascript">
	
		$(function(){
			
			
			
			//검색 요청 버튼(id="searchBtn")을 눌렀을때...
			//$.ajax메소드를 호출하도록 정의 
			$("#searchBtn").click(function(){
				
				alert($("#keyword").val());
				
				$.ajax({
						//요청 주소는 서블릿을 요청할 주소 사용 
						url:"<%=contextPath%>/NaverSearchAPI.do", 
						
						type:"get",
						
						//요청 할 값
						//1.입력한 검색어
						//2.선택한 검색 시작 위치 
						data: { 
								keyword : $("#keyword").val(),//검색어 
								startNum : 1 //검색 시작위치
						      },
						//응답 받을 데이터 형식 
						dataType : "json",
						
						success : suncFuncJson, //요청 성공시 호출할 메소드 설정 
						
						error : errFunc  //요청 실패 시 호출할 메소드 설정				
				});			
			});	
		});
		//검색 성공시  결과를 화면에 뿌려줍니다.
		//요청 성공 했을때... 호출되는 콜백 메소드 만들기
		function suncFuncJson(d){
			
			console.log(d); //크롬실행후 F12눌러 콘솔 탭에서 확인 
			
			var str = "";
			
			$.each(  d.items  ,  function(index, item){  
				
				str += "<ul>";
				str += 		"<li>" + (index+1) + "</li>";//검색 번호 
				str +=		"<li>" + item.title + "</li>";//검색 결과 문서의 제목
				str +=      "<li>" + item.description + "</li>"; //검색결과 문서의 내용을 요약한 정보
				str +=      "<li>" + item.bloggername + "</li>";//블로거 이름 
				str +=      "<li>" + item.bloggerlink + "</li>";//블로그 포스트를 작성한 블로거의 링크 
				str +=      "<li>" + item.postdate + "</li>";//블로그 포스트를 작성한 날짜 
				str +=      "<li><a href='"+ item.link +"'>바로가기</a></li>";
				str += "</ul>";
				
			});
			
			//id="searchResult"인 <div>영역에 HTML태그형식으로 보내서 출력
			$("#searchResult").html(str);
				
		}
		
		function errFunc(){
			alert("요청 실패");
		}
	</script>
	
	<style type="text/css">
		ul{
			border: 2px #cccccc solid;
		}
	</style>




</head>
<body>


<%-- 메인 로고 이미지 --%>
<div id="logo">
	<a href="<%=contextPath%>/car/Main"> <img src="<%=contextPath%>/img/RENT.jpg" width="300" height="80"/> </a>
</div>



	<%-- login | Join --%>
	<table width="100%" height="5" >
		<tr>
			<td align="right" colspan="5">
				<%
					//session내장객체 메모리 영역에 session값 얻기
					 String id = (String)session.getAttribute("id");
				
					//session에 값이 저장되어 있지 않으면?
					if(id == null){
				%>		
						<%-- 네이버 검색을 위한 <form>태그 정의  --%>
						<div id="login">
							<form action="<%=contextPath%>/car/NaverSearchAPI.do" class="form-inline my-2 my-lg-0" id="searchFrm">
						      <input type="hidden" name="startNum" value="1">
						      <input  id="keyword"  name="keyword" class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
						      <button id="searchBtn" class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
						    </form>
						</div>
						<%-- 네이버 검색을 위한 <form>태그 정의  --%>
						
						<div id="login">	
							<a href="<%=contextPath%>/member/login.me">login</a> | 
							<a href="<%=contextPath%>/member/join.me?center=members/join.jsp">join</a>
						</div>
				<%		
					}else{
				%>		
						<%-- 네이버 검색을 위한 <form>태그 정의  --%>
						<div id="login">
							<form action="<%=contextPath%>/car/NaverSearchAPI.do" class="form-inline my-2 my-lg-0" id="searchFrm">
						      <input type="hidden" name="startNum" value="1">
						      <input  id="keyword"  name="keyword" class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
						      <button id="searchBtn" class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
						    </form>
						</div>
						<%-- 네이버 검색을 위한 <form>태그 정의  --%>
						
						<div id="login">
							<%=id%>님 로그인 되셨습니다.
							<a href="<%=contextPath%>/member/logout.me">logout</a>
						</div>
				<%		
					}
				%>
				
			</td>
			
		</tr>	
	</table>

	

	<%-- 메뉴 만들기 --%>
	<table width="100%" background="<%=contextPath%>/img/aa.jpg" height="5">
		<tr>
			<td align="center" bgcolor="red">
				<a href="<%=contextPath%>/car/bb?center=CarReservation.jsp">
					<img src="<%=contextPath%>/img/bb.jpg">
				</a> <%-- 예약하기 --%>
			</td>
			<td align="center" bgcolor="red">
				<a href="<%=contextPath%>/car/cc?center=CarReserveConfirm.jsp">
					<img src="<%=contextPath%>/img/cc.jpg">
				</a> <%-- 예약확인 --%>
			</td>
			<td align="center" bgcolor="red">			
				<a href="<%=contextPath%>/board1/list.bo?nowBlock=0&nowPage=0">
					<img src="<%=contextPath%>/img/dd.jpg">
				</a> <%-- 자유게시판 --%>
			</td>
			<td align="center" bgcolor="red">
				<a href="<%=contextPath%>/car/even"><img src="<%=contextPath%>/img/even.jpg"></a> <%-- 이벤트정보 --%>
			</td>
			<td align="center" bgcolor="red">
				<a href="<%=contextPath%>/car/ee"><img src="<%=contextPath%>/img/ee.jpg"></a> <%-- 공지사항게시판  --%>
			</td>
		</tr>	

		
	</table>	

	

</body>
</html>



