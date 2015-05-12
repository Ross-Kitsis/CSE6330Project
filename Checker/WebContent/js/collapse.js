$('.issue').click(function()
{
	$(this).nextUntil('tr.issue').slideToggle(50);
});