let addBtn = document.querySelector("#add-point");
let subtractBtn = document.querySelector("#subtract-point");
let pointsField = document.querySelector("#points-field");
let balanceField = document.querySelector("#balance");
let balance;
if (balanceField) {
	let balance = parseInt(balanceField.innerHTML);
} else {
	balance = -1;
}
let submitBtn = document.querySelector("#submit-discount-btn");
let priceLbl = document.querySelector("#price-lbl");
let originalPrice = parseInt(priceLbl.innerHTML.split(" ")[3]);
let reservationBtn = document.querySelector("#reservationBtn");
let submittedPointsField = document.querySelector("#points-field-submitted");
let price = originalPrice;

if (addBtn)
	addBtn.addEventListener("click", () => {
		if (balance <= 10) {
			if (parseInt(pointsField.value) < balance) {
				pointsField.value = parseInt(pointsField.value) + 1;
			}
		} else {
			if (parseInt(pointsField.value) < 10) {
				pointsField.value = parseInt(pointsField.value) + 1;
			}
		}
	});

if (subtractBtn)
	subtractBtn.addEventListener("click", () => {
		if (parseInt(pointsField.value) > 0) {
			pointsField.value = parseInt(pointsField.value) - 1;		
		}
	});

if (submitBtn)
	submitBtn.addEventListener("click", () => {
		price = parseInt(originalPrice) * (1 - ((parseInt(pointsField.value) * 7) / 100));
		submittedPointsField.value = pointsField.value;
		let paragraphElements = priceLbl.innerHTML.split(" ");
		paragraphElements[3] = price.toFixed(2);
		priceLbl.innerHTML = paragraphElements.join(" ");
	});

reservationBtn.addEventListener("click", () => {
	let formData = new FormData();
	if (submitBtn) {
		formData.append("points", parseInt(submittedPointsField.value));
	} else {
		formData.append("points", -1);
	}
	formData.append("totalPrice", price.toFixed(0));
	
	let request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			window.location.replace("/reservation/");
		}
	};
	request.open("POST", "/reservation/");
	request.send(formData);
//	window.location.replace("/reservation/=" + pointsField.value);
});