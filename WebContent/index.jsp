<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>


<script>
	location.href="http://localhost:8090/CarProject/car/Main";
</script>


<%--
실행 안된다면 확인해볼 것:

1.index.jsp의 포트번호 및 프로젝트 이름
	*몇몇 파일은 포트번호 지정 되어 있기에 나중에 동적으로 변경 필요하다 판단

3.Tomcat Server의 context.xml에 아래 내용이 추가되었는지
   	
<Resource	
	name="jdbc/oracle"
	auth="Container"
	type="javax.sql.DataSource"
	driverClassName="oracle.jdbc.OracleDriver"
	url="jdbc:oracle:thin:@localhost:1521:XE"
	username="carproject"
	password="carproject"
	maxActive="50"
	maxWait="-1"
/>	

4.Tomcat Server의 server.xml에 아래 내용이 추가되었는지
  
  <Context {프로젝트 정보}>
  <Resource	
	name="jdbc/oracle"
	auth="Container"
	type="javax.sql.DataSource"
	driverClassName="oracle.jdbc.OracleDriver"
	url="jdbc:oracle:thin:@localhost:1521:XE"
	username="carproject"
	password="carproject"
	maxActive="50"
	maxWait="-1"
	/>	
  </Context>

--%>