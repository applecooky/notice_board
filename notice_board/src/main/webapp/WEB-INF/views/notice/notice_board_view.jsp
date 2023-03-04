<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${not empty msg}">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
<script type="text/javascript" src="js/jquery-1.8.1.min.js"></script>				
<script type="text/javascript" src="js/common.js"></script>		
<link rel="stylesheet" href="css/layout.css">
<meta charset="UTF-8">
<title>게시판 등록</title>

<script type="text/javascript">
	function postInsert(){

		write.method="post";
		write.action="notice_board_save";
		write.submit();
		
	}
	function notice_board(){
		write.method="post";
		write.action="notice_board";
		write.submit();
	}
	function notice_delete(){
		write.method="post";
		write.action="notice_delete";
		write.submit();
	}
	function notice_board_update(postno) {
		
		write.postNo.value=postno;
		write.method="post";
		write.action="notice_board_updateForm";
		write.submit();
	}
	//이왜안?
	function rpSave(){
		if(${sessionId == null}){
			alert('로그인 후 댓글을 달아주세요.');
		}else{
		$.ajax({
			type : "POST",
			url : "notice_reply_save",
			data: "content="+resave.content.value+"&postNo="+resave.postNo.value,
			dataType : "text",
			error : function(){
			//	alert('통신실패!!!!!');
				alert('내용을 입력해주세요.');
				resave.content.focus();
				
			},
			success : function(data){
				alert('댓글 등록되었습니다.');
				if(data==resave.postNo.value){
					location.reload();
				}
				}
				
			}); 
		}
		
	}
	
	function replyDelete(postno,postreno) {
		var ans = confirm("삭제하시겠습니까?");
			 if(ans){
			
		$.ajax({
			type : "POST",
			url : "notice_board_redelete",
			data: "postReNo="+postreno+"&postNo="+postno,
			dataType : "text",
			error : function(){
				alert('통신실패!!!!!');
				
			},
			success : function(data){
				
				location.reload();
			
				}
			}); 
			}
		
	}
	function replyUpdate(postno,postreno,content) {
		var ans = confirm("수정하시겠습니까?");
			 if(ans){
		var change = prompt("수정할 내용", content);
		$.ajax({
			type : "POST",
			url : "notice_board_reupdate",
			data: "postReNo="+postreno+"&postNo="+postno+"&content="+change,
			dataType : "text",
			error : function(){
				alert('통신실패!!!!!');
				
			},
			success : function(data){
				location.reload();
			
				}
			}); 
			}
		
	}
</script>
</head>
<body>
<div class="container">
 	<div>
 		<h2>게시판 내용</h2>
 	</div>
 	<div>
 		<form name="write">
 			<input type="hidden" value="${dto.getPostno()}" name="postNo">
 		<table border="1">
 			<tr>
 				<th>제목</th>
 				<td>
 					<input type="hidden" name="title" value="${dto.getTitle()}">${dto.getTitle()}
 				</td>
 				<th>조회수</th>
 				<td>
 					<input type="hidden" name="hit" value="${dto.getHit()}">${dto.getHit()}
 				</td>
 			</tr>
 			<tr>
 				<th>내용</th>
 				<td colspan="3">
 					<input type="hidden" name="content" value="${dto.getContent()}">${dto.getContent()}
 				</td>
 			</tr>
 			<tr>
 				<th>등록일</th>
 				<td>
 					${dto.getReg_date()}
 					<input type="hidden" name="reg_date" value="${dto.getReg_date()}">
 				</td>
 				<th>
 					작성자
 				</th>
 				<td>
 					${dto.getId()}
 					<input type="hidden" name="id" value="${dto.getId()}">
 				</td>
 			</tr>
 		</table>
 		</form>
 		
 		<div class="insert">
 			<input type="button" onclick="notice_board()" value="뒤로가기">
			<c:if test="${sessionId eq dto.getId()}">
 				<input type="button" onclick="notice_board_update('${dto.getPostno()}')" value="수정">
 				<input type="button" onclick="notice_delete()" value="삭제">
			</c:if>
 			
 		</div>
 	</div>
</div>
<div class="view_container">
	<form name="resave">
	<div style="text-align: right;">
		<input type="hidden" value="${dto.getPostno()}" name="postNo">
		<input type="text" name="content">
		<input type="button" onclick="rpSave()" value="댓글 등록">
	</div>
	</form>
</div>
<div class="reply_container">
 			<form name="reply">
 	<div>
 			<ul class="reply">
 				<c:forEach items="${redto}" var="redto">
	 				<li>
	 					<p>
	 						${redto.getId()}
	 						<span>${redto.getReg_date()}</span>
	 						<input type="hidden" value="${redto.getPostno()}" name="postNo">
	 						<input type="hidden" value="${redto.getPostreno()}" name="postReNo">
	 					</p>
	 					<p>
	 						${redto.getContent()}
	 						<c:if test="${sessionId eq redto.getId()}">
		 						<input type="button" onclick="replyDelete('${redto.getPostno()}','${redto.getPostreno()}')" value="x">
		 						<input type="button" onclick="replyUpdate('${redto.getPostno()}','${redto.getPostreno()}','${redto.getContent()}')" value="수정">
	 						</c:if>
	 					</p>
	 				</li>
 				</c:forEach>
 			</ul>
 	</div>
 			</form>
</div> 	


</body>
</html>