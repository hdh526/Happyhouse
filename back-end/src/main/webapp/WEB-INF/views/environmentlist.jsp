<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HappyHouse</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2 class="mb-4">주변 환경 정보</h2>
		<c:if test="${!empty environments}">
			<table border="1" style="border-collapse: collapse;">
				<tr>
					<th>업체</th>
					<th>인허가번호</th>
					<th>업종코드</th>
					<th>업종명</th>
					<th>지도점검일</th>
					<th>점검기관명</th>
					<th>소재지주소</th>
				</tr>
				<c:forEach var="environment" items="${environments}">
					<tr>
						<td>${environment.company}</td>
						<td>${environment.licenseNo}</td>
						<td>${environment.mapName}</td>
						<td>${environment.companyCode}</td>
						<td>${environment.checkDate}</td>
						<td>${environment.checkAgency}</td>
						<td>${environment.address}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty environments}">
			<p>검색된 환경 정보가 없습니다.</p>
		</c:if>
		<p>
			<button onclick="location.href='${root}'" class="btn btn-primary"
				type="button" data-bs-dismiss="modal">메인으로</button>
	</div>
</body>
</html>
