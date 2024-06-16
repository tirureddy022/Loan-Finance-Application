
$(document).ready(function() {

	$(".sel").keyup(function() {

		var nullChecker = $("#f1").val();
		var nullChecker2 = $("#f2").val();

		if (nullChecker.trim() !== '' || nullChecker2.trim() !== '') {

			var formData = $("#searchForm").serialize();
			$.get({
				url: "/search",
				data: formData,

				success: function(data) {

					$("#rec").replaceWith($(data).find("#rec"));
				},
				error: function() {

					alert("Please try again.....");
				}
			});
		}
	});


	document.getElementById("loanClousureBtn").addEventListener("click", function() {
		var loanNoInput = document.getElementById("f1");
		if (confirm('Are you sure want to Close the Loan...!')) {
			if (loanNoInput) {
				var loanNo = loanNoInput.value;
				var url = "/handleclousure?loanNo=" + loanNo;
				console.log(url)
				document.getElementById("loanClousureBtn").setAttribute("href", url);
			}
		} else {
			console.error("Loan number input field not found!");
		}
	});

	document.getElementById("submitBtn").addEventListener("click", function() {
		var loanNo = document.getElementById("loanNo").value.trim();
		var endDate = document.getElementById("newEndDate").value.trim();

		if (loanNo !== '' && endDate !== '') {
			var editDateUrl = "/editEndDate?endDate=" + endDate + "&loanNo=" + loanNo;
			console.log(editDateUrl);
			document.getElementById("editEndDateForm").setAttribute("action", editDateUrl);
		} else {
			alert("Loan number input field or end date is empty!");
		}


	});
});




