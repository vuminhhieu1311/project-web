
let openContactInfo = document.getElementById("contact-info");
let closeContactInfo = document.getElementById("contact-info-close");
let overlayContactInfo = document.getElementById("overlay");
let contactInfoDiv = document.getElementById("contact-info-div");
let body = Array.from(document.getElementsByTagName("BODY"));

openContactInfo.addEventListener("click", ()=>{
    contactInfoDiv.style.display="block";
    body.forEach( ele=>{
        ele.style.overflow="hidden";
    })
    overlayContactInfo.style.display="block";
})

closeContactInfo.addEventListener("click",()=>{
    contactInfoDiv.style.display="none";
    Array.prototype.forEach.call(body, ele=>{
        ele.style.overflow="auto";
    })
    overlayContactInfo.style.display="none";
})


let experienceTitle = Array.from(document.getElementsByClassName("experience__title"));

experienceTitle.forEach((ele)=>{
    ele.addEventListener("click",()=>{
        let experienceList = ele.nextElementSibling;
        experienceList.style.display="block";
        experienceList.style.backgroundColor="white";
    })
})

let experienceClose = Array.from(document.getElementsByClassName("experience__close"));

experienceClose.forEach((ele)=>{
    ele.addEventListener("click",()=>{
        ele.parentNode.style.display="none";
    })
})


let closeEdit = document.getElementById("edit__overlay__form__close");
let overlayEdit = document.getElementById("edit__overlay");
let openEdit = document.querySelector("#main-avatar div");
let submitEdit = document.querySelector("#edit__overlay__form__main button")
console.log(openEdit);

closeEdit.addEventListener("click", ()=>{
    overlayEdit.style.display="none";
    body.forEach((ele)=>{
        ele.style.overflow="auto";
    })
})

submitEdit.addEventListener("click", ()=>{
    overlayEdit.style.display="none";
    body.forEach((ele)=>{
        ele.style.overflow="auto";
    })
})

openEdit.addEventListener("click", ()=>{
    overlayEdit.style.display="block";
    body.forEach((ele)=>{
        ele.style.overflow="hidden";
    })
})




