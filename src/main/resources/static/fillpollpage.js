function assNavigateBackListener() {
    document.addEventListener('keyup', (e) => {
        if (e.code === "ArrowLeft")
            window.location.href = "/";
    });
}


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
    setTimeout(function () {
        updateOutcome();
        autoreload();
    }, 1000);
}

function updateOutcome() {

    let pollid = document.getElementById("pollid").innerText;
    let firstoption = document.getElementById("firstoptioncode").innerText;
    let maybeoption = document.getElementById("maybeoptioncode").innerText;
    let lastoption = document.getElementById("lastoptioncode").innerText;

    console.log("pollid: " + pollid);
    console.log("firstoption: " + firstoption);
    console.log("maybeoption: " + maybeoption);
    console.log("lastoption: " + lastoption);

    refreshVote(pollid, firstoption, "counter1");
    refreshVote(pollid, lastoption, "counter3");

    // only refresh neutral if exists
    if(maybeoption)
        refreshVote(pollid, maybeoption, "counter2");
}

function refreshVote(pollid, option, targetelement) {

    fetch('/polls/' + pollid + '/outcome/' + option).then(result => result.text()).then(text => {
            if(text >= 0)
                document.getElementById(targetelement).textContent = text;
            else
                window.location.href = "/";
        }
    );
}
