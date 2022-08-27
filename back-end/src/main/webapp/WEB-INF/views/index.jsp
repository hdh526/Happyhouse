<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Happy House</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Font Awesome icons (free version)-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js"
	crossorigin="anonymous"></script>
<!-- Google fonts-->
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700"
	rel="stylesheet" type="text/css" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css/styles.css" rel="stylesheet" />
</head>
<body id="page-top">
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
		<div class="container">
			<a class="navbar-brand" href="${root}#page-top"><img
				src="assets/img/home.png" alt="..." /></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
				aria-controls="navbarResponsive" aria-expanded="false"
				aria-label="Toggle navigation">
				Menu <i class="fas fa-bars ms-1"></i>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
					<li class="nav-item"><a class="nav-link" href="#mapService">지도</a></li>
					<li class="nav-item"><a class="nav-link" href="#realPrice">실거래가
							조회</a></li>
					<li class="nav-item"><a class="nav-link" href="#realEstate">Notice Board
							</a></li>
					<c:if test="${empty userinfo}">
						<li class="nav-item" id="nav-register"><a class="nav-link"
							href="${root}/user/register">회원가입</a></li>
						<li class="nav-item" id="login"><a class="nav-link"
							data-bs-toggle="modal" href="#loginModal">로그인</a></li>
					</c:if>
					<c:if test="${!empty userinfo}">
						<li class="nav-item" id="client"><a class="nav-link"
							href="${root}/user/view">회원정보</a></li>
						<li class="nav-item" id="logout"><a class="nav-link"
							href="${root}/user/logout">로그아웃</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
	<!-- 상단 페이지-->
	<header class="masthead">
		<div class="container">
			<div class="masthead-heading text-uppercase">Happy House</div>
			<!-- <div class="masthead-subheading">6 Team</div> -->
			<a class="btn btn-primary btn-xl text-uppercase" href="#realPrice">아파트 실거래가
				조회</a>
		</div>
	</header>
	<!-- 지도-->
	<section class="page-section" id="mapService">
		<div class="container">
			<div class="text-center">
				<h2 class="section-heading text-uppercase">지도</h2>
				<h3 class="section-subheading text-muted">Current location</h3>
			</div>
			<div id="map" style="width: 100%; height: 500px"></div>
			<script type="text/javascript"
				src="//dapi.kakao.com/v2/maps/sdk.js?appkey=52667e121100ad19e6dd0f3331ace02c"></script>
			<script>
				var map;
				$(function() {
					var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
					var options = { //지도를 생성할 때 필요한 기본 옵션
						center : new kakao.maps.LatLng(37.501309,
								127.039581), //지도의 중심좌표.
						level : 3
					//지도의 레벨(확대, 축소 정도)
					};
					map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

					// 마커가 표시될 위치입니다 
					var markerPosition = new kakao.maps.LatLng(
							37.501309, 127.039581);

					// 마커를 생성합니다
					var marker = new kakao.maps.Marker({
						position : markerPosition,
						title : "",
					});

					// 마커가 지도 위에 표시되도록 설정합니다
					marker.setMap(map);

					// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
					// marker.setMap(null);

					// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
					if (navigator.geolocation) {

						// GeoLocation을 이용해서 접속 위치를 얻어옵니다
						navigator.geolocation
								.getCurrentPosition(function(position) {

									var lat = position.coords.latitude, // 위도
									lon = position.coords.longitude; // 경도

									var locPosition = new kakao.maps.LatLng(
											lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
									message = '<div style="font-size:15px">HAPPY HOUSE!</div>'; // 인포윈도우에 표시될 내용입니다

									// 마커와 인포윈도우를 표시합니다
									displayMarker(locPosition, message);
									
									var locs = {
											lat: lat,
											lon: lon,
									};
									localStorage.setItem("location", JSON.stringify(locs));

								});

					} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

						var lat = 37.501309, // 위도
						lon = 127.039581; // 경도
						
						var locPosition = new kakao.maps.LatLng(lat,
								lon), message = 'geolocation을 사용할수 없어요..'

						displayMarker(locPosition, message);
						
						var locs = {
								lat: lat,
								lon: lon,
						};
						localStorage.setItem("location", JSON.stringify(locs));
					}

					// 지도에 마커와 인포윈도우를 표시하는 함수입니다
					function displayMarker(locPosition, message) {

						// 마커를 생성합니다
						var marker = new kakao.maps.Marker({
							map : map,
							position : locPosition
						});

						var iwContent = message, // 인포윈도우에 표시할 내용
						iwRemoveable = true;

						// 인포윈도우를 생성합니다
						var infowindow = new kakao.maps.InfoWindow({
							content : iwContent,
							removable : iwRemoveable
						});

						// 인포윈도우를 마커위에 표시합니다 
						infowindow.open(map, marker);

						// 지도 중심좌표를 접속위치로 변경합니다
						map.setCenter(locPosition);
					}
				});
				//$(document).ready(function () {
				//	let requestData = {
				//		serviceKey:
				//			'eU5u3SoVLrATuYcq/+Ps5dS+zsoeDtX1X8pfsqXb6m4BFT3KmXMjUjZUsqN9tLzWBp/Q2D6Ok/Q8zDKpqjWy0g==',
				//		pageNo: 1,
				//		numOfRows: 50,
				//	};
				//	$.ajax({
				//		url: 'http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService',
				//		type: 'GET',
				//		data: requestData,
				//		dataType: 'xml',
				//		success: function (response) {
				//			makeMarker(response);
				//		},
				//	});
				//});
				function makeMarker(data) {
					// 마커를 표시할 위치와 title 객체 배열입니다
					var positions = [];
					$(data).find('item').each(
							function() {
								positions.push({
									content : $(this).find('yadmNm').text(),
									title : $(this).find('yadmNm').text(),
									latlng : new kakao.maps.LatLng($(this)
											.find('YPosWgs84').text(), $(this)
											.find('XPosWgs84').text()),
								});
							});
					// 마커 이미지의 이미지 주소입니다
					var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
					for (var i = 0; i < positions.length; i++) {
						// 마커 이미지의 이미지 크기 입니다
						var imageSize = new kakao.maps.Size(24, 35);
						// 마커 이미지를 생성합니다
						var markerImage = new kakao.maps.MarkerImage(imageSrc,
								imageSize);
						// 마커를 생성합니다
						var marker = new kakao.maps.Marker({
							map : map, // 마커를 표시할 지도
							position : positions[i].latlng, // 마커를 표시할 위치
							title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
							image : markerImage, // 마커 이미지
						});
						// 마커에 표시할 인포윈도우를 생성합니다
						var infowindow = new kakao.maps.InfoWindow({
							content : positions[i].content, // 인포윈도우에 표시할 내용
						});
						// 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
						// 이벤트 리스너로는 클로저를 만들어 등록합니다
						// for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
						kakao.maps.event.addListener(marker, 'mouseover',
								makeOverListener(map, marker, infowindow));
						kakao.maps.event.addListener(marker, 'mouseout',
								makeOutListener(infowindow));
						kakao.maps.event.addListener(marker, 'click',
								makeClickListener(map, marker, infowindow));

						var moveLocation = new kakao.maps.LatLng(
								positions[0].latlng.Ma, positions[0].latlng.La);
						map.setLevel(6);
						map.setCenter(moveLocation);
					}
					// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
					function makeOverListener(map, marker, infowindow) {
						return function() {
							infowindow.open(map, marker);
						};
					}
					// 인포윈도우를 닫는 클로저를 만드는 함수입니다
					function makeOutListener(infowindow) {
						return function() {
							infowindow.close();
						};
					}
					function makeClickListener(map, marker, infowindow) {
						return function() {
							let pos = marker.getPosition();
							map.setLevel(5);
							map.panTo(pos);
						};
					}
				}
			</script>
		</div>
	</section>
	<!-- 실거래가 조회-->
		<section class="page-section bg-light" id="realPrice">
			<div class="container">
				<div class="text-center mb-5">
					<h2 class="section-heading text-uppercase">아파트 실거래가 조회</h2>
				</div>
				<div class="text-center">
					<div class="mb-4">
						<!-- <button
							type="button"
							class="btn btn-primary p-3"
							data-bs-toggle="modal"
							data-bs-target="#searchModal"
						>
							검색하기
						</button> -->
						<button
							type="button"
							class="btn btn-primary p-3"
							onclick="location.href='${root}/search'">
							Search
						</button>
					</div>
					<c:if test="${!empty prices}">
						<div id="result" class="mt-5">
							<h3 id="searchResultTitle"></h3>
							<div class="table-reponsive">
								<table class="table">
									<tr>
										<th>아파트</th>
										<th>전용면적</th>
										<th>거래금액</th>
										<th>층</th>
										<th>시/도</th>
										<th>구/군</th>
										<th>동</th>
										<th>거래년도</th>
									</tr>
									<tbody>
										<c:forEach items="${prices}" var="price">
											<tr>
												<td>${price.aptName}</td>
												<td>${price.area}</td>
												<td>${price.dealAmount}</td>
												<td>${price.floor}</td>
												<td>${price.sidoName}</td>
												<td>${price.gugunName}</td>
												<td>${price.dongName}</td>
												<td>${price.dealYear}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</section>
		<!-- 부동산 정보-->
		<section class="page-section" id="realEstate">
			<div class="container">
				<div class="text-center">
					<h2 class="section-heading text-uppercase">Notice Board</h2>
					<br>
				</div>
				<div class="row">
					<div class="col-lg-4 col-sm-6 mb-4">
						<!-- 부동산정보 - 주변환경-->
						<div class="portfolio-item">
							<a class="portfolio-link" data-bs-toggle="modal" href="#ensearch">
								<div class="portfolio-hover">
									<div class="portfolio-hover-content"><i class="fas fa-plus fa-3x"></i></div>
								</div>
								<img class="img-fluid" src="assets/img/portfolio/1.png" width="300" height="300" alt="..." />
							</a>
							<div class="portfolio-caption">
								<br>
								<div class="portfolio-caption-heading" style="padding: 0px 0px 0px 160px" >주변 환경 정보</div>
								<!-- <div class="portfolio-caption-subheading text-muted" style="padding: 0px 0px 0px 160px">Environment</div> -->
							</div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-6 mb-4">
						<!-- 부동산정보 - 공지사항-->
						<div class="portfolio-item">
							<a class="portfolio-link" data-bs-toggle="modal" href="#newsModal">
								<div class="portfolio-hover">
									<div class="portfolio-hover-content"><i class="fas fa-plus fa-3x"></i></div>
								</div>
								<img class="img-fluid" src="assets/img/portfolio/2.png" width="300" height="300" alt="..." />
							</a>
							<div class="portfolio-caption">
								<br>
								<div class="portfolio-caption-heading" style="padding: 0px 0px 0px 185px">공지사항</div>
								<!-- <div class="portfolio-caption-subheading text-muted" style="padding: 0px 0px 0px 185px">NOTICE</div> -->
							</div>
						</div>
					</div>
					<div class="col-lg-4 col-sm-6 mb-4">
						<!-- 부동산정보 - 주변병원-->
						<div class="portfolio-item">
							<a
								class="portfolio-link"
								data-bs-toggle="modal"
								href="#hospitalModal"
								id="hospitalModalLink"
							>
								<div class="portfolio-hover">
									<div class="portfolio-hover-content"><i class="fas fa-plus fa-3x"></i></div>
								</div>
								<img class="img-fluid" src="assets/img/portfolio/3.png" width="300" height="300" alt="..." />
							</a>
							<div class="portfolio-caption">
								<br>
								<div class="portfolio-caption-heading" style="padding: 0px 0px 0px 140px">주변 병원 및 선별진료소</div>
								<!-- <div class="portfolio-caption-subheading text-muted" style="padding: 0px 0px 0px 160px">Hospital</div> -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- Footer-->
		<footer class="footer py-4 bg-light">
			<div class="container">
				<div class="row align-items-center">
					<div class="col-lg-4 text-lg-start">&copy; SSAFY DAEJEON 5Class 6Team, 2022</div>
					<div class="col-lg-4 my-3 my-lg-0">
						<a
							class="btn btn-dark btn-social mx-2"
							href="https://lab.ssafy.com/ParkJYeon2808/happyhousealgo"
							target="_blank"
							><i class="fab fa-github"></i
						></a>
						
					</div>
					<div class="col-lg-4 text-lg-end">
						<a class="link-dark text-decoration-none me-3" href="#!"></a>
						<a class="link-dark text-decoration-none" href="#!"></a>
					</div>
				</div>
			</div>
		</footer>
		<!-- Modals-->
		<!-- 부동산정보 - 주변 환경 modal popup-->
		<div class="modal fade" id="ensearch" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content p-3">
					<div class="modal-header">
						<h5 class="modal-title">주변 환경 정보</h5>
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="container">
						<div class="mb-3">
						<p></p>
						<form method="post" action="environment?action=list">
							<label for="address" class="form-label">동을 입력해주세요.</label>
							<select class="form-control" id="address" name="address">
                            	<option value="all">전체</option>
	                            <option value="명륜1가">명륜1가</option>
                            	<option value="종로5가">종로5가</option>
                            	<option value="종로6가">종로6가</option>
                        	</select>
							<button class="btn btn-primary" type="submit" data-bs-dismiss="modal">검색</button>
						</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 부동산정보 - 공지사항 modal popup-->
		<div
			class="modal fade"
			id="newsModal"
			tabindex="-1"
			role="dialog"
			aria-hidden="true"
		>
			<div class="modal-dialog">
				<div class="modal-content p-3">
					<div class="modal-header">
						<h5 class="modal-title">공지사항</h5>
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div align="center">
						<c:if test="${empty userinfo}">
								<a href="${root}/register">회원가입</a>
								<a href="${root}/user/login">로그인</a>
								<a href="${root}/article?act=list&pg=1&key=&word=">공지사항 목록</a>	<p></p>
						</c:if>
						<c:if test="${!empty userinfo}">
						<p></p>
								<strong>${userinfo.name}(${userinfo.id})</strong>님 안녕하세요.
								<a href="${root}/user/logout">로그아웃</a><p></p>
								<a href="${root}/article?act=list&pg=1&key=&word=">공지사항 목록</a>	<p></p>
						</c:if>	
					</div>
					
				</div>
			</div>
		</div>
		<!-- 부동산정보 - 주변 병원 및 선별진료소 modal popup-->
		<div
			class="portfolio-modal modal fade"
			id="hospitalModal"
			tabindex="-1"
			role="dialog"
			aria-hidden="true"
		>
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="close-modal" data-bs-dismiss="modal">
						<img src="assets/img/close-icon.svg" alt="Close modal" />
					</div>
					<div class="container text-center">
						<h3>건강보험심사평가원_호흡기 진료 지정 의료기관 정보서비스</h3>
						<div class="mt-5" id="hospitalLoadingMsg">로딩 중입니다.</div>
						<div class="table-responsive mt-4">
							<table class="table">
								<tr>
									<th>요양기관명</th>
									<th>전화번호</th>
									<th>주소</th>
									<th>PCR가능여부</th>
									<th>위도</th>
									<th>경도</th>
								</tr>
								<tbody id="hospitalInfo"></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- LogIn modal popup-->
		<script>
	        $(document).ready(function () {
	            $("#loginBtn").click(function () {
	            	
	            	// 이용자 입력 값 저장 
					var uid = $("#id").val();
					var pwd = $("#password").val();
				 
					console.log(uid);
					console.log(pwd);
					console.log($("#rsaPublicKeyModulus").val());
					console.log($("#rsaPublicKeyExponent").val());
					
					// 서버로부터 수신한 공개키로 RSA 암호화 생성
					var rsa = new RSAKey();
					rsa.setPublic($("#rsaPublicKeyModulus").val(),$("#rsaPublicKeyExponent").val());
					
					// 이용자 입력 값 암호화 처리
					uid = rsa.encrypt(uid);
					pwd = rsa.encrypt(pwd); 
					
					console.log(uid);
					console.log(pwd);
					
					// 암호화 값이 보이지 않게 hidden 로그인 폼으로 데이터 전달 
					$("#securedUsername").val(uid);
					$("#securedPassword").val(pwd);
					console.log("A : " + $("#securedUsername").val());
					console.log("B : " + $("#securedPassword").val());
	  			    $("#securedLoginForm").submit();
					// location.href="/member/login.do";
				
					
	                /* if (!$("#id").val()) {
	                    alert("아이디 입력!!!");
	                    return;
	                } else if (!$("#password").val()) {
	                    alert("비밀번호 입력!!!");
	                    return;
	                } else {
	                    $("#loginform").attr("action", "${root}/user/login").submit();
	                } */
	                
	            });
	        });
	    </script>
	    
	    <input type="hidden" id="rsaPublicKeyModulus" value="${publicKeyModulus}" /> 
        <input type="hidden" id="rsaPublicKeyExponent" value="${publicKeyExponent}" />
        
		<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content p-3">
					<div class="modal-header">
						<h5 class="modal-title" id="loginTitle">Log In</h5>
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="container">
							<input type="hidden" name="action" id="action" value="login">
							<div class="form-group mb-4 mt-3" id="emailContainer">
								<label for="id" class="form-label" id="labelId">아이디</label> 
								<input type="text"
									class="form-control" id="id" name="id"
									placeholder="아이디입력...">
								<div id="idresult" class="mt-1"></div>
							</div>
							<div class="form-group mb-4" id="passwordContainer">
								<label for="password" class="form-label">비밀번호</label> 
								<input type="password"
									class="form-control" id="password" name="password"
									placeholder="비밀번호입력...">
							</div>
							<div class="form-group text-center">
								<button type="submit" id="loginBtn" data-bs-dismiss="modal"
									class="btn btn-success">로그인</button>
							</div>
						<form id="securedLoginForm" name="securedLoginForm" action="${root}/user/login" method="post" style="display: none;">
				            <input type="hidden" name="securedUsername" id="securedUsername" value="" />
				            <input type="hidden" name="securedPassword" id="securedPassword" value="" />
						</form> 
						
						<div class="form-check mb-3" id="remembermeContainer">
							<label class="form-check-label">
								<input class="form-check-input" type="checkbox" name="remember" /> Remember me
							</label>
						</div>
							<button type="button" class="btn btn-secondary" id="findPw">비밀번호 찾기</button>
							<button type="button" class="btn btn-secondary" style="display: none" id="findPw2">
								비밀번호 찾기
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Search modal popup-->
		<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content p-3">
					<div class="modal-header">
						<h5 class="modal-title">실거래가 검색하기</h5>
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="container">
						<div class="mb-3 mt-3">
							<label for="city1" class="form-label">도/광역시</label>
							<select class="form-control" id="city1" name="city1">
	                            <option value="Seoul">서울특별시</option>
	                            <option value="Gyeonggi">경기도</option>
	                            <option value="Gangwon">강원도</option>
                        	</select>
						</div>
						<div class="mb-3">
							<label for="city2" class="form-label">시/군/구</label>
							<select class="form-control" id="city2" name="city2">
	                            <option value="Jongro">종로</option>
                            	<option value="Yeoungdungpo">영등포</option>
                            	<option value="Suwon">수원</option>
                            	<option value="Yongin">용인</option>
                            	<option value="Wonju">원주</option>
                            	<option value="Chuncheon">춘천</option>
                        	</select>
						</div>
						<div class="mb-3">
							<div class="input-group-text d-flex justify-content-around">
								<label for="base" class="form-label">검색 기준을 선택해주세요.</label>
								<div><input type="radio" name="base" value="동" />동</div>
								<div><input type="radio" name="base" value="아파트" />아파트</div>
							</div>
						</div>
						<div class="mb-3">
							<label for="city3" class="form-label">동 또는 아파트</label>
							<select class="form-control" id="city3" name="city3">
                            	<option value=" ">전체</option>
	                            <option value="사직동">사직동</option>
                            	<option value="내수동">내수동</option>
                            	<option value="연건동">연건동</option>
                        	</select>
						</div>
						<button type="button" class="btn btn-primary" data-bs-dismiss="modal" id="getInfo">검색하기</button>
					</div>
				</div>
			</div>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
		<script src="js/scripts.js"></script>
		<script src="js/hospital.js"></script>
		<script src="js/realPrice.js"></script>
		<script src="js/findPassword.js"></script>
		
		<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script type="text/javascript" src="/js/jsbn.js"></script>
		<script type="text/javascript" src="/js/rsa.js"></script>
		<script type="text/javascript" src="/js/prng4.js"></script>
		<script type="text/javascript" src="/js/rng.js"></script>
	</body>
</html>
