// JavaScript to modify the DOM based on the poll packs persisted on disk at backend.

function updatePackList() {
    fetch('/menu/packs/')
        .then(result => result.json())
        .then(packmetas => modifyPackMetaDom(packmetas))
}

function modifyPackMetaDom(packmetamap) {

    // let metapacks = JSON.parse(packmetas)
    console.log( { packmetamap })
    console.log("All good.")
}