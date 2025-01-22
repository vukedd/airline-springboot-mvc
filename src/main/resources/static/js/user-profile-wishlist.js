let removeWishlistItemBtn = document.querySelectorAll(".removeWishlistItemBtn");
let wishlist = document.querySelector("#wishlist");

removeWishlistItemBtn.forEach(button => {
	button.addEventListener("click", (e) => {
		let clickedBtn = e.target;
		let itemId = clickedBtn.getAttribute("wishlist-item-id");
		let userId = clickedBtn.getAttribute("user-id");
		
		let formData = new FormData();
		formData.append("userId", userId);
		formData.append("flightId", itemId);
		let request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (this.readyState == 4 && this.status == 200) {
				if (this.responseText === 'true'){
					let wishlistItemForRemoval = clickedBtn.parentNode;
					wishlist.removeChild(wishlistItemForRemoval);
					
				}
			}
		};
		request.open("POST", "/wishlist/remove");
		request.send(formData);
	});
});
