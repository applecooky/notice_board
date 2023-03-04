<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/common.css" rel="stylesheet">
<link href="css/layout.css" rel="stylesheet">
<meta charset="UTF-8">
<title>게시판 만들기</title>
<c:if test="${not empty msg}">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
<script type="text/javascript">
	function notice_write(){
		write.method="post";
		write.action="notice_board_write";
		write.submit();
		
	}
	function notice_insert(){
		write.method="post";
		write.action="notice_board_insertForm";
		write.submit();
		
	}
	function notice_login(){
		write.method="post";
		write.action="notice_board_loginForm";
		write.submit();
		
	}
	function noticeView(postNo){
		write.postno.value=postNo;
		write.method="post";
		write.action="notice_board_view";
		write.submit();
		
	}
	function notice_logout(){
		write.method="post";
		write.action="notice_board_logout";
		write.submit();
		
	}
	function doPaging(pageNo) {
		write.pageNo.value=pageNo;
		write.method="post";
		write.action="notice_board";
		write.submit();
	}
	function doSearch(){
		write.method="post";
		write.action="notice_board";
		write.submit();
		
	}
</script>
</head>
<body>
 <div class="container">
 	<div>
 		<h2>게시판 만들기</h2>
 	</div>
 	<div class="login">
 		<input type="button" value="회원가입" onclick="notice_insert()">
 			 <c:if test="${empty sessionId}">
 		<%-- <c:if test="${sessionId eq null}"> --%>
 			<input type="button" value="로그인" onclick="notice_login()">
 		</c:if>
 			<c:if test="${not empty sessionId}">
 		<%-- <c:if test="${sessionId ne null}"> --%>
 		<input type="button" value="로그아웃" onclick="notice_logout()">
 		</c:if>
 	</div>
 		<form name="write">
 			<input type="hidden" name="pageNo">
	 		<div>
				<select name="select">
					<option value="title" <c:if test="${select eq 'title'}">selected</c:if>>제목</option>
					<option value="id" <c:if test="${select eq 'id'}">selected</c:if>>작성자</option>
				</select>
		 		<input type="text" name="search" value="${search}">
		 		<input type="button" onclick="doSearch()" value="검색">
		 	</div>
				<input name="postno" type="hidden">
				<input name="select" type="hidden">
				<input name="serach" type="hidden">
		</form>
 		<table border="1">
 			<colgroup>
 				<col width="20%">
 				<col width="30%">
 				<col width="20%">
 				<col width="15%">
 				<col width="15%">
 			</colgroup>
 			<tr>
 				<th>게시글 번호</th>
 				<th>제목</th>
 				<th>작성자</th>
 				<th>조회수</th>
 				<th>작성일</th>
 			</tr>
 			
 		<c:forEach items="${dtos}" var="dto">
 			<tr>
 				<td>${dto.getPostno()}</td>
 				<td><a href="javascript:noticeView('${dto.getPostno()}')">${dto.getTitle()}</a></td>
 				<td>${dto.getId()}</td>
 				<td>${dto.getHit()}</td>
 				<td>${dto.getReg_date()}</td>
 			</tr>
 		</c:forEach>
 		
 		
 		</table>
	 	<div>
	 		<div class="paging">
	 			<c:forEach begin="1" end="${Paging}">
					<c:set var="paging" value="${paging+1}"></c:set>
		 			<a href="javascript:doPaging('${paging}')">${paging}</a>
		 			</c:forEach>
	 		</div>
		 	<div class="write">
		 		<input type="button" value="글쓰기" onclick="notice_write()">
		 	</div>
	 	</div>	
 </div>
</body>
</html>