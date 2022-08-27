window.onload = function () {
	let user_json = localStorage.getItem('user');
	let user = JSON.parse(user_json);
	let id = user['id'];
	let password = user['password'];
	let name = user['name'];
	let address = user['address'];
	let phone = user['phone'];

	// console.log(id, password, name, address, phone);

	document.querySelector('#infoId').value = id;
	document.querySelector('#infoPassword').value = password;
	document.querySelector('#infoName').value = name;
	document.querySelector('#infoAddress').value = address;
	document.querySelector('#infoPhone').value = phone;
};

function regist2() {
	let id = document.querySelector('#infoId').value;
	let password = document.querySelector('#infoPassword').value;
	let name = document.querySelector('#infoName').value;
	let address = document.querySelector('#infoAddress').value;
	let phone = document.querySelector('#infoPhone').value;

	// console.log(id, password, name, address, phone);

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

function home() {
	location.href = '/index.html';
}

function secession() {
	localStorage.setItem(
		'user',
		JSON.stringify({
			id: '',
			password: '',
			name: '',
			address: '',
			phone: '',
		})
	);
	location.href = '/index.html';
}
