<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
%>
    
    
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

<title>회원가입</title>
</head>
<body>

<form action="<%=contextPath%>/member/joinPro.me" class="form">	
	<div style="height: 100px;"></div>
	<div class="container">
		<div class="row justify-content-lefts">
			<h2>이용약관</h2>
		</div>
		<div class="accordion" id="accordionExample">
			<div class="card">
				<div class="card-header" id="headingOne">
					<h2 class="mb-0">
						<button class="btn btn-link btn-block text-left" type="button"
							data-toggle="collapse" data-target="#collapseOne"
							aria-expanded="true" aria-controls="collapseOne">
							약관동의 내용1
						</button>
					</h2>
				</div>

				<div id="collapseOne" class="collapse" aria-labelledby="headingOne"
					data-parent="#accordionExample">
					<div class="card-body">Anim pariatur cliche reprehenderit,
						enim eiusmod high life accusamus terry richardson ad squid. 3 wolf
						moon officia aute, non cupidatat skateboard dolor brunch. Food
						truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor,
						sunt aliqua put a bird on it squid single-origin coffee nulla
						assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer
						labore wes anderson cred nesciunt sapiente ea proident. Ad vegan
						excepteur butcher vice lomo. Leggings occaecat craft beer
						farm-to-table, raw denim aesthetic synth nesciunt you probably
						haven't heard of them accusamus labore sustainable VHS.</div>
				</div>
			</div>
			<div class="card">
				<div class="card-header" id="headingTwo">
					<h2 class="mb-0">
						<button class="btn btn-link btn-block text-left collapsed"
							type="button" data-toggle="collapse" data-target="#collapseTwo"
							aria-expanded="false" aria-controls="collapseTwo">
							약관동의 내용2
						</button>
					</h2>
				</div>
				<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
					data-parent="#accordionExample">
					<div class="card-body">Anim pariatur cliche reprehenderit,
						enim eiusmod high life accusamus terry richardson ad squid. 3 wolf
						moon officia aute, non cupidatat skateboard dolor brunch. Food
						truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor,
						sunt aliqua put a bird on it squid single-origin coffee nulla
						assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer
						labore wes anderson cred nesciunt sapiente ea proident. Ad vegan
						excepteur butcher vice lomo. Leggings occaecat craft beer
						farm-to-table, raw denim aesthetic synth nesciunt you probably
						haven't heard of them accusamus labore sustainable VHS.</div>
				</div>
			</div>
			<div class="card">
				<div class="card-header" id="headingThree">
					<h2 class="mb-0">
						<button class="btn btn-link btn-block text-left collapsed"
							type="button" data-toggle="collapse" data-target="#collapseThree"
							aria-expanded="false" aria-controls="collapseThree">
							약관동의 내용3
						</button>
					</h2>
				</div>
				<div id="collapseThree" class="collapse"
					aria-labelledby="headingThree" data-parent="#accordionExample">
					<div class="card-body">Anim pariatur cliche reprehenderit,
						enim eiusmod high life accusamus terry richardson ad squid. 3 wolf
						moon officia aute, non cupidatat skateboard dolor brunch. Food
						truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor,
						sunt aliqua put a bird on it squid single-origin coffee nulla
						assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer
						labore wes anderson cred nesciunt sapiente ea proident. Ad vegan
						excepteur butcher vice lomo. Leggings occaecat craft beer
						farm-to-table, raw denim aesthetic synth nesciunt you probably
						haven't heard of them accusamus labore sustainable VHS.</div>
				</div>
			</div>
		</div>
		<div class="row justify-content-center text-center">
			<div class="col-4">
				<input type="checkbox" name=""> 위의 약관의 내용에 동의합니다.
				<p id="checkboxInput"></p> 
			</div>

		</div>		
		<div class="row" style="height: 25px;"></div>
		<div class="row justify-content-left">
			<h1>회원가입 </h1>
		</div>

			<div class="form-group">
				<div class="row">
					<div class="col-6">
						<label>아이디</label> 
						<input type="text" 
							   id="id" 
							   name="id"
							   class="form-control"
							   placeholder="가입할 아이디를 적어주세요."> 
						<p id="IdInput"></p>	   
					</div>
					<div class="col-6">
						<label>비밀번호</label> 
						<input type="password" 
							   id="pass" 
							   name="pass"
							   class="form-control"
							   placeholder="가입할 비밀번호를 적어주세요.">
						<p id="passInput"></p> 
					</div>						
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-3">
						<label>이름</label> 
						<input type="text" 
							   id="name" 
							   name="name"
							   class="form-control"
							   placeholder="가입할 이름을 적어주세요."> 
						<p id="nameInput"></p>
					</div>
					
					<div class="col-9">
						<label>나이</label> 
						<input type="text" 
							   id="age" 
							   name="age"
							   class="form-control"
							   placeholder="나이를 적어주세요."> 
						<p id="ageInput"></p>
						
					</div>					
				</div>
			</div>					
			<div class="form-group">
				<div class="row">
					<div class="col-6">
						<%-- name속성값 address1 부터 ~~ address5 까지 입력되어 있는 주소를 모두 합쳐서 DB에 address열에  INSERT 하자. --%>
						<label>주소</label> 
						<input type="text" id="sample4_postcode" name="address1" class="form-control" placeholder="우편번호">
						<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" class="form-control"><br>
						
						<input type="text" id="sample4_roadAddress" name="address2" placeholder="도로명주소" class="form-control">
						<input type="text" id="sample4_jibunAddress" placeholder="지번주소" name="address3" class="form-control">
						
						<span id="guide" style="color:#999;display:none"></span>
						
						<input type="text" id="sample4_detailAddress" placeholder="상세주소" name="address4" class="form-control">
						<input type="text" id="sample4_extraAddress" placeholder="참고항목"  name="address5" class="form-control">
						
						<p id="addressInput"></p>
						
					</div>
					
					<div class="col-6">
						<label>성별</label> 
						<hr> 
						남성
					    <input type="radio" 
							   id="gender" 
							   name="gender"> 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	   
					     여성
					   <input type="radio" 
							   id="gender" 
							   name="gender">
							    
						<p id="genderInput"></p>
					
					</div>					
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-4">
						<label>Email</label> 
						<input type="email" 
							   id="email" 
							   name="email"
							   class="form-control"
							   placeholder="이메일을 적어주세요."> 
		           	    <p id="emailInput"></p>
							   
					</div>
					
					<div class="col-4">
						<label>연락처</label> 
						<input type="tel" 
							   id="tel" 
							   name="tel"
							   class="form-control"
							   placeholder="연락처를 적어주세요."> 
							   
		           	    <p id="telInput"></p>
						
					</div>
					<div class="col-4">
						<label>HP</label> 
						<input type="text" 
							   id="hp" 
							   name="hp"
							   class="form-control"
							   placeholder="HP를 적어주세요."> 
							   
					   <p id="HpInput"></p>
					</div>												
				</div>
			</div>				
								
			<div class="row">
				<div class="col">
					<a href="a.jsp" onclick="check(); return false;" 
					   class="btn btn-primary btn-block">
						회원가입하기
					</a>
				</div>
			</div>
			<br /> <br /> <br />
		
	</div>
