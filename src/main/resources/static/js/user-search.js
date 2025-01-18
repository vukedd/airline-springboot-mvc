const table = document.querySelector(".table-sortable");
const tbody = table.tBodies[0];
const allTableRows  = tbody.querySelectorAll("tr");
const tableRowsCopy = tbody.querySelectorAll("tr");

console.log(table);
console.log(tbody);
console.log(allTableRows);
console.log(tableRowsCopy);

let searchBar = document.querySelector("#searchInput");
searchBar.addEventListener("input", (e) => {
	while (tbody.firstChild) {
		tbody.removeChild(tbody.firstChild);
	}
	
	for (let i = 0; i < allTableRows.length; i++) {
		let row = allTableRows[i];
		if (row.children[1].innerText.startsWith(e.target.value.toLowerCase())) {
			tbody.appendChild(row);
		}
	}
	

});