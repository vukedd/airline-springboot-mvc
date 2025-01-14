let firstNameFields = document.querySelectorAll(".firstNameField");
let lastNameFields = document.querySelectorAll(".lastNameField");
let passportNumberFields = document.querySelectorAll(".passportNumberField");
let firstNameFieldErrorLbls = document.querySelectorAll(".passengerNameLabel");
let lastNameFieldErrorLbls = document.querySelectorAll(".passengerLastNameLabel");
let numberFieldErrorLbls = document.querySelectorAll(".passengerNumberLabel");

let reservationSummaryForm = document.querySelector("#reservationSummaryForm");
reservationSummaryForm.addEventListener("submit", (e) => {
	e.preventDefault();
	let firstNameValid = true;
	let lastNameValid = true;
	let passportNumberValid = true;
	
	firstNameFieldErrorLbls.forEach(lbl => {
		lbl.innerHTML = "";
	});
	
	lastNameFieldErrorLbls.forEach(lbl => {
		lbl.innerHTML = "";
	});
	
	numberFieldErrorLbls.forEach(lbl => {
		lbl.innerHTML = "";
	});
	
	let firstLastNameRegex = /^[A-Za-z]+$/;
	let passportNumberRegex = /^[A-Za-z0-9]{9}$/;
		
	firstNameFields.forEach(field => {
		if (!firstLastNameRegex.test(field.value)) {
			let errorLabel = field.nextSibling.nextSibling;
			errorLabel.innerHTML = 'First name must contain letters only and it cannot be empty!';
			errorLabel.style.color = "red";
			firstNameValid = false;
		}
	});
	
	lastNameFields.forEach(field => {
		if (!firstLastNameRegex.test(field.value)) {
			let errorLabel = field.nextSibling.nextSibling;
			errorLabel.innerHTML = 'Last name must contain letters only and it cannot be empty!';
			errorLabel.style.color = "red";
			lastNameValid = false;
		}
	});

	passportNumberFields.forEach(field => {
		if (!passportNumberRegex.test(field.value)) {
			let errorLabel = field.nextSibling.nextSibling;
			errorLabel.innerHTML = 'Passport number must be 9 characters long!';
			errorLabel.style.color = "red";
			passportNumberValid = false;
		}
	});
	
	if (firstNameValid && lastNameValid && passportNumberValid) {
		reservationSummaryForm.submit();
	}	
});