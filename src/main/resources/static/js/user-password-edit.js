let oldPasswordField = document.querySelector("#oldPassword");
let newPasswordField = document.querySelector("#newPassword");
let newPasswordConfirmationField = document.querySelector("#confirmPassword");

let showPasswordCB = document.querySelector("#showPassword");

showPasswordCB.onchange(() => {
	toggleInputFieldType();
});


function toggleInputFieldType() {
	console.log('hello');
	let type = oldPasswordField.type === 'password' ? 'text' : 'password';
	
	oldPasswordField.type = type;
	newPasswordField.type = type;
	newPasswordConfirmationField.type = type;
}