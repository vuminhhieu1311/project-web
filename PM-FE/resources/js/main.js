let inputs = document.querySelectorAll(".input");
inputs.forEach(element => {
    element.addEventListener("mouseover", changeLable);
    element.addEventListener("mouseout", remLable);
});

function changeLable(){
    let h = this.getElementsByTagName("H5")[0];
    h.style.top="-20px";
}

function remLable(){
    let t = this.getElementsByTagName("INPUT")[0];
    if(t.value==""){
        let h = this.getElementsByTagName("H5")[0];
        h.style.top="0px";
    }
}
