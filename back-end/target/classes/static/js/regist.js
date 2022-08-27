function regist() {
	let id = document.querySelector('#registId').value;
	let password = document.querySelector('#registPassword').value;
	let name = document.querySelector('#registName').value;
	let address = document.querySelector('#registAddress').value;
	let phone = document.querySelector('#registPhone').value;

	console.log(id, password, name, address, phone);

	let user = {
		id: id,
		password: password,
		name: name,
		address: address,
		phone: phone,
	};

	let user_json = JSON.stringify(user);
	localStorage.setItem('user', user_json);

	location.href = '/index.html';
}