</form>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
		integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
		crossorigin="anonymous"></script>
		
	<%-- 다음 우편번호 API --%>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
</script>	
	
	
	<script type="text/javascript">
	

		//모든 <input>을 선택해서 <input>에 데이터를 입력하기 위해 키가 눌러져 있다가 뗀 행동을 했을때..
	    $("input").keyup(function(){    	
	    	$("#id").focusout(function(){	
	    		//입력 했다면?
		    	if($("#id").val() != ""){	
		    		$("#IdInput").text("아이디입력 완료").css("color","blue");
		    	}else{	
//		 	    	//모든<p id="IdInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
		 	    	$("#IdInput").text("");
		    	}
	    	});
	    	//////////////////////////////////////////////////////////    	
	    	$("#pass").focusout(function(){
	    		//입력 했다면?
		    	if($("#pass").val() != ""){	
		    		$("#passInput").text("pass입력 완료").css("color","blue");
		    	}else{	
//		 	    	//모든<p id="passInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
		 	    	$("#passInput").text("");
		    	}	
	    	});	  
	    	//////////////////////////////////////////////////////////
	    	$("#name").focusout(function(){ 		
	    		//입력 했다면?
		    	if($("#name").val() != ""){    		
		    		$("#nameInput").text("name입력 완료").css("color","blue");    	
		    	}else{  		
//		 	    	//모든<p id="nameInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
		 	    	$("#nameInput").text("");
		    	}		
	    	});	 
	    	//////////////////////////////////////////////////////////
	    	$("#age").focusout(function(){ 		
	    		//입력 했다면?
		    	if($("#age").val() != ""){    		
		    		$("#ageInput").text("age입력 완료").css("color","blue");    	
		    	}else{  		
//		 	    	//모든<p id="ageInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
		 	    	$("#ageInput").text("");
		    	}		
	    	});	 
	    	//////////////////////////////////////////////////////////
	    	 $("input[name='address1'],input[name='address2'],input[name='address3'],input[name='address4'],input[name='address5']").focusout(function(){ 		
	    		//입력 했다면?
		    	if(
		    	   $("input[name='address1']").val() != "" ||
		    	   $("input[name='address2']").val() != "" ||
		    	   $("input[name='address3']").val() != "" ||
		    	   $("input[name='address4']").val() != "" ||
		    	   $("input[name='address5']").val() != "" 
		    	  ){    		
		    		
		    		$("#addressInput").text("모든 주소 입력 완료").css("color","blue");    	
		    	
		    	}else{  		
		    		
//		 	    	//모든<p id="addressInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
		 	    	$("#addressInput").text("모든 주소를 입력하지 않음!").css("color","red");
		    	}		
	    	});	 
	    	
	    	
	    	
	    	
		    	$("input[name='gender']").focusout(function(){ 		
		    		//입력 했다면?
			    	if($("input[name='gender']").val() != ""){    		
			    		$("#genderInput").text("gender입력 완료").css("color","blue");    	
			    	}else{  		
//			 	    	//모든<p id="genderInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
			 	    	$("#genderInput").text("");
			    	}		
		    	});	 
		    	//////////////////////////////////////////////////////////	    		
	    	
		    	
		    	//////////////////////////////////////////////////////////
		    	$("#email").focusout(function(){ 		
		    		//입력 했다면?
			    	if($("#email").val() != ""){    		
			    		$("#emailInput").text("email입력 완료").css("color","blue");    	
			    	}else{  		
//			 	    	//모든<p id="emailInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
			 	    	$("#emailInput").text("");
			    	}		
		    	});	 	    	
		    	

		    	
		    	
		    	$("#tel").focusout(function(){ 		
		    		//입력 했다면?
			    	if($("#tel").val() != ""){    		
			    		$("#telInput").text("tel입력 완료").css("color","blue");    	
			    	}else{  		
//			 	    	//모든<p id="telInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
			 	    	$("#telInput").text("");
			    	}		
		    	});	 
		    	
		    	
		    	
		    	$("#hp").focusout(function(){ 		
		    		//입력 했다면?
			    	if($("#hp").val() != ""){    		
			    		$("#HpInput").text("hp입력 완료").css("color","blue");    	
			    	}else{  		
//			 	    	//모든<p id="HpInput">요소들의 text값을 빈공백으로 설정해 없애 준다.
			 	    	$("##HpInput").text("");
			    	}		
		    	});	 		    	

	    });
	
	  	//약관동의 체크박스를 클릭했을때..
	    $("input[type='checkbox']").click(function(){
	    	//<p id="checkboxInput">약관에 동의해 주세요.</p>요소에 적힌 
	    	//약관에 동의해 주세요. 대신 체크함 넣기 
	   		$("#checkboxInput").text("체크함").css("color","blue");
	   	});		

		
		function check() {
			//약관 동의 체크박스 <input>요소를 선택해서 가져와  
    		var checkbox = $("input[type='checkbox']");
			
			//약관 동의 체크 했는지 검사
			if(!checkbox.is(":checked")){
				
				$("#checkboxInput").text("약관에 동의해 주세요.").css("color","red");
				
				checkbox.focus();//체크하지 않은 약관동의 체크박스에 포커스 
			    			
			}
			
			//아이디를 입력할 수 있는 <input>요소를 선택해서 가져와
    		var id = $("#id");
		
    		//이름을 입력했는지 검사
    		if(id.val().length == 0 || id.val() == ""){
    			$("#IdInput").text("아이디를 입력하지 않음!").css("color","red");	
    			id.focus();
       		}
			
    		//비밀번호를 입력할 수 있는 <input>요소를 선택해서 가져와
    		var pass = $("#pass");
    		
    		//이름을 입력했는지 검사
    		if(pass.val().length == 0 || pass.val() == ""){
    			$("#passInput").text("pass를 입력하지 않음!").css("color","red");	
    			pass.focus();
       		}
    		
    		//이름을 입력할 수 있는 <input>요소를 선택해서 가져와
    		var name = $("#name");
    		
    		//이름을 입력했는지 검사
    		if(name.val().length == 0 || name.val() == ""){
    			$("#nameInput").text("name를 입력하지 않음!").css("color","red");	
    			name.focus();
       		}
    		
    		//나이를 입력할 수 있는 <input>요소를 선택해서 가져와
    		var age = $("#age");
 
    		//이름을 입력했는지 검사
    		if(age.val().length == 0 || age.val() == ""){
    			$("#ageInput").text("age를 입력하지 않음!").css("color","red");	
    			age.focus();
       		}    		
    		
    		//주소1 ~ 주소5를  입력할 수 있는 <input>요소를 선택해서 가져와
    		var sample4_postcode = $("input[name='address1']"); //우편번호 
    		
    			console.log(sample4_postcode.val());
    		
    		var sample4_roadAddress = $("input[name='address2']"); //도로명주소
    		var sample4_jibunAddress = $("input[name='address3']"); //지번주소
    		var sample4_detailAddress = $("input[name='address4']");//상세주소
    		var sample4_extraAddress = $("input[name='address5']");//참고항목
    		
//     		var sample4 = sample4_postcode.val() + 
//     					  sample4_roadAddress.val() +
//     					  sample4_jibunAddress.val() + 
//     					  sample4_detailAddress.val() +    					  
//     					  sample4_extraAddress.val();
    		
    		//모든 주소를 입력했는지 검사
    		if((sample4_postcode.val().length == 0 || sample4_postcode.val() == "") ||
   				(sample4_roadAddress.val().length == 0 || sample4_roadAddress.val() == "") ||
   				(sample4_jibunAddress.val().length == 0 || sample4_jibunAddress.val() == "") ||
   				(sample4_detailAddress.val().length == 0 || sample4_detailAddress.val() == "") ||
   				(sample4_extraAddress.val().length == 0 || sample4_extraAddress.val() == "") 	
    		  ){
    			$("#addressInput").text("모든 주소를 입력하지 않음!").css("color","red");	
    			sample4_extraAddress.focus(); //참고항목에 포커스 
       		}    		
    		
    		
    		
    		//성별을 선택할수 있는 라디오 버튼 <input>요소들을 선택해서 가져와
    		var gender = $("input[name='gender']");
    		
    		//이름을 입력했는지 검사
    		if(gender.val().length == 0 || gender.val() == ""){
    			$("#genderInput").text("gender를 선택하지 않음!").css("color","red");	
    			gender.focus();
       		} 
    		
    		//이메일을 입력할 수 있는 <input>요소를 선택해서 가져와
    		var email = $("#email");
    		
    		//이름을 입력했는지 검사
    		if(email.val().length == 0 || email.val() == ""){
    			$("#emailInput").text("email를 입력하지 않음!").css("color","red");	
    			email.focus();
       		}    
    		
    		
    		//tel을 입력할 수 있는 <input>요소를 선택해서 가져와
    		var tel = $("#tel");
 
    		//tel 입력했는지 검사
    		if(tel.val().length == 0 || tel.val() == ""){
    			$("#telInput").text("tel를 입력하지 않음!").css("color","red");	
    			tel.focus();
       		}        		
    		
    		//hp을 입력할 수 있는 <input>요소를 선택해서 가져와
    		var hp = $("#hp");
    		
    		//tel 입력했는지 검사
    		if(hp.val().length == 0 || hp.val() == ""){
    			$("#HpInput").text("hp를 입력하지 않음!").css("color","red");	
    			hp.focus();
       		}  
    		
    		$("form").submit();
		}
	
	</script>	
</body>
</html>

