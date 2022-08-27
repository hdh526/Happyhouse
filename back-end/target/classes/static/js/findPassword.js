$(function () {
	$('#findPw').click(function () {
		$('#loginTitle').text('비밀번호 찾기');
		$('#passwordContainer').css('display', 'none');
		$('#remembermeContainer').css('display', 'none');
		$('#loginBtn').css('display', 'none');
		$('#findPw').css('display', 'none');
		$('#findPw2').css('display', 'block');
	});
});

$(function () {
	$('#findPw2').click(function () {
		var user_json = localStorage.getItem('user');
		var user = JSON.parse(user_json);
		var tmpId = document.querySelector('#email').value;
		if (user['id'] === tmpId) {
			console.log(user['password']);
			document.querySelector('#email').value = user['password'];
			document.getElementById('labelId').innerHTML = '찾은 비밀번호입니다.';
		}
	});
});
