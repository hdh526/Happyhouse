/*!
 * Start Bootstrap - Agency v7.0.10 (https://startbootstrap.com/theme/agency)
 * Copyright 2013-2021 Start Bootstrap
 * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-agency/blob/master/LICENSE)
 */
//
// Scripts
//

window.addEventListener('DOMContentLoaded', (event) => {
	// Navbar shrink function
	var navbarShrink = function () {
		const navbarCollapsible = document.body.querySelector('#mainNav');
		if (!navbarCollapsible) {
			return;
		}
		if (window.scrollY === 0) {
			navbarCollapsible.classList.remove('navbar-shrink');
		} else {
			navbarCollapsible.classList.add('navbar-shrink');
		}
	};

	// Shrink the navbar
	navbarShrink();

	// Shrink the navbar when page is scrolled
	document.addEventListener('scroll', navbarShrink);

	// Activate Bootstrap scrollspy on the main nav element
	const mainNav = document.body.querySelector('#mainNav');
	if (mainNav) {
		new bootstrap.ScrollSpy(document.body, {
			target: '#mainNav',
			offset: 74,
		});
	}

	// Collapse responsive navbar when toggler is visible
	const navbarToggler = document.body.querySelector('.navbar-toggler');
	const responsiveNavItems = [].slice.call(
		document.querySelectorAll('#navbarResponsive .nav-link')
	);
	responsiveNavItems.map(function (responsiveNavItem) {
		responsiveNavItem.addEventListener('click', () => {
			if (window.getComputedStyle(navbarToggler).display !== 'none') {
				navbarToggler.click();
			}
		});
	});
});

// admin id/pw
var adminId = 'admin';
var adminPw = '1234';

function login() {
	var user_json = localStorage.getItem('user');
	var user = JSON.parse(user_json);
	var inputId = document.querySelector('#email').value;
	var inputPw = document.querySelector('#password').value;
	if (
		inputId &&
		inputPw &&
		((inputId === adminId && inputPw === adminPw) ||
			(inputId === user['id'] && inputPw === user['password']))
	) {
		document.querySelector('#login').style.display = 'none';
		document.querySelector('#logout').style.display = 'block';
		document.querySelector('#client').style.display = 'block';
		document.querySelector('#nav-register').style.display = 'none';
		console.log('login');
		console.log(inputId, inputPw);
	} else {
		alert('올바른 아이디 및 패스워드를 사용하세요!');
	}
}

// 로그아웃
function logout() {
	console.log('logout');
	document.querySelector('#logout').setAttribute('style', 'display: none');
	document.querySelector('#client').setAttribute('style', 'display: none');
	document.querySelector('#login').setAttribute('style', 'display: block');
}
