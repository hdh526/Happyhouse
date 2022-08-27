// 주변 병원 및 선별진료소
$(document).ready(function () {
	$('#hospitalModalLink').click(function () {
		var ServiceKey =
			'eU5u3SoVLrATuYcq/+Ps5dS+zsoeDtX1X8pfsqXb6m4BFT3KmXMjUjZUsqN9tLzWBp/Q2D6Ok/Q8zDKpqjWy0g==';
		var pageNo = '1';
		var numOfRows = '30';
		// server에서 넘어온 data
		$.ajax({
			url: 'http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService',
			type: 'GET',
			data: {
				serviceKey: ServiceKey,
				pageNo: pageNo,
				numOfRows: numOfRows,
			},
			dataType: 'xml',
			success: function (response) {
				console.log(response);
				makeList(response);
			},
			error: function (xhr, status, msg) {
				console.log('상태값 : ' + status + ' Http에러메시지 : ' + msg);
			},
		});
	});

	function makeList(data) {
		let hospitalList = ``;
		$(data)
			.find('item')
			.each(function () {
				hospitalList += `
						<tr>
							<td>${$(this).find('yadmNm').text()}</td>
							<td>${$(this).find('telno').text()}</td>
							<td>${$(this).find('addr').text()}</td>
							<td>${$(this).find('pcrPsblYn').text()}</td>
							<td>${$(this).find('YPosWgs84').text()}</td>
							<td>${$(this).find('XPosWgs84').text()}</td>
						</tr>`;
			});
		//alert(studentlist);
		$('#hospitalInfo').empty().append(hospitalList);
		$('tr:first').css('background', 'darkgray').css('color', 'white');
		$('tr:even').css('background', 'lightgray');
		$('#hospitalLoadingMsg').css('display', 'none');
	}
});
