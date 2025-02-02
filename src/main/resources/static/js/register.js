let maxDate = new Date().toISOString().split("T")[0];
let yearCalculation = maxDate.split("-")[0] - 18;
let splitDate = maxDate.split("-");
splitDate[0] = yearCalculation;
maxDate = splitDate.join("-");


document.getElementById("dateOfBirth").max = maxDate;