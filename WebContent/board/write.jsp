<%@page import="VO.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" type="text/css" href="/MVCBoard/style.css"/>
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



</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	String nowPage = request.getParameter("nowPage");
	String nowBlock = request.getParameter("nowBlock");

%>

<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr height="40"> 
    <td width="41%" style="text-align: left"> &nbsp;&nbsp;&nbsp; 
    	<img src="<%=contextPath%>/board/images/board02.gif" width="150" height="30">
    </td>
    <td width="57%">&nbsp;</td>
    <td width="2%">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="3"><div align="center"><img src="<%=contextPath%>/board/images/line_870.gif" width="870" height="4"></div></td>
  </tr>
  <tr> 
    <td colspan="3"><div align="center"> 
        <table width="95%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="20" colspan="3"></td>
          </tr>
          
<%
//----------------- ���� �ۼ��ϴ� ����� ������ member���̺��� ��ȸ �ؿ� �Ʒ��� �����ο� ������-------------
MemberVo membervo = (MemberVo)request.getAttribute("membervo"); 
String email = membervo.getEmail();//�α����� ����� �̸���
String name = membervo.getName();//�α����� ��� �̸� (���� �ۼ��ϴ� ��� �̸�)
String memberId = membervo.getId();//�α����� ��� ���̵�(���ۼ��� ���̵�)

%>                     
          <tr> 
            <td colspan="3" valign="top">
            <div align="center"> 
                <table width="100%" height="373" border="0" cellpadding="0" cellspacing="1" class="border1">
                  <tr> 
                    <td width="10%" height="29" bgcolor="#e4e4e4" class="text2">
                    	<div align="center">�� �� ��</div>
                    </td>
                    <td width="17%" bgcolor="#f5f5f5" style="text-align: left">
                    	<input type="text" name="writer" size="20" class="text2" value="<%=name%>" disabled/>
                    </td>
   					<td width="6%" height="29" bgcolor="#e4e4e4" class="text2">
                    	<div align="center">���̵�</div>
                    </td>
                    
 				<%
 					String id = (String)session.getAttribute("id");
 					
 					if(id == null){//�α��� ���� �ʾ��� ��� 
 				%>		
 						<script>
 							alert("�α��� �ϰ� ���� �ۼ��ϼ���!"); 
 							history.back();
 						</script>
 				<%	
 					}
 				%>
 				              
             
                    <td width="17%" bgcolor="#f5f5f5" style="text-align: left">
                    	<input type="text" name="writer_id" 
                    	       size="20" class="text2" value="<%=memberId%>" disabled/>
                    </td>                    
                    <td width="13%" bgcolor="#e4e4e4">
                    	<div align="center"> 
                        	<p class="text2">�����ּ�</p>
                      	</div>
                    </td>
                    <td width="40%" bgcolor="#f5f5f5" style="text-align: left">
                        <input type="text" name="email" size="40" class="text2" value="<%=email%>" disabled/>
                    </td>
                  </tr>             
                  <tr> 
                    <td height="31" bgcolor="#e4e4e4" class="text2">
                    	<div align="center">��&nbsp;&nbsp;&nbsp;��</div>
                    </td>
                    <td colspan="3" bgcolor="#f5f5f5" style="text-align: left">
                    	<input type="text" name="title" size="70"/>
                    </td>
                  </tr>
                  <tr> 
                    <td bgcolor="#e4e4e4" class="text2">
                    	<div align="center">�� &nbsp;&nbsp; ��</div>
                    </td>
                    <td colspan="5" bgcolor="#f5f5f5" style="text-align: left">
                    	<textarea name="content" rows="15" cols="100"></textarea>
                    	
                    </td>
                  </tr>
                  <tr> 
                    <td bgcolor="#e4e4e4" class="text2">
                    	<div align="center">�н�����</div>
                    </td>
                    <td colspan="3" bgcolor="#f5f5f5" style="text-align: left">
                    	<input type="password" name="pass"/>
                    </td>
                  </tr>
                  <tr>
                  	<td colspan="4"><p id="resultInsert"></p></td>
                  </tr>
                </table>
              </div>
              </td>
          </tr>
          <tr> 
            <td colspan="3">&nbsp;</td>
          </tr>
          <tr> 
            <td width="48%">
            <%-- ��� --%>
            <div align="right">
            	<a href="" id="registration1">
            		<img src="<%=contextPath%>/board/images/confirm.gif" border="0"/>
            	</a>
            	
            </div>
            </td>
            <%-- ��� --%>
            <td width="10%">
            <div align="center">
            	<a href="" id="list">
            		<img src="<%=contextPath%>/board/images/list.gif" border="0"/>
            	</a>
            </div>
            </td>
            <td width="42%"></td>
          </tr>
        </table>
      </div></td>
  </tr>
</table>


	<script src="http://code.jquery.com/jquery-latest.min.js"></script>

	<script type="text/javascript">
		
		//����� Ŭ���ϸ� ?
		$("#list").click(function(event){
			
			event.preventDefault();//a�±��� href�⺻�̺�Ʈ ����
			
			//board���̺� ����� ���� ��ȸ �ϴ� ��û!
			location.href = "<%=contextPath%>/board1/list.bo?nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>";
			
		});
	
	
		//���� ������ �Է��ϰ� ��� �̹����� ���ΰ� �ִ� <a>�� Ŭ�� �ϸ�?
		$("#registration1").on("click",function(event){
			
			event.preventDefault();//a�±��� href�⺻�̺�Ʈ ���� 
			
			//�ۼ��� ���� �Է��� <input>�� ������  �Է��� ���� ��´�.
			var writer = $("input[name=writer]").val();
			//�ۼ��� ���̵� �Է� �޾� ��´�.
			var id = $("input[name=writer_id]").val();
			//�ۼ����� �̸����� �Է� �޾� ��´�.
			var email = $("input[name=email]").val();
			//�������� �Է¹޾� ��´�.
			var title = $("input[name=title]").val();
			//�۳����� �Է¹޾� ��´�.
			var content = $("textArea[name=content]").val();
			//�� ��й�ȣ �Է¹޾� ��´�.    
			var pass = $("input[name=pass]").val();
			
			
			$.ajax({
					 type:"post",
					 async:true,
					 url:"<%=contextPath%>/board1/writePro.bo",
					 data:{ w : writer, 
						 	i : id,
						 	e : email, 
						 	t : title, 
						 	c : content, 
						 	p : pass },
					 dataType : "text",
					 success:function(data){
						 
						 console.log(data);//ũ�� F12 ���� �ܼ� �ǿ��� �޼��� Ȯ�� 
						 
						 if(data == "1"){
						
							 $("#resultInsert").text("�� �߰� ����").css("color","red");
							
						
							  
						 }else{//"0"
							 
							 $("#resultInsert").text("�� �߰� ����").css("color","blue");
							 
							 
						 }
						 
					 },
				
				   });
			
			
			
		});
		
	</script>











</body>
</html>