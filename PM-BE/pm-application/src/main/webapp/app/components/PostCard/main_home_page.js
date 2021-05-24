const newsTemplate = document.createElement('template');
newsTemplate.innerHTML=`
<style>
    .news-card{
        display: flex;
        flex-direction: column;
        margin: 10px;
        border: 1px solid rgb(201, 201, 201);
        border-radius: 8px;
        padding: 10px 10px 0px 10px;
        background-color: white;
    }
    
    .news-card>div{
        margin: 10px;
    }

    .recruit-info{
        background-color: white;
        border-radius: 8px;
    }

    .news-react{
        background-color: red white;
    }

    #poster-avatar{
        display: block;
        height: 60px;
        width: 60px;
    }

    .poster{
        display: flex;
    }

    .poster-info{
        margin-left: 10px;
    }

    .recruit-info > div{
        margin-bottom: 10px;
    }

    #recruit-image{
        display: block;
        width: 100%;
    }

    iframe{
        width: 100%;
        height: 300px;
    }

    .react{
        height: max-element;
        width: 100%;
        display: flex;
        border-top: 0.5px solid rgb(201, 201, 201);
        background-color: blue white;
    }

    .react>div{
        height: 40px;
        width: 60px;
        font-size: 25px;
        cursor: pointer;
        background-color: brown white;
        margin-left: 5px;
        position: relative;
        border-radius: 8px;
    }

    .react>div:hover{
        background-color: rgb(201, 201, 201);
    }

    .react-icon{
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
    }
</style>

<div class="news-card">
<div class="news-header">
    <div class="poster">
        <img id="poster-avatar"/>
        <div class="poster-info">
            <div style="font-weight: bold; font-size: 18px;"><slot name="name"/></div>
            <div style="font-size: 12px;"><slot name="followers"/></div>
            <div style="font-size: 12px;"><slot name="time"/></div>
        </div>
    </div>
</div>

<div class="recruit-info">
    <div><slot name="recruit-text"/></div>
    <img id="recruit-image"/>
    <iframe></iframe>
</div>

<div class="news-react">
    <div class="react">
        <div>
            <div class="react-icon"><slot name="react-like"/></div>
        </div>
        <div>
            <div class="react-icon"><slot name="react-comment"/></div>
        </div>
        <div>
            <div class="react-icon"><slot name="react-share"/></div>
        </div>
    </div>
</div>
</div>
`;

class newsCard extends HTMLElement{
    constructor(){
        super();

        this.attachShadow({mode: 'open'});
        this.shadowRoot.appendChild(newsTemplate.content.cloneNode(true));

        if(this.getAttribute('avatar')!=null){
            this.shadowRoot.getElementById("poster-avatar").src = this.getAttribute('avatar');
        } else{
            this.shadowRoot.getElementById("poster-avatar").remove();
        }

        if(this.getAttribute('recruitImage')!=null){
            this.shadowRoot.getElementById("recruit-image").src = this.getAttribute('recruitImage');
        } else{
            this.shadowRoot.getElementById("recruit-image").remove();
        }

        if(this.getAttribute('recruitVideo')!=null){
            this.shadowRoot.querySelector('iframe').src = this.getAttribute('recruitVideo');
        } else{
            this.shadowRoot.querySelector('iframe').remove();
        }
    }
}

window.customElements.define('news-card', newsCard);
/*-----------------------------------------------------------*/
let popup = Array.from(document.getElementsByClassName('post__edit__container'));

let showPopupEdit = () =>{
    popup.forEach((ele)=>{
        ele.style.display="block";
    })
}

let closePopupEdit = ()=>{
    popup.forEach((ele)=>{
        ele.style.display="none";
    })
}

/*-----------------------------------------------------------*/

let showScopeOption = ()=>{
    let scopeOption = Array.from(document.getElementsByClassName('post__edit__scope__option'));
    scopeOption.forEach((ele)=>{
        ele.classList.toggle("option__toggle");
    })

    let text = document.querySelectorAll(".post__edit__scope__option input[type='radio']");
    console.log(text.length);
}

let scopeOption = document.querySelectorAll(".post__edit__scope__option input[type='radio']");

scopeOption.forEach((ele)=>{
    ele.addEventListener("click",()=>{
        console.log(ele);
        let scope = document.querySelector(".post__edit__scope span");
        let text ="";
        if(ele.value=="anyone") text = "Anyone";
        else if(ele.value=="onlyme") text = "Only me";
        else text = "Friends";
        scope.textContent = text;
    })
})


