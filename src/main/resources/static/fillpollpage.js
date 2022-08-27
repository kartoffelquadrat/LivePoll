function fillWithPollDetails() {

    // get poll details from backend


    // use poll details to set: title, QRs, counters
    // setTitle(pollDetails);
    reduceToTwoColumns();
}

function reduceToTwoColumns() {

    // make middle column invisible
    document.getElementById("middle").style.display = "none";

    // boost width of remaining columns to 50%
    document.getElementById("left").style.width = "50%";
    document.getElementById("right").style.width = "50%";
}

function setTitle() {
    // alert("TITLE");
}
