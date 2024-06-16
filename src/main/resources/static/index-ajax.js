$(document).ready(function() {
	$("#loanNumberField").change(function() {
		var loanNo = $("#loanNumberField").val();
		$.get({
			url: "/nameFromDB",
			data: { loanNo: loanNo },
			success: function(data) {
				$("#name").val(data);
			},
			error: function() {
				alert("Error in Fetching Name, Loan Number Not found");
			}
		});
	});
});

	