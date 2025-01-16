function sortTableByColumn(table, column, asc = true) {
	const directionModifier = asc ? 1 : -1;
	const tbody = table.tBodies[0];
	const rows = Array.from(tbody.querySelectorAll("tr"));
	
	const sortedRows = rows.sort((a, b) => {
		const aColText = a.querySelector(`td:nth-child(${column + 1})`).textContent.toLowerCase().trim();
		const bColText = b.querySelector(`td:nth-child(${column + 1})`).textContent.toLowerCase().trim();
		
		return aColText > bColText ? (1 * directionModifier) : (-1 * directionModifier);
	});
	
	while (tbody.firstChild) {
		tbody.removeChild(tbody.firstChild);
	}
	
	tbody.append(...sortedRows);
	
	table.querySelectorAll("th").forEach(th => th.classList.remove("th-sort-asc", "th-sort-desc"));
	
	const header = table.querySelector(`th:nth-child(${column + 1})`);
	if (header) {
		header.classList.toggle("th-sort-asc", asc);
		header.classList.toggle("th-sort-desc", !asc);
	}
}

document.querySelectorAll(".table-sortable th").forEach(headerCell => {
	headerCell.addEventListener("click", () => {
		const tableElement = headerCell.parentElement.parentElement.parentElement;
		const headerIndex = Array.prototype.indexOf.call(headerCell.parentElement.children, headerCell);
		const currentIsAscending = headerCell.classList.contains("th-sort-asc");
		
		sortTableByColumn(tableElement, headerIndex, !currentIsAscending);
	});
});



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