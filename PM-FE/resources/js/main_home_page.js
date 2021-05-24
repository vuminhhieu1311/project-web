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
        background-color: white;
        margin-bottom: 0 !important;
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

    .like-post, .like-comment{
        color:#FF3A83 !important;
    }
    /*Comment======================================*/
    .comment{
        background-color: whitesmoke;
        padding: 10px;
        margin-top: 0 !important;
        border-top: 0.5px solid rgb(201, 201, 201);
        display: none;
    }

    .display-comment{
        display: block;
    }

    .comment__card{
        background-color: white;
        display: flex;
        border-radius: 8px;
    }

    .comment__card__content{
        display: flex;
        flex-direction: column;
        margin-left: 10px;
    }

    .comment__card__avatar{
        padding: 5px;
    }

    #comment__card__avatar{
        height: 30px;
        width: 30px;
    }

    .comment__card__content__name a{
        font-weight: bold;
        color: blue;
        text-decoration: none;
    }

    .comment__card__content__detail {
        margin-left: 5px;
    }

    .comment__card__content__detail p{
        margin: 0;
    }

    .comment__card__content__react{
        display: flex;
    }

    .comment__card__content__react div{
        font-size:12px;
        margin-right: 10px;
        font-weight: bold;
        color: royalblue;
        cursor: pointer;
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
            <div class="react-icon" id="react-icon-like"><slot name="react-like"/></div>
        </div>
        <div>
            <div class="react-icon" id="react-icon-comment"><slot name="react-comment"/></div>
        </div>
        <div>
            <div class="react-icon"><slot name="react-share"/></div>
        </div>
    </div>
</div>

<div class="comment" id="comment">
    <div class="comment__card">
        <div class="comment__card__avatar">
            <img id="comment__card__avatar" src="https://cdn.theculturetrip.com/wp-content/uploads/2018/05/shutterstock_89159080.jpg">
        </div>

        <div class="comment__card__content">
            <div class="comment__card__content__name">
                <a href="#">Some account</a>
            </div>

            <div class="comment__card__content__detail">
                <p>This is a beautiful sight!</p>
            </div>
            
            <div class="comment__card__content__react">
                <div id="comment__card__react__like" > Like </div>
                <div id="comment__card__react__reply"> Reply </div>
            </div>
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

    connectedCallback(){
        let likeIcon = this.shadowRoot.getElementById("react-icon-like");
        let likeComment = this.shadowRoot.getElementById("comment__card__react__like");
        let comment = this.shadowRoot.getElementById("comment");
        let commentIcon = this.shadowRoot.getElementById("react-icon-comment");

        commentIcon.addEventListener("click",()=>{
            comment.classList.toggle("display-comment");
            console.log("test");
        })

        likeIcon.addEventListener("click", ()=>{
            likeIcon.classList.toggle("like-post");
        })

        likeComment.addEventListener("click", ()=>{
            likeComment.classList.toggle("like-comment");
            console.log("test");
        })
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


