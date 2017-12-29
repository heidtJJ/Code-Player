function updateOutput(){
    $("#output-panel").contents().find("html").html("<html><head><style type='text/css'>" + $("#css-panel").val() + "</style></head><body>" + $("#html-panel").val() + "</body></html>");

    document.getElementById("output-panel").contentWindow.eval($("#javascript-panel").val());                
}



$(".toggle-box").hover(function() {
    $(this).addClass("highlighted-box");
}, function(){
    $(this).removeClass("highlighted-box");
});



$(".toggle-box").click(function(){
    $(this).toggleClass("active");
    $(this).removeClass("highlighted-box");

    var panelID = "#" + $(this).attr("id") + "-div";

    $(panelID).toggleClass("hidden");

    var numActivePanels = 4 - $(".hidden").length;

    $(".io-box").width(($(window).width() / (4 - $(".hidden").length)) - 30 );

});



$("textarea").bind("input propertychange", function() {

    updateOutput();

});



$(".io-box").width(($(window).width() / (4 - $(".hidden").length)) - 30 );

$(".io-box").height($(window).height()-$("#top-bar").height()-105);

updateOutput();
