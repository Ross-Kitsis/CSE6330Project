$('.issue').click(function()
{
	$(this).nextUntil('tr.issue').slideToggle(50);
});

$('.websites').click(function()
{
	$(this).nextUntil('tr.endwebsites').slideToggle(50);
});

$(".firstUl li").click(function (e) {
    $(this).next("li").children(".submenu-ebene2").stop().slideToggle();
});

$(".submenu-ebene2 li").click(function() {
$(this).next("li").children(".submenu-ebene3").stop().slideToggle();
});

$(".submenu-ebene3 li").click(function() {
	$(this).next("li").children(".submenu-ebene4").stop().slideToggle();
});

$(".link").click(function(e) {
$(".active").toggleClass("inactive active");
$(this).addClass("active").removeClass("inactive");
});