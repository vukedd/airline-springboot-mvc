let fromInputDate = document.querySelector("#dateFrom");
let toInputDate = document.querySelector("#dateTo");
let submitBtn = document.querySelector("#submitReportSearchBtn");
let form = document.querySelector("#reportSearchForm");

form.addEventListener("submit", (e) => {
	e.preventDefault();
	let reportSearchLabel = document.querySelector("#reportSearchLabel");
	if (toInputDate.value != "" && fromInputDate.value != "") {
		form.submit();
		reportSearchLabel.style.fontSize = "0px";
	} else {
		reportSearchLabel.style.fontSize = "15px";
	}
});