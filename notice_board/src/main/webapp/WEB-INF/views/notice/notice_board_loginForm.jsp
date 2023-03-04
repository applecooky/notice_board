<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/layout.css">
<title>게시판 로그인</title>
	<c:if test="${not empty msg}">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
<script type="text/javascript">
	function doLogin() {
		
		if(login.id.value==''){
			alert("아이디를 입력해주세요");
			login.id.focus();
			return;
		}
		if(login.password.value==''){
			alert("비밀번호를 입력해주세요");
			login.password.focus();
			return;
		}
		
		login.method="post";
		login.action="notice_board_login";
		login.submit();
	}
</script>
</head>
<body>
	<div class="container_form">
		<div>
			<h2>게시판 로그인</h2>
		</div>
				<form name="login">
			<div>
				<table border="1">
					<tr>
						<th>id</th>
						<td>
							<input type="text" name="id">
						</td>
					</tr>
					<tr>
						<th>password</th>
						<td>
							<input type="text" name="password">
						</td>
					</tr>
				</table>
		</div>
		
		<div class="insert">
			<input type="button" onclick="history.back()" value="뒤로가기">
 			<input type="reset" value="RESET">
 			<input type="button" onclick="doLogin()" value="로그인">
 		</div>
				</form>
			
	</div>
	
	
</body>
</html>