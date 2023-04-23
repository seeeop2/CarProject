<%@page import="VO.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
	//조회한 글정보 얻기
	BoardVo vo = (BoardVo)request.getAttribute("vo");
	String name	 = vo.getB_name();//조회한 글을 작성한 사람
	String email = vo.getB_email();//조회한 글을 작성한 사람의 이메일 
	String title = vo.getB_title();//조회한 글제목
	String content = vo.getB_content().replace("/r/n", "<br>"); //조회한 글내용
	
	String nowPage = (String)request.getAttribute("nowPage");
	String nowBlock = (String)request.getAttribute("nowBlock");
	String b_idx = (String)request.getAttribute("b_idx");

	
	//로그인 하지 않았을 경우 글제목 눌렀을떄 화면 보여 주지 말자!
	String id = (String)session.getAttribute("id");
	
	if(id == null){//로그인 하지 않았을 경우 
%>		
		<script>
			alert("로그인 하시면 글 내용 확인 가능!"); 
			history.back();
		</script>
<%	
	}
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정 화면</title>
</head>
<body>
	<table width="80%" border="0" cellspacing="0" cellpadding="0">
		<tr height="40">
			<td width="41%" style="text-align: left">&nbsp;&nbsp;&nbsp; 
				<img src="<%=contextPath%>/board/images/board02.gif" width="150" height="30">
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<div align="center">
					<img src="<%=contextPath%>/board/images/line_870.gif" width="870" height="4">
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<div align="center">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="20" colspan="3"></td>
						</tr>
						<tr>
							<td height="327" colspan="3" valign="top">
								<div align="center">
									<table width="95%" height="373" border="0" cellpadding="0" cellspacing="1" class="border1">
										<tr>
											<td width="13%" height="29" bgcolor="#e4e4e4" class="text2">
												<div align="center">작 성 자</div>
											</td>
											<td width="34%" bgcolor="#f5f5f5" style="text-align: left">
												&nbsp;&nbsp; 
												<input type="text" name="writer" value="<%=name%>" disabled>
													
											</td>
											<td width="13%" bgcolor="#e4e4e4">
												<div align="center">
													<p class="text2">이메일</p>
												</div></td>
											<td width="40%" bgcolor="#f5f5f5" style="text-align: left">
												&nbsp;&nbsp; 
												<input type="email" name="email" value="<%=email%>" id="email" disabled>
											</td>
										</tr>
										<tr>
											<td height="31" bgcolor="#e4e4e4" class="text2">
												<div align="center">제&nbsp;&nbsp;&nbsp; &nbsp; 목</div>
											</td>
											<td colspan="3" bgcolor="#f5f5f5" style="text-align: left">
												&nbsp;&nbsp; 
												<input type="text" name="title" value="<%=title%>" id="title" disabled>
											</td>
										</tr>
										<tr>
											<td height="245" bgcolor="#e4e4e4" class="text2">
												<div align="center">내 &nbsp;&nbsp; 용</div>
											</td>
											<td colspan="3" bgcolor="#f5f5f5" style="text-align: left; vertical-align: top;">
												&nbsp; 
												<textarea rows="20" cols="100" name="content" id="content" disabled><%=content%></textarea>
											</td>
										</tr>
										<tr>
											<td bgcolor="#e4e4e4" class="text2">
												<div align="center">패스워드</div>
											</td>
											<td colspan="2" bgcolor="#f5f5f5" style="text-align: left">
												<input type="password" name="pass" id="pass" />
											</td>
											<td><p id="pwInput"></p></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3">&nbsp;</td>
						</tr>
						<tr>
							<td style="width: 48%">
								<div align="right">
										<%--답변달기 --%>
										<input type="image" src="<%=contextPath%>/board/images/reply.gif" id="reply" />&nbsp;&nbsp; 
									
										<%--수정하기 --%>
										<input type="image" src="<%=contextPath%>/board/images/update.gif" id="update"/>&nbsp;&nbsp; 
										<%--삭제하기 --%>
										<input type="image" src="<%=contextPath%>/board/images/delete01.gif" id="delete" 
										       onclick="javascript:deletePro('<%=b_idx%>');"/>
									
								</div>
							</td>
							<td width="10%">
								<div align="center">
									<input type="image" 
									       src="<%=contextPath%>/board/images/list.gif"
									       id="list"
									       onclick="location.href='<%=contextPath%>/board1/list.bo?nowBlock=<%=nowBlock%>&nowPage=<%=nowPage%>'"/>
									       &nbsp;
								</div>
							</td>
							<td width="42%"></td>
						</tr>
						<tr>
							<td colspan="3" style="height: 19px">&nbsp;</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	

	<style>
		#update, #delete{
			visibility: hidden;
		}							
		
	</style>

	<%--답변 을 눌렀을때 답변을 작성할수 있는 화면 요청 --%>
	<form action="<%=contextPath%>/board1/reply.do" id="replyForm">
		<input type="hidden" name="b_idx" value="<%=b_idx%>" id="b_idx">
	</form>
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript">
	
		//답변달기를 클릭했을때....
		$("#reply").on("click",function(){
						
				$("#replyForm").submit();
		});
	
	
	
	
		//삭제하기 클릭했을때.. 삭제할 글번호를 받아 삭제요청을 하는 함수 
		function deletePro(b_idx){
				
			
						//확인   취소 버튼이 있는 한번더 묻는 창!
			var result = window.confirm("정말로 글을 삭제하시겠어요?");
					
			if(result == true){//확인 버튼 클릭함! 삭제 할게요~
				
				//비동기 방식으로 글삭제 요청!
				$.ajax({
					type : "post",
					async : true,
					url : "<%=contextPath%>/board1/deleteBoard.bo",
					data : {b_idx : b_idx},
					dataType : "text",
					success : function(data){
						console.log(data);
						
						if(data == "삭제성공"){
							
 							$("#pwInput").text("삭제성공!").css("color","blue");
 							$("#pwInput").append("<br>3초뒤에 게시판 리스트 목록 화면으로 이동합니다......").css("color","blue");
							 
							 //입력하여 수정할수 있는 <input>2개 , <textArea>1개 비활성화
							 document.getElementById("email").disabled = true;
							 document.getElementById("title").disabled = true;
							 document.getElementById("content").disabled = true;
							
							 //3초 휴식 후  목록 <input>이 강제 클릭 될수 있도록 !!!!!!!!!!!!!!!!!!!!!!
							 setTimeout(function(){    $("#list").trigger("click") }, 3000);
							
							
						}else{
							$("#pwInput").text("삭제실패!").css("color","blue");
 							
							 //입력하여 수정할수 있는 <input>2개 , <textArea>1개 비활성화
							 document.getElementById("email").disabled = false;
							 document.getElementById("title").disabled = false;
							 document.getElementById("content").disabled = false;
							 
							 //3초 휴식 후 
							 setTimeout(function(){  location.reload();   }, 3000);
							 
						}
						
					}
					
				});
				
				
				
			}else{//취소 버튼 클릭함! 삭제 안함!
				
				return false;
				
			}
			
			
		}
	
	
	
		//수정 이미지 버튼을 선택해서 가져와서 
		//클릭 이벤트 등록!
		$("#update").on("click", function(){ 
			
			var idx = $("#b_idx").val(); //수정시 사용할 글번호 얻기 
			var email = $("#email").val(); //수정시 입력한 이메일 얻기
			var title = $("#title").val(); //수정시 입력한 제목 얻기
			var content = $("#content").val();//수정시 입력한 내용 얻기 
			
			$.ajax({
					 type : "post",
					 async : true,
					 url : "<%=contextPath%>/board1/updateBoard.do",
					 data :  {idx : idx,  email : email, title : title, content : content},
					 dataType : "text",
					 success : function(data){
						 
						 console.log(data); //크롬웹브라우저에서 F12를눌러  콘솔 탭에서 로그메시지로 확인
	
						 if(data == "수정성공"){
							 
							 $("#pwInput").text("수정성공!").css("color","blue");
							 
							 //입력하여 수정할수 있는 <input>2개 , <textArea>1개 비활성화
							 document.getElementById("email").disabled = true;
							 document.getElementById("title").disabled = true;
							 document.getElementById("content").disabled = true;
							 
							 
							 
						 }else{//"수정실패"
							 
							 $("#pwInput").text("수정실패!").css("color","red");
							 
							 //입력하여 수정할수 있는 <input>2개 , <textArea>1개 활성화
							 document.getElementById("email").disabled = false;
							 document.getElementById("title").disabled = false;
							 document.getElementById("content").disabled = false;							 
						 }
						 
						 
					 },
					 error : function(){
						 alert("비동기 통신 장애");
					 }
							
						
				
			});
			
			
		});
	
	
	
	
		//수정 삭제를 위해 비밀번호를 입력하고 포커스가 떠난 이벤트가 발생했을때...
		$("#pass").on("focusout",function(){
			
			$.ajax({
					 type : "post",
					 async : true,
					 url : "<%=contextPath%>/board1/password.do",
					 data :  { b_idx : $("#b_idx").val() ,  pass : $("#pass").val() },
					 dataType : "text",
					 success : function(data){
						 
						 console.log(data); //크롬웹브라우저에서 F12를눌러  콘솔 탭에서 로그메시지로 확인

						 if(data == "비밀번호틀림"){
							 
							 $("#pwInput").text("글의 비밀번호를 잘못 입력 했습니다.").css("color","blue");
							
							 
							 document.getElementById("update").style.visibility= "hidden";
							 document.getElementById("delete").style.visibility= "hidden";

							 //입력하여 수정할수 있는 <input>2개 , <textArea>1개 비활성화
							 document.getElementById("email").disabled = true;
							 document.getElementById("title").disabled = true;
							 document.getElementById("content").disabled = true;
							 
							 
							 
						 }else{//"비밀번호맞음"
							 
							 $("#pwInput").text("비밀번호가 맞으므로 수정, 삭제가 가능합니다.").css("color","red");
							 
						 
							 document.getElementById("update").style.visibility= "visible";
							 document.getElementById("delete").style.visibility= "visible";


							 //입력하여 수정할수 있는 <input>2개 , <textArea>1개 활성화
							 document.getElementById("email").disabled = false;
							 document.getElementById("title").disabled = false;
							 document.getElementById("content").disabled = false;							 
						 }
						 
						 
					 },
					 error : function(){
						 alert("비동기 통신 장애");
					 }
				
				
			});			
			
		});
	
	</script>
</body>
</html>


<!-- 		//입력한 비밀번호가 DB에 저장되어 있어서 맞으면? -->
<!-- 		//		1. "비밀번호가 맞으므로 수정,삭제가  가능 합니다."  메세지를 보여주고 -->
<!-- 		//		2. 수정을 할수 있도록 모든 입력 html을 활성화 시켜 줍니다. -->
<!-- 		//		3. 또한 동적으로 새로운  답변, 수정, 삭제 버튼을 자바스크립트로 생성하여 -->
<!-- 		//          HTML태그들의 영역에 추가하면 보이게 합니다. -->
		
<!-- 		//입력한 비밀번호가 DB에 저장되어 있지 않으면? -->
<!-- 		//		1. "<h4>글의 비밀번호를 잘못 입력 했습니다.</h4>" 메세지를 보여 줍니다. -->
<!-- 		//      2. 수정을 할수 없게  모든 입력 html을 비활성화 시켜 줍니다. -->
<!-- 		//		3. 또한 비밀번호가 틀렸을 경우에는 목록 버튼만 보이게 합니다. -->
