/**
 * must be called when form page is accessed via browser back button so form submit button is re-enabled
 *
 */
function enableButtons() {
    // Enable button so page us usable when user navigates back
    document.getElementById("submitbutton").disabled = false;

    // reset checkbox
    document.getElementById("neutralallowed").checked = false;
}

function registerPollSubmitButton() {

    // Register click handlers
    console.log("Registering poll submit button");
    document.getElementById("submitbutton").addEventListener("click", submitPoll)
    document.getElementById("neutralallowed").addEventListener("click", refreshOptions)

    // Register enter in topic field
    document.getElementById("topic").addEventListener("keyup", ({key}) => {
        if (key === "Enter") {
            submitPoll();
        }
    })

    // focus on input filed by default
    document.getElementById("topic").focus();
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

    // deactivate button to prevent further clicks
    document.getElementById("submitbutton").disabled = true;


    // Create JSON that can be fed into CONSTRUCTOR of poll object.
    let pollFormData = {"topic": question, "options": []}
    if (document.getElementById('neutralallowed').checked) {
        pollFormData.options = [positiveLabel, neutralLabel, negativeLabel];
    } else {
        pollFormData.options = [positiveLabel, negativeLabel];
    }

    // Create new poll at backend
    console.log(pollFormData);
    fetch('/polls/', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify(pollFormData)
    }).then(reply => reply.text())
        .then(pollid => {
            console.log(pollid)

            // Forword to URL of new poll
            window.location.href = "/polls/"+pollid;
        });
}