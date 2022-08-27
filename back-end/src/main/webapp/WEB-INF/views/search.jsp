<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
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
<script>
	$(document).ready(function() {
		//$("#searchBtn").click(function() {
			//$("#priceform").attr("action", "price?action=search").submit();
		//});
		$("#result").css("display", "none");
			
		$(document).on("click", "#searchBtn", function(){
			var value = $(".radio-value:checked").val();
			var sort = $(".radio-sort:checked").val();
			var base = $(".radio-base:checked").val();
			var location = JSON.parse(localStorage.getItem("location"));
			if (value == "동") {
				
				$.get("${root}" + "/address/apt"
						,
						{
						dong: $("#city3").val(),
						lat : location['lat'],
						lon : location['lon'],
						sort: sort,
						base: base
						},
						function(data, status){
							$("#result").css("display", "block");
							$("#searchResult").empty();
							console.log(data[0].aptName);
							$.each(data, function(index, vo) {
								let str = `
									<tr class=index>
									<td>` + vo.aptCode + `</td>
									<td>` + vo.aptName + `</td>
									<td>` + vo.sidoName + ` ` + vo.gugunName + ` ` + vo.dongName + ` ` + vo.jibun + `</td>
									<td>` + vo.buildYear + `</td>
									<td>` + vo.recentPrice + `</td>
									</tr>`;
								$("#searchResult").append(str);
							});
						displayMarkers(data);
						}
						,"json"
				);
			} else if (value == "아파트") {
				$.get("${root}" + "/address/aptN"
						,{city: $("#city2").val(),
						  apt: $("#city4").val(),
						  lat : location['lat'],
						  lon : location['lon'],
						  sort : sort,
						  base : base
						},
						function(data, status){
							$("#result").css("display", "block");
							$("#searchResult").empty();
							console.log(data[0].aptName);
							$.each(data, function(index, vo) {
								let str = `
									<tr class=index>
									<td>` + vo.aptCode + `</td>
									<td>` + vo.aptName + `</td>
									<td>` + vo.sidoName + ` ` + vo.gugunName + ` ` + vo.dongName + ` ` + vo.jibun + `</td>
									<td>` + vo.buildYear + `</td>
									<td>` + vo.recentPrice + `</td>
									</tr>`;
								$("#searchResult").append(str);
							});
						displayMarkers(data);
						}
						,"json"
				);
			}
		});
	
		$(".radio-value").on("change", function() {
			var value = $(".radio-value:checked").val();
			if (value == "동") {
				$("#city3").css("display", "block");
				$("#city4").css("display", "none");
			} else if (value == "아파트") {
				$("#city3").css("display", "none");
				$("#city4").css("display", "block");
			}
		});

		
		$.get("${root}" + "/address/sido"
				,function(data, status){
					$.each(data, function(index, vo) {
						$("#sido").append("<option value='"+vo.sidoCode+"'>"+vo.sidoName+"</option>");
					});
				}
				, "json"
			);
		$.get("${root}" + "/address/sido"
				,function(data, status){
					$.each(data, function(index, vo) {
						$("#city1").append("<option value='"+vo.sidoCode+"'>"+vo.sidoName+"</option>");
					});
				}
				, "json"
			);
	});
	
	$(document).on("change", "#sido", function() {
		$.get("${root}" + "/address/gugun"
				,{sido: $("#sido").val()}
				,function(data, status){
					$("#gugun").empty();
					$("#gugun").append('<option value="0">선택</option>');
					$.each(data, function(index, vo) {
						$("#gugun").append("<option value='"+vo.gugunCode+"'>"+vo.gugunName+"</option>");
					});
				}
				, "json"
		);
	});
	
	$(document).on("change", "#city1", function() {
		$.get("${root}" + "/address/gugun"
				,{sido: $("#city1").val()}
				,function(data, status){
					$("#city2").empty();
					$("#city2").append('<option value="0">선택</option>');
					$.each(data, function(index, vo) {
						$("#city2").append("<option value='"+vo.gugunCode+"'>"+vo.gugunName+"</option>");
					});
				}
				, "json"
		);
	});
	
	$(document).on("change", "#gugun", function() {
		$.get("${root}" + "/address/dong"
				,{gugun: $("#gugun").val()}
				,function(data, status){
					$("#dong").empty();
					$("#dong").append('<option value="0">선택</option>');
					$.each(data, function(index, vo) {
						$("#dong").append("<option value='"+vo.dongCode+"'>"+vo.dongName+"</option>");
					});
				}
				, "json"
		);
	});
	
	$(document).on("change", "#city2", function() {
		$.get("${root}" + "/address/dong"
				,{gugun: $("#city2").val()}
				,function(data, status){
					$("#city3").empty();
					$("#city3").append('<option value="0">선택</option>');
					$.each(data, function(index, vo) {
						$("#city3").append("<option value='"+vo.dongCode+"'>"+vo.dongName+"</option>");
					});
				}
				, "json"
		);
	});
	
	$(document).on("change", "#dong", function() {
		$.get("${root}" + "/address/apt"
				,{dong: $("#dong").val()}
				,function(data, status){
					$("tbody").empty();
					$.each(data, function(index, vo) {
						let str = `
							<tr class="${colorArr[index%3]}">
							<td>${vo.aptCode}</td>
							<td>${vo.aptName}</td>
							<td>${vo.sidoName} ${vo.gugunName} ${vo.dongName} ${vo.jibun}</td>
							<td>${vo.buildYear}</td>
							<td>${vo.recentPrice}</td>
						`;
						$("tbody").append(str);
					});
					displayMarkers(data);
				}
				, "json"
		);
	});
	
