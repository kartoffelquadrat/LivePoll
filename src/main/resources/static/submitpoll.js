function registerPollSubmitButton() {

    console.log("Registering poll submit button");
    document.getElementById("submitbutton").addEventListener("click", submitPoll)
    document.getElementById("neutralallowed").addEventListener("click", refreshOptions)

}

// Verifies the status fo the neutral votes checkbox and enables the thrid option text if needed.
function refreshOptions() {

    let neturalenabled = document.getElementById('neutralallowed').checked
    console.log("Neutral votes allowed: " + neturalenabled)
    optionaldiv = document.getElementById("optionaloption");

    if (neturalenabled) {
        optionaldiv.style.removeProperty('display');

    } else {
        optionaldiv.style.display = "none"
    }
}

async function submitPoll() {

    // Get values of input fields:
    let question = document.getElementById("topic").value;
    console.log(question);
    let positiveLabel = document.getElementById("option1").value;
    let neutralLabel = document.getElementById("option2").value;
    let negativeLabel = document.getElementById("option3").value;

    if (!question) {
        alert("Question must not be empty.")
        return;
    }

    // Create JSON that can be fed into CONSTRUCTOR of poll object.
    let pollFormData = {"topic": question, "options": []}
    if (document.getElementById('neutralallowed').checked) {
        pollFormData.options = [positiveLabel, neutralLabel, negativeLabel];
    } else {
        pollFormData.options = [positiveLabel, negativeLabel];
    }

    console.log(pollFormData);

    fetch('/polls/', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify(pollFormData)
    }).then(reply => {
        // TODO: forward to landing QR page.
        console.log(reply);
    });
}