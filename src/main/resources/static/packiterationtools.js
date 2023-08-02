/**
 * Converts a given poll Id string to the associated webpage URL.
 */
function pollIdToUrl(pollID) {
    return "/polls/" + pollID
}

/**
 * Stores a provided JSON array of poll-IDs in local session store, instantiated index counter and redirects to
 * first poll page.
 * @param pollUrls
 */
function initializePackSequence(pollUrls) {
    sessionStorage.setItem("pack-sequence", pollUrls)
    sessionStorage.setItem("pack-pointer", "0")

    // construct poll URL for first ID in list and forward
    window.location.replace(pollIdToUrl(pollUrls[0]))
}

// TODO add function to go forward / back by / go to target index.

/**
 * Proceed to next poll in loaded pack, if there are any remaining.
 */
function packForward() {
    let index = Number(sessionStorage.getItem("pack-pointer"))
    let allPackPollIds = sessionStorage.getItem("pack-sequence").split(',')


    if (index < allPackPollIds.length - 1) {
        // there are remaining questions, go one forward:
        sessionStorage.setItem("pack-pointer", (index + 1).toString())
        let previousPollId = pollIdToUrl(allPackPollIds[index + 1])
        window.location.replace(previousPollId)
    } else {
        // TODO: Unload pack OR ignore.

        window.location.replace("/")
    }
}

/**
 * Go back to previous poll in loaded pack, if there are any before.
 */
function packBackward() {
    let index = Number(sessionStorage.getItem("pack-pointer"))
    let allPackPollIds = sessionStorage.getItem("pack-sequence").split(',')

    if (index > 0) {
        // there are previous questions, go one back:
        sessionStorage.setItem("pack-pointer", (index - 1).toString())
        let previousPollId = pollIdToUrl(allPackPollIds[index - 1])
        window.location.replace(previousPollId)
    } else {

        // TODO: unload pack OR ignore.
        window.location.replace("/")
    }
}