</script>
</head>
<body>
	<div class="modal-header">
		<h5 class="modal-title">실거래가 검색하기</h5>
		<button type="button" class="close" onclick="location.href='${root}/'"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	<div class="container">
		<form id="priceform" class="text-left mb-3" method="post" action="">
			<input type="hidden" name="action" id="action" value="search">
			<div class="mb-3 mt-3">
				<label for="city1" class="form-label">도/광역시</label> <select
					class="form-control" id="city1" name="city1">
					<!--<c:if test="${!empty sidos}">
						<option value="all">전체</option>
						<c:forEach items="${sidos}" var="sido">
							<option value="${sido}">${sido}</option>
						</c:forEach>
					</c:if>
					<c:if test="${empty sidos}">
						<option value="all">전체</option>
					</c:if>
					-->

					<option value="0">선택</option>
				</select>
			</div>
			<div class="mb-3">
				<label for="city2" class="form-label">시/군/구</label> <select
					class="form-control" id="city2" name="city2">
					<!-- <option value="all">전체</option>
					<option value="종로">종로</option>
					<option value="영등포">영등포</option>
					<option value="수원">수원</option>
					<option value="용인">용인</option>
					<option value="원주">원주</option>
					<option value="춘천">춘천</option>
					 -->
					<option value="0">선택</option>
				</select>
			</div>
			<div class="mb-3">
				<div class="input-group-text d-flex justify-content-around">
					<label for="base" class="form-label">검색 기준을 선택해주세요.</label>
					<div>
						<input type="radio" class="radio-value" name="base" value="동"
							checked />동
					</div>
					<div>
						<input type="radio" class="radio-value" name="base" value="아파트" />아파트
					</div>
				</div>
			</div>
			<div class="mb-3">
				<label for="city3" class="form-label">동 또는 아파트</label> <select
					class="form-control" id="city3" name="city3">
					<!-- <option value="all">전체</option>
					<option value="사직동">사직동</option>
					<option value="내수동">내수동</option>
					<option value="연건동">연건동</option> -->
					<option value="0">선택</option>

				</select> <input type="text" id="city4" name="city4" class="form-control"
					style="display: none">
			</div>
			
			<div class="mb-3">
				<div class="input-group-text d-flex justify-content-around">
					<label for="sort" class="form-label">정렬 기준을 선택하세요</label>
					<div>
						<input type="radio" class="radio-base" name="compare" value="distance"
							checked />거리
					</div>
					<div>
						<input type="radio" class="radio-base" name="compare" value="price" />가격
					</div>
				</div>
			</div>
			
			<div class="mb-3">
				<div class="input-group-text d-flex justify-content-around">
					<label for="sort" class="form-label">정렬 순서를 선택해주세요.</label>
					<div>
						<input type="radio" class="radio-sort" name="sort" value="오름차순"
							checked />오름차순
					</div>
					<div>
						<input type="radio" class="radio-sort" name="sort" value="내림차순" />내림차순
					</div>
				</div>
			</div>
			
			<div class="form-group text-center">
				<button type="button" id="searchBtn" name="searchBtn"
					class="btn btn-primary">검색하기</button>
			</div>
		</form>

		<section id="result">
			<table class="table mt-2">
				<colgroup>
					<col width="100">
					<col width="150">
					<col width="*">
					<col width="120">
					<col width="120">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>아파트이름</th>
						<th class="text-center">주소</th>
						<th>건축연도</th>
						<th>최근거래금액</th>
					</tr>
				</thead>
				<tbody id="searchResult">
				
				</tbody>
			</table>
			<div id="map" style="width: 100%; height: 500px;"></div>
			<script type="text/javascript"
				src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d02c091daa58ba38edce2bc5801f9c96&libraries=services"></script>
			<script type="text/javascript" src="js/map.js"></script>
		</section>
	</div>
</body>
</html>