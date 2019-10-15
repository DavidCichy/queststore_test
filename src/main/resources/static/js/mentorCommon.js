function handleMenu() {
    var x = document.getElementById("Menu");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function openMenu() {
    document.getElementById("menu").style.width = "200px";
    document.getElementById("middleSection").style.marginLeft = "200px";
}

function closeMenu() {
    document.getElementById("menu").style.width = "0";
    document.getElementById("middleSection").style.marginLeft = "0";
}