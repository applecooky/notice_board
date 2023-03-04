<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 회원가입</title>
<script type="text/javascript" src="js/jquery-1.8.1.min.js"></script>				
<script type="text/javascript" src="js/common.js"></script>		
<link rel="stylesheet" href="css/layout.css">
<script type="text/javascript">
	function doInsert(){
		if(insert.id.value==''){
			alert("아이디를 입력해주세요");
			return;
		}
		if(insert.password.value==''){
			alert("비밀번호를 입력해주세요");
			return;
		}
		if(insert.password_confirm.value==''){
			alert("비밀번호 확인란을 입력해주세요");
			return;
		}
		if(insert.password.value!=insert.password_confirm.value){
			alert("비밀번호와 확인란을 확인해주세요");
			return;
		}
		
		
		insert.method="post";
		insert.action="notice_board_insert";
		insert.submit();
	}
	function idCheck(){
		
	 $.ajax({
				type : "POST",
				url : "notice_idCheck",
				data: "id="+insert.id.value,
				dataType : "text",
				error : function(){
					alert('통신실패!!!!!');
				},
				success : function(data){
					
				//	alert(data);
					var result = $.trim(data);
					$("#idResult").text(result);
					
					if(result =="사용불가"){
						$("#idResult").css("color","red");
					} else {
						//  사용가능   
						$("#idResult").css("color","blue");
					}
					}
				}); 
		
	
	}
</script>
</head>
<body>
	<div class="container_form">
		<div>
			<h2>게시판 회원가입</h2>
		</div>
		<form name="insert">
		<div>
			<table border="1">
				<tr>
					<th>id</th>
					<td>
						<input type="text" name="id" onkeyup="idCheck()">
						<span id="idResult"></span>
					</td>
				</tr>
				<tr>
					<th>password</th>
					<td>
						<input type="text" name="password">
					</td>
				</tr>
				<tr>
					<th>password_check</th>
					<td>
						<input type="text" name="password_confirm">
					</td>
				</tr>
			</table>
		</div>
	
		<div class="insert">
 			<input type="button" onclick="history.back()" value="뒤로가기">
 			<button type="RESET">리셋</button>
 			<input type="button" onclick="doInsert()" value="회원가입">
			</div>
		</form>
			
	</div>
	


</body>
</html>