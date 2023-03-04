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

	function postUpdate(postno){
		update.postNo.value=postno;
		update.method="post";
		update.action="notice_board_update"
		update.submit();
		
	}
</script>
</head>
<body>
<div class="insert_container">
 	<div>
 		<h2>게시판 만들기</h2>
 	</div>
 	<div>
 		<form name="update">
 			<input type="hidden" name="postNo" value="${postNo}">
 		<table border="1">
 			<tr>
 				<th>제목</th>
 				<td colspan="3">
 					<input type="text" name="title" value="${title}" style="width: 800px;">
 				</td>
 			</tr>
 			<tr>
 				<th>내용</th>
 				<td colspan="3" class="content">
	 				<textarea name="content" style="width: 930px; height: 308px;" >${content}</textarea>
 				</td>
 			</tr>
 			<tr>
 				<th>수정일</th>
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
 			<input type="button" onclick="history.back()" value="뒤로가기">
 			<input type="reset" value="리셋">
 			<input type="button" onclick="postUpdate('${postNo}')" value="등록">
 		</div>
 		</form>
 	</div>
 	
</div>

</body>
</html>