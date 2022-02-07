console.log("Loader connected")
removeFadeOut(document.getElementById("loader"), 1500);

function removeFadeOut( el, speed ) {
    setTimeout(function() {
        el.style.transition = "opacity " + .5 +"s ease";
        el.style.opacity = 0;
        setTimeout(function() {
            el.parentNode.removeChild(el);
        }, 500);
    }, speed);
}
