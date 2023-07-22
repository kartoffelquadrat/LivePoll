/**
 * Registers a handler to go back to poll overview and a second one to reveal the poll counts.
 */
function addKeyListeners() {
    document.addEventListener('keyup', (e) => {
        if (e.code === "ArrowLeft") window.location.href = "/";
    });
    document.addEventListener('keyup', (e) => {
        if (e.code === "Space") revealNumbers();
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

function revealNumbers() {
    console.log("Spacebar press registered!")

    // Reveal the "yes" and "no" options
    document.getElementById("counter1").style.display = null;
    document.getElementById("counter3").style.display = null;

    // Reveal the maybeoption if it exists
    let maybeoption = document.getElementById("maybeoptioncode").innerText;
    if (maybeoption) {
        document.getElementById("counter2").style.display = null;
    }

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
    if (maybeoption) refreshVote(pollid, maybeoption, "counter2");
}

/**
 * Retrieves amount of votes for a given option from backend.
 * @param pollid as the identifier of the poll.
 * @param option as the option to get the stats for.
 * @param targetelement as the DOM element to modify by retrieved number.
 */
function refreshVote(pollid, option, targetelement) {

    fetch('/polls/' + pollid + '/outcome/' + option).then(result => result.text()).then(text => {
        if (text >= 0) document.getElementById(targetelement).textContent = text; else window.location.href = "/";
    });
}
