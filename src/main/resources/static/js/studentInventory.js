var artifactID = 0;

function openPopup(artID) {
    artifactID = artID;
    document.getElementById("popup").style.height = "100%";
}

function use(){
    document.getElementById("useArtifact").formAction = "/student/inventory/use/"+artifactID;
}

function closePopup() {
    document.getElementById("popup").style.height = "0%";
}