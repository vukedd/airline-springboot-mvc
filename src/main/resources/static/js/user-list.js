let blockBtns = document.querySelectorAll(".block-btn");
let unblockBtns = document.querySelectorAll(".unblock-btn");
let unblockModal = document.querySelector("#unblock");
let selectedUserUnblockId = document.querySelector("#selectedUserUnblockId");
let selectedUserBlockId = document.querySelector("#selectedUserBlockId");
blockBtns.forEach(btn => {
	btn.addEventListener("click", (e) => {
		let clickedBtn = e.target;
		let userId = clickedBtn.getAttribute("user-id");
		selectedUserBlockId.value = parseInt(userId);
	});
});


unblockBtns.forEach(btn => {
	btn.addEventListener("click", (e) => {
		let clickedBtn = e.target;
		let userId = clickedBtn.getAttribute("user-id");
		selectedUserUnblockId.value = parseInt(userId);
	});
});