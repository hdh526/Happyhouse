// 실거래가 검색하기 -> 검색 결과 띄워주기
$(document).ready(function () {
	$('#getInfo').click(function () {
		// 대한민국 모든 특별/광역시,도
		var countryUrl =
			'https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00000000';

		// 서울특별시 소속의 모든 구,동
		var seoulUrl =
			'https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=11*';

		// 실거래가 api url
		var url =
			'http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade';

		var queryParams =
			'?' +
			encodeURIComponent('ServiceKey') +
			'=' +
			'eU5u3SoVLrATuYcq%2F%2BPs5dS%2BzsoeDtX1X8pfsqXb6m4BFT3KmXMjUjZUsqN9tLzWBp%2FQ2D6Ok%2FQ8zDKpqjWy0g%3D%3D';
		queryParams += '&' + encodeURIComponent('LAWD_CD') + '=' + encodeURIComponent('11110');
		queryParams += '&' + encodeURIComponent('DEAL_YMD') + '=' + encodeURIComponent('201512');

		var xhr = new XMLHttpRequest();
		xhr.open('GET', url + queryParams);
		xhr.send('');

		// server에서 넘어온 data
		$.ajax({
			url: url,
			type: 'GET',
			dataType: 'xml',
			data: {
				//ServiceKey: '일반인증키(Decoding)', /*Decoding Service Key*/
				ServiceKey:
					'eU5u3SoVLrATuYcq/+Ps5dS+zsoeDtX1X8pfsqXb6m4BFT3KmXMjUjZUsqN9tLzWBp/Q2D6Ok/Q8zDKpqjWy0g==' /*Decoding Service Key*/,
				LAWD_CD: '11110',
				DEAL_YMD: '201512',
			},
			success: function (response) {
				makeList(response);
			},
			error: function (xhr, status, msg) {
				console.log('상태값 : ' + status + ' Http에러메시지 : ' + msg);
			},
		});
	});

	function makeList(data) {
		var city1 = document.querySelector('#city1').value;
		var city2 = document.querySelector('#city2').value;
		var city3 = document.getElementById('city3'); 
		city3 = city3.options[city3.selectedIndex].value;
		var base = document.getElementsByName('base');
		let baseLine = '';
		base.forEach((b) => {
			if (b.checked) baseLine = b.value;
		});
		console.log(city1, city2, baseLine, city3);
		var aptlist = ``;
		$(data)
			.find('item')
			.each(function () {
				if (city3.trim() === '') {
					aptlist += `<tr>
                        <td>${$(this).find('아파트').text()}</td>
                        <td>${$(this).find('법정동').text()}</td>
                        <td>${$(this).find('전용면적').text()}</td>
                        <td>${$(this).find('거래금액').text()}</td>
                        <td>${$(this).find('층').text()}</td>
                        <td>${$(this).find('건축년도').text()}</td>
                        <td>${$(this).find('지번').text()}</td>
                        <td>${$(this).find('지역코드').text()}</td>
                    </tr>`;
				} else if (baseLine === '동' && $(this).find('법정동').text().trim() === city3.trim()) {
					aptlist += `<tr>
                        <td>${$(this).find('아파트').text()}</td>
                        <td>${$(this).find('법정동').text()}</td>
                        <td>${$(this).find('전용면적').text()}</td>
                        <td>${$(this).find('거래금액').text()}</td>
                        <td>${$(this).find('층').text()}</td>
                        <td>${$(this).find('건축년도').text()}</td>
                        <td>${$(this).find('지번').text()}</td>
                        <td>${$(this).find('지역코드').text()}</td>
                    </tr>`;
				} else if (baseLine === '아파트' && $(this).find('아파트').text().trim() === city3.trim()) {
					aptlist += `<tr>
                        <td>${$(this).find('아파트').text()}</td>
                        <td>${$(this).find('법정동').text()}</td>
                        <td>${$(this).find('전용면적').text()}</td>
                        <td>${$(this).find('거래금액').text()}</td>
                        <td>${$(this).find('층').text()}</td>
                        <td>${$(this).find('건축년도').text()}</td>
                        <td>${$(this).find('지번').text()}</td>
                        <td>${$(this).find('지역코드').text()}</td>
                    </tr>`;
				}
			});
		$('#aptinfo').empty().append(aptlist);
		$('tr:first').css('background', 'darkgray').css('color', 'white');
		$('tr:even').css('background', 'lightgray');
		document.querySelector('#searchResultTitle').innerText =
			city3.trim() === '' ? '전체 조회 결과' : `${baseLine}별 조회 결과`; // 향후 아파트, 동 별 검색 기준 만들어 구현
		document.querySelector('#result').style.display = 'block';
	}
	console.log("success");
});
