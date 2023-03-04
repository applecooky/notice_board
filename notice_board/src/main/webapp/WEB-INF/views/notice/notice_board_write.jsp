<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>       
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/layout.css">
<meta charset="UTF-8">
<title>게시판 등록</title>
<c:if test="${empty sessionId}">
		<script type="text/javascript">
			alert('${msg}');
			location.href="notice_board"
		</script>
	</c:if>
<script type="text/javascript">

	function postInsert(){
		
		write.method="post";
		write.action="notice_board_save"
		write.submit();
		
	}
</script>
</head>
<body>
<div class="insert_container">
 	<div>
 		<h2>게시판 만들기</h2>
 	</div>
 	<div>
 		<form name="write">
 		<table border="1">
 			<colgroup>
 				<col width="20%">
 				<col width="30%">
 				<col width="10%">
 				<col width="20%">
 			</colgroup>
 			<tr>
 				<th>제목</th>
 				<td colspan="3">
 					<input type="text" name="title" style="width: 800px;">
 				</td>
 			</tr>
 			<tr>
 				<th>내용</th>
 				<td colspan="3" class="content">
	 				<textarea name="content" style="width: 930px; height: 308px;" ></textarea>
 				</td>
 			</tr>
 			<tr>
 				<th>등록일</th>
 				<td>
 					${reg_date}
 					<input type="hidden" name="reg_date" value="${reg_date2}">
 				</td>
 				<th>
 					작성자
 				</th>
 				<td>
 					${sessionId}
 					<input type="hidden" name="id" value="${sessionId}">
 				</td>
 			</tr>
 		</table>
 		
 		<div class="insert">
 			<input type="reset" value="RESET">
 			<input type="button" onclick="postInsert()" value="등록">
 		</div>
 		</form>
 	</div>
 	
</div>

</body>
</html>