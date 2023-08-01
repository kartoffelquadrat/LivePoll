// JavaScript to modify the DOM based on the poll packs persisted on disk at backend.

/**
 * Obtains list of packs from backend and initiates listing them all in DOM.
 */
function updatePackList() {
    fetch('/menu/packs')
        .then(result => result.json())
        .then(packmetas => modifyPackMetaDom(packmetas))
}

/**
 * Modifies DOM to contain one card per loaded pack, as indexed in json map.
 * @param packMetaMap
 */
function modifyPackMetaDom(packMetaMap) {

    // compute how many lines (with up to two entries) are needed
    let packKeys = Object.keys(packMetaMap)
    let packLines = Math.ceil(packKeys.length / 2.0) // rounded up division by 2

    // let metapacks = JSON.parse(packmetas)
    console.log("Packs found: " + packKeys)
    console.log("Lines needed: " + packLines)


    // For every pack (map entry), create a grid entry
    for (const [packFileName, packMeta] of Object.entries(packMetaMap)) {
        createPackCard(packFileName, packMeta)
    }
}

/**
 * Initiates call to backend to create REST endpoints for all polls associated to the given pack.
 */
function instantiatePack(packId) {
    alert("Instantiating pack: "+packId)

    fetch('/menu/packs/'+packId, {method: "PUT"})
        .then(result => result.json())
        .then(pollUrls => alert(pollUrls))
}

/**
 * Helper function to create HTML code that corresponds to one pack card representation, and
 * associated JS handler function.
 * @param packFileName
 * @param packMeta
 */
function createPackCard(packFileName, packMeta) {

    console.log("Key: " + packFileName + ", Value: " + packMeta)
    let title = packMeta.title
    let author = packMeta.author
    let date = packMeta.creation
    let abstract = packMeta.packAbstract

    // Use pack metadata to fill html template (of card)
    // See: https://stackoverflow.com/a/42758963
    let div = document.createElement('div');
    // TODO: adjust onclick: it must trigger creation of all questions and redirect to first poll
    // of pack
    div.setAttribute("class", "menu-option")
    div.innerHTML = `
        <div onclick="instantiatePack(\``+packFileName+`\`)">
            <h2>${title}</h2>
            <br/>
            ${abstract}
            <br/><br/>
            <div style="text-align: right">${author}, ${date}</div>
        </div>
    `;
    document.getElementById("pack-container").appendChild(div);
}
