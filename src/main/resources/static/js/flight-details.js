// Flight seats
let arrAvailable = document.querySelectorAll(".seat");

arrAvailable.forEach(seat =>  {
	seat.addEventListener("click", (event) => {
		const button = event.target

		if (button.classList.contains("selected")) {
			button.classList.remove("selected");
			button.classList.add("available");
		} else if (button.classList.contains("available")) {
			const column = seat.getAttribute("data-column");
			const row = seat.getAttribute("data-row")
			console.log(parseInt(column) - 1 + "|" + parseInt(row - 1));
			button.classList.add("selected");
			button.classList.remove("available");
		}
	})
});

let cartButton = document.getElementById("add-to-cart-btn");
cartButton.addEventListener("click", (e) => {
	let arrSelected = document.querySelectorAll(".seat.selected");
	let requestUrl = "/flight/book?flightId=" + document.getElementById("flightIdField").value + "&selectedSeats=";
	arrSelected.forEach(seat => {
		requestUrl += seat.getAttribute("data-column") + "-" + seat.getAttribute("data-row") + ",";		
	})
	
	if (arrSelected.length < 1) {
		window.location.replace("/flight/details?id=" + document.getElementById("flightIdField").value + "&actionStatus=addToCartError");
	}
	
	if (requestUrl.endsWith(",")) {
		requestUrl = requestUrl.slice(0, -1);
	}
	let success = false;
	
	let request = new XMLHttpRequest();
	request.onreadystatechange = function(){
		if (this.readyState == 4 && this.status == 200){
			if (document.getElementById("logged-in").value === '1') {
				window.location.replace("/cart");
			} else {
				window.location.replace("/auth/login");
			}
		}
	}
	request.open("POST", requestUrl);
	request.send();
});


// Wishlist
let userIdField = document.querySelector("#userIdField");
let wishlistButton = document.querySelector("#wishlistBtn");
let flightIdField = document.getElementById("flightIdField");
wishlistButton.addEventListener("click", () => {
	let icon = wishlistButton.children[0];
	let iconClass = icon.getAttribute("class");
	if (iconClass === "bi bi-heart") {
		let formData = new FormData();
		formData.append("userId", userIdField.value);
		formData.append("flightId", flightIdField.value);
		
		let request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (this.readyState == 4) {
				if (this.status == 200) {
					if (this.responseText == 'true') {
						icon.removeAttribute("class");
						icon.setAttribute("class", "bi bi-heart-fill");
					}
				}
			}
		};
		request.open("POST", "/wishlist/add");
		request.send(formData);
		
	} else if (iconClass === "bi bi-heart-fill") {
		let formData = new FormData();
		formData.append("userId", userIdField.value);
		formData.append("flightId", flightIdField.value);
		
		let request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (this.readyState == 4) {
				if (this.status == 200) {
					if (this.responseText == 'true') {
						icon.removeAttribute("class");
						icon.setAttribute("class", "bi bi-heart");
					}
				}
			}
		};
		request.open("POST", "/wishlist/remove");
		request.send(formData);
	}
}); 