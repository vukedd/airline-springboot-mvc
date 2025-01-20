function sortTableByColumn(table, column, asc = true) {
	const directionModifier = asc ? 1 : -1;
	const tbody = table.tBodies[0];
	const rows = Array.from(tbody.querySelectorAll("tr"));
	
	function getCellValue(row, col) {
		const cell = row.querySelector(`td:nth-child(${col + 1})`);
		if (!cell)
			return "";
				
		const cellText = cell.textContent.trim();
		
		if (!isNaN(cellText) && cellText != "") {
			return parseFloat(cellText);
		} else if (Date.parse(cellText)) {
			return new Date(cellText);
		}
		
		return cellText.toLowerCase();
	};
	
	const sortedRows = rows.sort((a, b) => {
		const aValue = getCellValue(a, column);
		const bValue = getCellValue(b, column);
		
		if (aValue > bValue)
			return 1 * directionModifier;
			
		if (aValue < bValue)
			return -1 * directionModifier;
		
		return 0
	});
	
	tbody.innerHtml = "";
	sortedRows.forEach(row => {
		tbody.appendChild(row);
	});
	

	table.querySelectorAll("th").forEach(th => th.classList.remove("th-sort-asc", "th-sort-desc"));
	const header = table.querySelector(`th:nth-child(${column + 1})`);
	if (header) {
		header.classList.toggle("th-sort-asc", asc);
		header.classList.toggle("th-sort-desc", !asc);	
	}
}

document.querySelectorAll(".table-sortable th").forEach(headerCell => {
	headerCell.addEventListener("click", () => {
		const tableElement = headerCell.closest("table");
		const headerIndex = Array.from(headerCell.parentElement.children).indexOf(headerCell);
		const currentIsAscending = headerCell.classList.contains("th-sort-asc");
		
		sortTableByColumn(tableElement, headerIndex, !currentIsAscending);
	});
});