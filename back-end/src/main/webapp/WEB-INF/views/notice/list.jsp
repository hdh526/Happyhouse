<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>HappyHouse</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<c:if test="${empty userinfo}">
	<script>
	alert("로그인 사용자만 볼 수 있는 페이지입니다.");
	location.href = "${root}/user?action=login";
	</script>
</c:if>
<c:if test="${!empty userinfo}">
    <style>
        mark.sky {
            background: linear-gradient(to top, #54fff9 20%, transparent 30%);
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
        	$("#mvRegisterBtn").click(function () {
                location.href = "${root}/article?act=mvregister";
            });
        	
        	$(".page-item").click(function () {
               	$("#pg").val($(this).attr("data-pg"));
               	$("#pageForm").attr("action", "${root}/article").submit();
            });
        });
        
        function deleteArticle(no) {
        	if(confirm("삭제하시겠습니까?")) {
        		location.href = "${root}/article?act=delete&articleno=" + no;
        	}
        }
    </script>
    <form name="pageForm" id="pageForm">
    	<input type="hidden" name="act" id="act" value="list" />
    	<input type="hidden" name="pg" id="pg" value="" />
    	<input type="hidden" name="key" id="key" value="${key}" />
    	<input type="hidden" name="word" id="word" value="${word}" />
    </form>

    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
        		<div class="col-lg-12 text-right mb-2">
					<strong>${userinfo.name}</strong>님 환영합니다.
					<a href="${root}/user?action=logout">로그아웃</a>
				</div>
            <h2 class="p-3 mb-3 shadow bg-light"><mark class="sky">공지사항 목록</mark></h2>
            <div class="m-3 text-right">
                <button type="button" id="mvRegisterBtn" class="btn btn-link">글작성</button>
                <button onclick="location.href='${root}/index.jsp'" type="button" id="mvRegisterBtn" class="btn btn-link">메인으로</button>
            </div>
            <div class="m-3 row justify-content-end">
            	<form class="form-inline" action="${root}/article">
	            	<input type="hidden" name="act" value="list">
	            	<input type="hidden" name="pg" value="1">
	            	<select name="key" class="form-control">
	            		<option value="id"> 아이디
	            		<option value="subject"> 제목
	            		<option value="articleno"> 글번호
	            	</select>
	            	<input type="text" class="ml-1 form-control" name="word">
	            	<button type="submit" class="ml-1 btn btn-outline-primary">검색</button>
            	</form>
            </div>
	<c:if test="${!empty articles}">    
		<c:forEach var="article" items="${articles}">
            <table class="table table-active text-left">
                <tbody>
                    <tr class="table-info">
                        <td>작성자 : ${article.name}</td>
                        <td class="text-right">작성일 : ${article.regTime}</td>
                    </tr>
                    <tr>
                        <td colspan="2" class="table-danger">
                            <strong>${article.articleNo}. ${article.subject}</strong>
                           <c:if test="${userinfo.id eq article.id}">
                            <div class="text-right">
                            	<a href="${root}/article?act=mvmodify&articleno=${article.articleNo}">수정</a>
                            	<a href="javascript:deleteArticle(${article.articleNo});">삭제</a>
                            </div>
                            </c:if> 
                        </td>
                    </tr>
                    <tr>
                        <td class="p-4" colspan="2">${article.content}</td>
                    </tr>
                </tbody>
            </table>
		</c:forEach>
		${navi.navigator}
	</c:if>  
	<c:if test="${empty articles}">    
			<table class="table table-active text-left">
                    <tr class="table-info">
                        <td colspan="2">작성한 글이 없습니다.</td>
                    </tr>
            </table>
	</c:if>       
        </div>
    </div>
</c:if>
</body>
</html>
