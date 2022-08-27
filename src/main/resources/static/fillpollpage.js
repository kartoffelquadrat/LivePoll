function removeNeutralOptionIfEmpty() {

    // look up text of netrual option. Is empty if not needed.
    let maybeoption = document.getElementById("maybeoption");
    maybeoptiontext = maybeoption.innerText;
    console.log("Analyzing maybeoption: " + maybeoption);

    // Remove entire div if empty, boost first and last option.
    if (!maybeoptiontext) {
        // make middle column invisible
        document.getElementById("maybediv").style.display = "none";

        // boost width of remaining columns to 50%
        document.getElementById("left").style.width = "50%";
        document.getElementById("right").style.width = "50%";
    }
}


function autoreload() {
    //TODO: dont reload everything, only reload outcome.
    // setTimeout(function(){
    //     window.location.reload(1);
    // }, 1000);
}

