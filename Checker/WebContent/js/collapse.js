$('.issue').click(function()
{
	$(this).nextUntil('tr.issue').slideToggle(50);
});

$('.websites').click(function()
{
	$(this).nextUntil('tr.endwebsites').slideToggle(50);
});