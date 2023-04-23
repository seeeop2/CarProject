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
<title>결제를 위해 계산된 총금액을 보여주는 VIEW</title>

	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

</head>
<body>

	
<%-- 결제하기 버튼을 누르면 결제후 예약요청 --%>
	<form action="<%=contextPath%>/car/CarOrder.do" method="post">
	
	 <%--예약 정보들을 intput type="hidden" 태그로  전달! --%>
	 <input type="hidden" name="carno" value="${requestScope.vo.carno}">
	 <input type="hidden" name="carqty" value="${requestScope.vo.carqty}">
	 <input type="hidden" name="carreserveday" value="${requestScope.vo.carreserveday}">
	 <input type="hidden" name="carins" value="${requestScope.vo.carins}">
	 <input type="hidden" name="carwifi" value="${requestScope.vo.carwifi}">
	 <input type="hidden" name="carnave" value="${requestScope.vo.carnave}">
	 <input type="hidden" name="carbabyseat" value="${requestScope.vo.carbabyseat}">
	 <input type="hidden" name="carbegindate" value="${requestScope.vo.carbegindate}">
	 
	 



	 <div align="center">
	 	<%-- <결제하기> 이미지 --%>
		<img src="<%=contextPath%>/img/haki.jpg">	
		<h1 style="background-color: aqua;">회원 전용</h1>
		
		<p>
			<font size="13" color="blue">
				차량 기본 금액 비용 : ￦ ${requestScope.totalreserve}원
			</font>
		</p>
		<p>
			<font size="13" color="blue">
				차량 옵션 금액 비용 : ￦ ${requestScope.totaloption}원
			</font>
		</p>	
		<p>
			<font size="13" color="blue">
				총 비용 : ￦ ${totalreserve + totaloption}원
			</font>
		</p>
		<p>
			회원 아이디 : <input type="text" name="memberId" value="${requestScope.membervo.id}">
			&nbsp;&nbsp;&nbsp;
			회원 비밀번호 : <input type="password" name="memberpass" value="${requestScope.membervo.pass}">
			
			<input type="submit" value="결제후 예약 요청">
			
<!-- 			<input type="button" value="장바구니 담기" onclick="cart();">  -->
			
			
		</p>	
	  </div>
	  
	</form>
	
	
<script type="text/javascript">

	function cart(){
		
		alert("asdasd");
		
		var carno = $("input[name='carno']").val();
		var carqty = $("input[name='carqty']").val();
		var carreserveday = $("input[name='carreserveday']").val();
		var carins = $("input[name='carins']").val();
		var carwifi = $("input[name='carwifi']").val();
		var carnave = $("input[name='carnave']").val();
		var carbabyseat = $("input[name='carbabyseat']").val();
		var carbegindate = $("input[name='carbegindate']").val();
		
		var url = "car/Cart.do?cano="+cano+"&carqty="+carqty+"&carreserveday="+carreserveday+
				  		   "&carins="+carins+"&carwifi="+carwifi+"&carnave="+carnave+
				  		   "&carbabyseat="+carbabyseat+"&carbegindate="+carbegindate;
		
		location.href = url; 
		
// 		alert(carno);
		

		
	}

</script>	
	
	
	
	
</body>
</html>












