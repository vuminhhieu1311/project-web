let logo = document.getElementById("logo");
let background = document.getElementById("background");
let avatarReview = document.querySelector("#main-avatar img");
let backgroundReview = document.querySelector("#background-avatar img");
let name = document.getElementById("name");
let size = document.getElementById("companySize");
let nameReview = document.querySelector("#company-info h1");
let sizeReview = document.querySelector("#company-info a");


logo.addEventListener("change",()=>{
    const fileLogo = logo.files[0];
    console.log(fileLogo);
    avatarReview.src = URL.createObjectURL(fileLogo);
})

background.addEventListener("change",()=>{
    const fileBackground = background.files[0];
    backgroundReview.src = URL.createObjectURL(fileBackground);
    backgroundReview.style.display="block";
    backgroundReview.style.width="100%";
})

name.addEventListener("keyup",()=>{
    nameReview.innerHTML = name.value;
    console.log(name);
})

size.addEventListener("keyup",()=>{
    sizeReview.innerHTML = size.value + " Employees";
})
