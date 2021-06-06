const briefJobTemplate = document.createElement('template');
briefJobTemplate.innerHTML=`
<style>


    #brief__info{
        display: flex;
        background-color: white;
    }

    #brief__info__content:hover{
        background-color: rgb(196, 221, 255);
        cursor: pointer;
    }

    #brief__info__content{
        padding: 0;
        padding-left: 10px;
        padding-top: 5px;
        padding-bottom: 5px;
        margin: 0;
        background-color: white;
        width: 100%;
        border-top: 1px solid black;
    }

    #brief__info__content p{
        margin: 0;
    }

    #brief__info__content h3{
        margin-bottom: 5px;
        margin-top: 0;
        margin-top: 5px;
    }

    #brief__info__content a{
        color: black;
        text-decoration: none;
    }
</style>

<div id="brief__info">
    <div id="brief__info__avatar">
        <img id="brief__info__avatar__img" height="60px" width="60px">
    </div>
                
    <div id="brief__info__content">
        <h3><a href="#"><slot name="jobName"/></a></h3>
        <p style="font-size:12px"><slot name="companyName"/></p>
        <p style="font-size:12px"><slot name="address"/></p>
        <span style="font-size:12px"><slot name="time"/></span>
        <span style="font-size:12px; color: rgb(54, 87, 185); font-weight: bold; margin-left: 5px;"><slot name="applicant"/></span>
    </div>
</div>`

class briefJobCard extends HTMLElement{
    constructor(){
        super();

        this.attachShadow({mode: 'open'});
        this.shadowRoot.appendChild(briefJobTemplate.content.cloneNode(true));

        this.shadowRoot.getElementById("brief__info__avatar__img").src = this.getAttribute('avatar');
    }
    connectedCallback(){
        console.log('connectedcallback');
    }
}

window.customElements.define('brief-job-card', briefJobCard);

const recruitDetailTemplate = document.createElement('template');
recruitDetailTemplate.innerHTML=`
<style>
    #recruit__info{
        background-color: white;
        border-radius: 8px;
        padding: 10px;
    }

    #recruit__info__overview{
        display: flex;
    }

    #recruit__info__overview h3{
        margin: 10px 0 0 0;
    }

    #recruit__info__overview span{
        font-size: 15px;
    }

    #recruit__info__company__avatar{
        display: block;
        margin: 10px;
    }

    #recruit__info__web__feature{
        display: flex;
    }

    #recruit__info__web__feature div{
        background-color: blue;
        margin: 10px;
        border-radius: 20px;
        color: white;
        font-weight: bold;
        padding: 10px;
    }

    #recruit__info__brief__info{
        display: grid;
        grid-template-columns: 1fr 1fr;
        background-color: white;
        gap: 10px;
    }

    #recruit__info__brief__info div{
        background-color: white;
        padding: 10px;
        border: 0.1px solid  rgb(199, 199, 199);
        border-radius: 8px;
    }

    #recruit__info__brief__info h4{
        margin: 0;
        background-color: white;
    }

    #recruit__info__brief__info ul{
        margin: 0;
    }

    #recruit__info__detail__job__criteria{
        display: grid;
        grid-template-columns: 1fr 4fr;
        margin: 10px;
    }

    .recruit__info__detail__job__criteria__tag{
        font-weight: bold;
        font-size: 15px;
        margin: 8px;
    }

    .recruit__info__detail__job__criteria__detail{
        font-size: 15px;
        margin: 8px;
    }

    #popup__apply{
        background-color: aqua;
    }

</style>

    <div id="recruit__info">
        <div id="recruit__info__overview">
            <img id="recruit__info__company__avatar" height="60px" width="60px">
            <div>
                <h3>XYZ</h3>
                <span><slot name="address"/></span>
                <span><slot name="time"/></span>
                <div id="recruit__info__web__feature">
                    <div id="recruit__info__web__feature__apply">Apply</div>
                    <div>Save</div>
                </div>
            </div>
        </div>

        <div id="recruit__info__brief__info">
            <div id="recruit__info__brief__job">
                <h4>Job</h4>
                <ul>
                    <li>5 applicants</li>
                    <li>Entry level</li>
                </ul>
            </div>
            <div id="recruit__info__brief__company">
                <h4>Company</h4>
                <ul>
                    <li>100 employees</li>
                    <li>Industry</li>
                </ul>
            </div>
        </div>

        <div id="recruit__info__detail__job">
            <h3>Description</h3>
                <div style="text-indent: 20px;"><slot name="description"/></div>
                <div id="recruit__info__detail__job__criteria">
                    <div class="recruit__info__detail__job__criteria__tag">Requirement</div>
                        <div class="recruit__info__detail__job__criteria__detail"><slot name="requirement"/> </div>    
                    <div class="recruit__info__detail__job__criteria__tag">Seniority Level</div>
                        <div class="recruit__info__detail__job__criteria__detail"><slot name="level"/> </div> 
                    <div class="recruit__info__detail__job__criteria__tag">Employment Type</div>
                        <div class="recruit__info__detail__job__criteria__detail"><slot name="type"/> </div> 
                    <div class="recruit__info__detail__job__criteria__tag">Industry</div>
                        <div class="recruit__info__detail__job__criteria__detail"><slot name="industry"/> </div> 
                    <div class="recruit__info__detail__job__criteria__tag">Job Functions</div>
                        <div class="recruit__info__detail__job__criteria__detail"><slot name="jobFunction"/> </div> 
                </div>
        </div>

    </div>
`

class recruitDetailCard extends HTMLElement{
    constructor(){
        super();

        this.attachShadow({mode: 'open'});
        this.shadowRoot.appendChild(recruitDetailTemplate.content.cloneNode(true));

        this.shadowRoot.getElementById("recruit__info__company__avatar").src = this.getAttribute('avatar');
    }
    connectedCallback(){
        console.log('connectedcallback-recruit');
        /*=====================================================================================================*/
        let apply = this.shadowRoot.getElementById("recruit__info__web__feature__apply");
        let applyForm = document.getElementById("overlay__apply");
        let closeApply = document.getElementById("popup__apply__header__close");

        apply.addEventListener("click",()=>{
            applyForm.style.display="block";
            console.log(applyForm);
        })

        closeApply.addEventListener("click",()=>{
            applyForm.style.display="none";
        })
    }
}

window.customElements.define('recruit-detail-card', recruitDetailCard);

/*---------------------------------------------*/
let jobTypeDrop = document.getElementById("search__filter__jobtype");
let createdAtDrop = document.getElementById("search__filter__createdat");

jobTypeDrop.addEventListener("click",()=>{
    let overlayJobType = document.getElementById("overlay__jobtype");
    overlayJobType.classList.toggle("overlay");
    console.log(overlayJobType);
})

createdAtDrop.addEventListener("click", ()=>{
    let overlayCreatedAtDrop = document.getElementById("overlay__createdat");
    overlayCreatedAtDrop.classList.toggle("overlay");
})


/*=========================================================*/
