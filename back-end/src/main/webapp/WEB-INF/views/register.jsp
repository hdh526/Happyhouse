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
		<title>회원가입</title>
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

<!-- <style>
mark.orange {
	background: linear-gradient(to top, orange 20%, transparent 30%);
}
</style> -->

<script>
      $(document).ready(function () {
        	var isId = false;
        	// 아이디 중복검사
        	$("#id").keyup(function () {
        		var ckid = $("#id").val();
        		console.log(ckid.length)
        		if(ckid.length < 6 || ckid.length > 16) {
        			$("#idresult").text("아이디는 6자이상 16자이하입니다.").removeClass('text-primary').removeClass('text-danger').addClass('text-dark');
        			isId = false;
        		} else {
	                $.ajax({
	                	url: '${root}/user/idcheck',
	                	data: {'ckid': ckid},
	                  	type: 'GET',
	                  	dataType: 'text',
	                  	success: function (data, status) {
	                    	var cnt = parseInt(data);
	                    	if(cnt == 0) {
	                    		$("#idresult").text(ckid + "는 사용가능합니다.").removeClass('text-dark').removeClass('text-danger').addClass('text-primary');
	                    		isId = true;
	                    	} else {
	                    		$("#idresult").text(ckid + "는 사용할 수 없습니다.").removeClass('text-dark').removeClass('text-primary').addClass('text-danger');
	                    		isId = false;
	                    	}
	                  	}
					});
        		}
			});
        	
        	// 회원가입
            $("#registerBtn").click(function () {
                if (!$("#name").val()) {
                    alert("이름 입력!!!");
                    return;
                } else if (!isId) {
                    alert("아이디 확인!!!");
                    return;
                } else if (!$("#password").val()) {
                    alert("비밀번호 입력!!!");
                    return;
                } else if ($("#password").val() != $("#pwdcheck").val()) {
                    alert("비밀번호 확인!!!");
                    return;
                } else {
                    $("#memberform").attr("action", "${root}/user/regist").submit();
                }
            });

            // $('#zipcode').focusin(function () {
            //     $('#zipModal').modal();
            // });
        });
    </script>

</head>
<body id="page-top">
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
		<div class="container">
			<a class="navbar-brand" href="${root}/"><img
				src="/assets/img/home.png" alt="..." /></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
				aria-controls="navbarResponsive" aria-expanded="false"
				aria-label="Toggle navigation">
				Menu <i class="fas fa-bars ms-1"></i>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
					<li class="nav-item"><a class="nav-link"
						href="${root}/#mapService">지도</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${root}/#realPrice">실거래가 조회</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${root}/#realEstate">Notice Board</a></li>
					<li class="nav-item"><a class="nav-link" href="${root}/user/register">회원가입</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- Masthead-->

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
				 <h4 class="mb-3">회원가입</h4>
				<form id="memberform" class="mb-3" method="post" action="">
				<input type="hidden" name="action" id="action" value="register">
				<div class="mb-3">
					<label for="name">이름</label> 
					<span class="text-danger small">*</span>
					<input type="text"
						class="form-control" id="name" name="name"
						placeholder="이름입력...">
				</div>
				<div class="mb-3">
					<label for="id">아이디</label>
					<span class="text-danger small">*</span>
					<input type="text"
						class="form-control" id="id" name="id"
						placeholder="아이디입력...">
					<div id="idresult" class="mt-1"></div>
				</div>
				<div class="mb-3">
					<label for="password">비밀번호</label>
					<span class="text-danger small">*</span>
					<input type="password"
						class="form-control" id="password" name="password"
						placeholder="비밀번호입력...">
				</div>
				<div class="mb-3">
					<label for="pwdcheck">비밀번호재입력</label>
					<span class="text-danger small">*</span> 
					<input type="password"
						class="form-control" id="pwdcheck" name="pwdcheck"
						placeholder="비밀번호재입력...">
				</div>
				<div class="mb-3">
					<label for="emailid">이메일</label><br>
					<div id="email" class="custom-control-inline">
						<input type="text" class="form-control" id="emailid"
							name="emailid" placeholder="이메일아이디입력..." size="25"> @ <select
							class="form-control" id="emaildomain" name="emaildomain">
							<option value="ssafy.com">ssafy.com</option>
							<option value="naver.com">naver.com</option>
							<option value="kakao.com">kakao.com</option>
							<option value="google.com">google.com</option>
						</select>
					</div>
				</div>
				<div class="mb-3">
					<label for="phone">휴대폰</label> <input type="text"
						class="form-control" id="phone" name="phone"
						placeholder="휴대폰번호입력...">
				</div>
                <div class="mb-3">
                    <label for="country">Country</label>
                    <div id="country" class="custom-control-inline">
                        <select class="form-control" id="country" name="country">
                            <option value="Seoul">서울특별시</option>
                            <option value="Gangwon">강원도</option>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="city">City</label>
                    <div id="city" class="custom-control-inline">
                        <select class="form-control" id="city" name="city">
                            <option value="Jongro">종로</option>
                            <option value="Yeoungdungpo">영등포</option>
                            <option value="Wonju">원주</option>
                            <option value="Chuncheon">춘천</option>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="dong">Dong</label>
                    <div id="dong" class="custom-control-inline">
                        <select class="form-control" id="dong" name="dong">
                            <option value="Singil">신길동</option>
                            <option value="Bankok">반곡동</option>
                        </select>
                    </div>
                </div>
                <hr class="mb-4">
				<div class="form-group text-center">
					<button type="button" id="registerBtn"
						class="btn btn-outline-primary">회원가입</button>
					<button type="reset" class="btn btn-outline-danger">초기화</button>
				</div>
			</form>
			</div>
		</div>
	</div>
	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
	<script src="js/regist.js"></script>
</body>
</html>
