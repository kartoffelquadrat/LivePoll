// JavaScript to modify the DOM based on the poll packs persisted on disk at backend.

function updatePackList() {
    fetch('/menu/packs/')
        .then(result => result.json())
        .then(packmetas => modifyPackMetaDom(packmetas))
}

function modifyPackMetaDom(packmetamap) {

    // compute how many lines (with up to two entries) are needed
    let packKeys = Object.keys(packmetamap)
    let packLines = Math.ceil(packKeys.length / 2.0) // rounded up division by 2

    // let metapacks = JSON.parse(packmetas)
    console.log("Packs found: "+ packKeys)
    console.log("Lines needed: "+packLines)
}