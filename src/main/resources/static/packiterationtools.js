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