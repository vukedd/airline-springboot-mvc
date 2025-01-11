let btns = document.querySelectorAll(".addDiscountBtn");
let selectedFlightField = document.querySelector("#selectedFlightField");
let discountFormSubmitBtn = document.querySelector("#discountFormSubmitBtn");
let discountPercentageField = document.querySelector("#discountPercentage");
let endDateField = document.querySelector("#endDate");
let discountPercentageValidation = document.querySelector("#discountPercentageValidation");
let endDateValidation = document.querySelector("#endDateValidation");
let parentDiv;
let clickedBtn;

btns.forEach(btn => {
	btn.addEventListener("click", (e) => {
		clickedBtn = e.target;
		selectedFlightField.value = clickedBtn.getAttribute("flightId");
		parentDiv = clickedBtn.parentNode.parentNode;		
	});
})


discountFormSubmitBtn.addEventListener("click", (e) => {
	endDateValidation.style.display = "none";
	discountPercentageValidation.style.display = "none";
	
	let endDate = endDateField.value;
	let discountPercentage = discountPercentageField.value;
	if ((endDate == '' || Date.parse(endDate) <= Date.now())) {
		endDateValidation.style.display = "inline";
	}
	
	if (discountPercentage == null || (discountPercentage <= 0 || discountPercentage > 100)) {
		discountPercentageValidation.style.display = "inline";
	}
	
	if ((endDate != '' && Date.parse(endDate) > Date.now()) && (discountPercentage > 0 && discountPercentage <= 100)) {
		let formData = new FormData();
		formData.append("flightId", selectedFlightField.value);
		formData.append("endDate", new Date(endDate).toISOString().split("T")[0]);
		formData.append("discountPercentage", parseInt(discountPercentage));
		let request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (this.readyState == 4){
				if (this.status == 200){
					let newPrice = document.createElement("strong");
					newPrice.style.color = "red";
					newPrice.innerText = this.response + "0 RSD"
					newPrice.style.fontSize = "normal";
					var discountModal = bootstrap.Modal.getOrCreateInstance(document.getElementById("flightDiscount"));
					discountModal.hide();
					
					parentDiv.children[0].style.textDecoration = "line-through";
					parentDiv.insertBefore(newPrice, parentDiv.children[1]);
					clickedBtn.disabled = true;
				}
			}
		}
		request.open("POST", "/discount/create");
		request.send(formData);
	}
});

document.getElementById("departureDateField").min = new Date().toISOString().split("T")[0];