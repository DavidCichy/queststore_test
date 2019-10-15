var artifactID = 0;

function openPopup(artID, coinAmount, price) {
    artifactID = artID;
    if(price > coinAmount){
        var coinsToCollect = price - coinAmount;
        alert("You can' afford this! Collect "+coinsToCollect+" more coins!");
    }else{
        document.getElementById("popup").style.height = "100%";
    }
}

function buy(){
    document.getElementById("buyArtifact").formAction = "/student/store/buy/"+artifactID;
}

function closePopup() {
    document.getElementById("popup").style.height = "0%";
}