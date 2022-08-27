<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
		<meta name="description" content="" />
		<meta name="author" content="" />
		<title>회원정보</title>
		<!-- Favicon-->
		<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		<!-- Font Awesome icons (free version)-->
		<script
			src="https://use.fontawesome.com/releases/v5.15.4/js/all.js"
			crossorigin="anonymous"
		></script>
		<!-- Google fonts-->
		<link
			href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
			rel="stylesheet"
			type="text/css"
		/>
		<link
			href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700"
			rel="stylesheet"
			type="text/css"
		/>
		<!-- Core theme CSS (includes Bootstrap)-->
		<link href="/css/styles.css" rel="stylesheet" />
		<script>
        $(document).ready(function () {
            $("#infoupdate").click(function () {
            	$("#infoform").attr("action", "${root}/user/update").submit();
            });
        });
    </script>
	</head>
	<body id="page-top">
		<!-- Navigation-->
		<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
			<div class="container">
				<a class="navbar-brand" href="${root}/"
					><img src="/assets/img/home.png" alt="..."
				/></a>
				<button
					class="navbar-toggler"
					type="button"
					data-bs-toggle="collapse"
					data-bs-target="#navbarResponsive"
					aria-controls="navbarResponsive"
					aria-expanded="false"
					aria-label="Toggle navigation"
				>
					Menu
					<i class="fas fa-bars ms-1"></i>
				</button>
				<div class="collapse navbar-collapse" id="navbarResponsive">
					<ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
			            <li class="nav-item"><a class="nav-link" href="${root}/index.jsp#mapService">지도</a></li>
			            <li class="nav-item"><a class="nav-link" href="${root}/index.jsp#realPrice">실거래가 조회</a></li>
			            <li class="nav-item"><a class="nav-link" href="${root}/index.jsp#realEstate">Notice Board</a></li>
			            <li class="nav-item" style="display: block" id="logout">
			              <a class="nav-link" href="${root}/user/logout">로그아웃</a>
            			</li>
					</ul>
				</div>
			</div>
		</nav>
        <style>
            body {
              min-height: 100vh;
              background: -webkit-gradient(linear, left bottom, right top, from(#92b5db), to(#1d466c));
              background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
              background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
              background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
              background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%);
            }
            .input-form {
              max-width: 680px;
              margin-top: 80px;
              padding: 40px;
              background: #fff;
              -webkit-border-radius: 10px;
              -moz-border-radius: 10px;
              border-radius: 10px;
              -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
              -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
              box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
            }
        </style>
        </head>
        <body>
          <div class="container">
            <div class="input-form-backgroud row">
              <div class="input-form col-md-12 mx-auto">
                <h4 class="mb-3">회원 정보 확인</h4>
                <form id="infoform" class="mb-3" method="post" action="">
               		<input type="hidden" name="action" id="action" value="update">	
                  <div class="mb-3">
                    <label for="id">아이디</label>
                    <span class="text-danger small">*</span>
                    <input type="text" class="form-control" name="id" id="id" value="${userinfo.id}">
                  </div>
                  <div class="mb-3">
                    <label for="password">비밀번호</label>
                    <span class="text-danger small">*</span>
                    <input type="text" class="form-control" name="password" id="password" value="${userinfo.password}" >
                  </div>
                  <div class="mb-3">
                    <label for="name">이름</label>
                    <span class="text-danger small">*</span>
                    <input type="text" class="form-control" name="name" id="name" value="${userinfo.name}" >
                  </div>
                  <div class="mb-3">
                    <label for="country">Country</label>
                    <input type="text" class="form-control" name="country" id="country" value="${userinfo.country}" >
                  </div>
                  <div class="mb-3">
                    <label for="city">City</label>
                    <input type="text" class="form-control" name="city" id="city" value="${userinfo.city}" >
                  </div>
                  <div class="mb-3">
                    <label for="dong">Dong</label>
                    <input type="text" class="form-control" name="dong" id="dong" value="${userinfo.dong}" >
                  </div>
                  <div class="mb-3">
                    <label for="phone">전화번호</label>
                    <input type="text" class="form-control" name="phone" id="phone" value="${userinfo.phone}" >
                  </div>
                  <hr class="mb-4">
                  <div class="form-group text-center">
	                  <button onclick="location.href='${root}/'" class="btn btn-outline-primary" type="button">확인</button>
	                  <button id="infoupdate" class="btn btn-outline-primary" type="submit">수정</button>
	                  <button onclick="location.href='${root}/user/delete?id=${userinfo.id}'" class="btn btn-outline-danger" type="button">회원탈퇴</button>
                  </div>
            	</form>
            </div>
          </div>
        </div>

        <!-- Bootstrap core JS-->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
		<!-- Core theme JS-->
		<script src="js/scripts.js"></script>
		<script src="js/info.js"></script>
		<script src="js/regist.js"></script>
	</body>
</html>
