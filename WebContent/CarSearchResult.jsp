<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSTL태그의 core라이브러리관련 태그들을 사용하기 위해  import --%>    
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>      
    
<%request.setCharacterEncoding("UTF-8");%> 
 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"/>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CarSearchResult.jsp 네이버 블로그 검색 정보 보여주는 View페이지</title>
</head>
<body>
<%

	/*
			{    }  <- JSONObject클래스로부터 객체 생성    new JSONObject();
  			[    ]  <- JSONArray클래스로부터 객체 생성    new JSONArray();


	JSONObject Object = new JSONObject();
	//{  }
   Object.put("name", "김길동");
	//{"name":"김길동"  }
   Object.put("age", "10");
	//{"name":"김길동" , "age":"10" }
				
	JSONArray array = new JSONArray();
	//[]
			   Object.put("items", array);
			 //{"name":"김길동" , "age":"10", "items" : [] }
				
	*/	

	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
					
		//JSONObject객체 형태의 문자열 받기 
		String searchData = (String)request.getAttribute("searchData");
		//"{ items:[ {title:검색한문서제목, description:검색한요약문서내용, ....  }, {....}  ]    }"

		//문자열 -> JSONObject객체 로 변환 
		//JSONParser객체 생성후!
		JSONParser jsonParser = new JSONParser();
		//parse메소드를 호출할때.... JSONObject객체 형태의 문자열을 전달하면
		//JSONObject객체 로 변환 해서 반환 해줍니다.
		JSONObject	jsonObject	 = (JSONObject)jsonParser.parse(searchData);
		//{ items:[ {title:검색한문서제목, description:검색한요약문서내용, ....  }, {....}  ]    }
		
		//[ {title:검색한문서제목, description:검색한요약문서내용, ....  }, {....}  ]  
		JSONArray jsonArray = (JSONArray)jsonObject.get("items");
		
	%>

	<%-- 빨간 선  --%>
	<hr width="100%" color="red">
	
	<table width="100%"border="1">		
		<tr style="background-color: aqua;">
			<td>검색 문서 제목</td>
			<td>검색 문서 요약 내용</td>
			<td>검색된 블로거 이름</td>
			<td>블로그 포스트를 작성한 블로거의 링크</td>
			<td>블로그 포스트를 작성한 날짜 </td>
			<td>바로가기</td>
		</tr>	
			
	<%
		for(int i=0; i<jsonArray.size(); i++){
			
			//[ {title:검색한문서제목, description:검색한요약문서내용, ....  }, {....}, {  }  ]  
			//							0                                 1      2
			JSONObject object = (JSONObject)jsonArray.get(i);
	
	%>		
		<tr>
			<td><%=object.get("title") %></td>
			<td><%=object.get("description")%></td>
			<td><%=object.get("bloggername")%></td>
			<td><%=object.get("bloggerlink")%></td>
			<td><%=object.get("postdate")%></td>
			<td><a href='"+ <%=object.get("link")%> +"'>바로가기</a></td>
		</tr>	
	 		
	<%		
		}
	%>
	
	</table>
	



			







</body>
</html>