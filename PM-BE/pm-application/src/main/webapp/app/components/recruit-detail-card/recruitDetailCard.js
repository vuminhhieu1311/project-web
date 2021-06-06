import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { recruitDetailCardStyle } from './recruitDetailCard-style';

import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/layouts/Footer/Footer';

class RecruitDetailCard extends MaleficComponent{
    static get properties(){
        return{
            companyName:{type:String},
            address:{type:String},
            time:{type:String},
            jobBriefInfo:{type:String},
            companyBriefInfo:{type:String},
            requirement:{type:String},
            level:{type:String},
            type:{type:String},
            function:{type:String},
            industry:{type:String},
            description:{type:String},
            avatar:{type:String},
            showApply: {type:String}
        }
    }

    static get styles(){
        return [recruitDetailCardStyle];
    }

    handleShowApply(){
        this.showApply = "block";
    }

    handleCloseApply(){
        this.showApply = "none";
    }

    constructor(){
        super();
    }

    render(){
        return html`
            ${commonStyles}
            <div id="recruit__info">
        <div id="recruit__info__overview">
            <img id="recruit__info__company__avatar" height="60px" width="60px" src=${this.avatar}>
            <div>
                <h3>${this.companyName}</h3>
                <span>${this.address}</span>
                <span>${this.time}</span>
                <div id="recruit__info__web__feature">
                    <div @click="${this.handleShowApply}">Apply</div>
                    <div>Save</div>
                </div> 
            </div>
        </div>

        <div id="recruit__info__brief__info">
            <div id="recruit__info__brief__job">
                <h4>Job</h4>
                <ul>
                    ${this.jobBriefInfo}
                </ul>
            </div>
            <div id="recruit__info__brief__company">
                <h4>Company</h4>
                <ul>
                    ${this.companyBriefInfo}
                </ul>
            </div>
        </div>

        <div id="recruit__info__detail__job">
            <h3>Description</h3>
                <div style="text-indent: 20px;">${this.description}</div>
                <div id="recruit__info__detail__job__criteria">
                    <div class="recruit__info__detail__job__criteria__tag">Requirement</div>
                        <div class="recruit__info__detail__job__criteria__detail">${this.requirement} </div>    
                    <div class="recruit__info__detail__job__criteria__tag">Seniority Level</div>
                        <div class="recruit__info__detail__job__criteria__detail">${this.level} </div> 
                    <div class="recruit__info__detail__job__criteria__tag">Employment Type</div>
                        <div class="recruit__info__detail__job__criteria__detail">${this.type} </div> 
                    <div class="recruit__info__detail__job__criteria__tag">Industry</div>
                        <div class="recruit__info__detail__job__criteria__detail">${this.industry} </div> 
                    <div class="recruit__info__detail__job__criteria__tag">Job Functions</div>
                        <div class="recruit__info__detail__job__criteria__detail">${this.function} </div> 
                </div>
        </div>

        <div id="overlay__apply" style="display:${this.showApply}">
        <div id="popup__apply">
            <div id="popup__apply__header">
                <div id="popup__apply__header__text">Apply to abc</div>
                <div id="popup__apply__header__close" @click="${this.handleCloseApply}"><i class="fa fa-times"></i></div>
            </div>

            <div id="popup__apply__form">
                <form>

                        <div id="popup__apply__form__contactinfo__basic">
                            <img id="form__contactinfo__basic__image" src="https://i.pinimg.com/originals/d5/7e/f5/d57ef5d4d6bae63dce5b5c8a14f200e7.jpg">
                            <div>
                                <p id="form__contactinfo__basic__name">Nguyen Van A</p>
                                <p id="form__contactinfo__basic__position">
                                    Student at HUST
                                </p>
                                <p id="form__contactinfo__basic__location">
                                    Hanoi
                                </p>
                            </div>
                        </div>

                        <div id="popup__apply__form__contactinfo__detail">
                            <label for="mail">Email address *</label></br>
                            <input type="email" name="email" id="email" class="contact__info__input" required></br>
                            <label>Phone country code *</label></br>
                            
                            <select id="country__code" name="country__code" class="contact__info__input" required>  <option value="">Select an option</option>

                                <option value="urn:li:country:af">Afghanistan (+93)</option>
                                <option value="urn:li:country:ax">Aland Islands (+358)</option>
                                <option value="urn:li:country:al">Albania (+355)</option>
                                <option value="urn:li:country:dz">Algeria (+213)</option>
                                <option value="urn:li:country:as">American Samoa (+1)</option>
                                <option value="urn:li:country:ad">Andorra (+376)</option>
                                <option value="urn:li:country:ao">Angola (+244)</option>
                                <option value="urn:li:country:ai">Anguilla (+1)</option>
                                <option value="urn:li:country:aq">Antarctica (+0)</option>
                                <option value="urn:li:country:ag">Antigua and Barbuda (+1)</option>
                                <option value="urn:li:country:ar">Argentina (+54)</option>
                                <option value="urn:li:country:am">Armenia (+374)</option>
                                <option value="urn:li:country:aw">Aruba (+297)</option>
                                <option value="urn:li:country:au">Australia (+61)</option>
                                <option value="urn:li:country:at">Austria (+43)</option>
                                <option value="urn:li:country:az">Azerbaijan (+994)</option>
                                <option value="urn:li:country:bs">Bahamas (+1)</option>
                                <option value="urn:li:country:bh">Bahrain (+973)</option>
                                <option value="urn:li:country:bd">Bangladesh (+880)</option>
                                <option value="urn:li:country:bb">Barbados (+1)</option>
                                <option value="urn:li:country:by">Belarus (+375)</option>
                                <option value="urn:li:country:be">Belgium (+32)</option>
                                <option value="urn:li:country:bz">Belize (+501)</option>
                                <option value="urn:li:country:bj">Benin (+229)</option>
                                <option value="urn:li:country:bm">Bermuda (+1)</option>
                                <option value="urn:li:country:bt">Bhutan (+975)</option>
                                <option value="urn:li:country:bo">Bolivia (+591)</option>
                                <option value="urn:li:country:ba">Bosnia and Herzegovina (+387)</option>
                                <option value="urn:li:country:bw">Botswana (+267)</option>
                                <option value="urn:li:country:bv">Bouvet Island (+0)</option>
                                <option value="urn:li:country:br">Brazil (+55)</option>
                                <option value="urn:li:country:io">British Indian Ocean Territory (+246)</option>
                                <option value="urn:li:country:bn">Brunei Darussalam (+673)</option>
                                <option value="urn:li:country:bg">Bulgaria (+359)</option>
                                <option value="urn:li:country:bf">Burkina Faso (+226)</option>
                                <option value="urn:li:country:bi">Burundi (+257)</option>
                                <option value="urn:li:country:kh">Cambodia (+855)</option>
                                <option value="urn:li:country:cm">Cameroon (+237)</option>
                                <option value="urn:li:country:ca">Canada (+1)</option>
                                <option value="urn:li:country:cv">Cape Verde (+238)</option>
                                <option value="urn:li:country:cb">Caribbean Nations (+0)</option>
                                <option value="urn:li:country:ky">Cayman Islands (+1)</option>
                                <option value="urn:li:country:cf">Central African Republic (+236)</option>
                                <option value="urn:li:country:td">Chad (+235)</option>
                                <option value="urn:li:country:cl">Chile (+56)</option>
                                <option value="urn:li:country:cn">China (+86)</option>
                                <option value="urn:li:country:cx">Christmas Island (+61)</option>
                                <option value="urn:li:country:cc">Cocos (Keeling) Islands (+61)</option>
                                <option value="urn:li:country:co">Colombia (+57)</option>
                                <option value="urn:li:country:km">Comoros (+269)</option>
                                <option value="urn:li:country:cg">Congo (+242)</option>
                                <option value="urn:li:country:ck">Cook Islands (+682)</option>
                                <option value="urn:li:country:cr">Costa Rica (+506)</option>
                                <option value="urn:li:country:ci">Cote D’Ivoire (Ivory Coast) (+225)</option>
                                <option value="urn:li:country:hr">Croatia (+385)</option>
                                <option value="urn:li:country:cu">Cuba (+53)</option>
                                <option value="urn:li:country:cy">Cyprus (+357)</option>
                                <option value="urn:li:country:cz">Czech Republic (+420)</option>
                                <option value="urn:li:country:cd">Democratic Republic of the Congo (+243)</option>
                                <option value="urn:li:country:dk">Denmark (+45)</option>
                                <option value="urn:li:country:dj">Djibouti (+253)</option>
                                <option value="urn:li:country:dm">Dominica (+1)</option>
                                <option value="urn:li:country:do">Dominican Republic (+1)</option>
                                <option value="urn:li:country:ec">Ecuador (+593)</option>
                                <option value="urn:li:country:eg">Egypt (+20)</option>
                                <option value="urn:li:country:sv">El Salvador (+503)</option>
                                <option value="urn:li:country:gq">Equatorial Guinea (+240)</option>
                                <option value="urn:li:country:er">Eritrea (+291)</option>
                                <option value="urn:li:country:ee">Estonia (+372)</option>
                                <option value="urn:li:country:et">Ethiopia (+251)</option>
                                <option value="urn:li:country:fk">Falkland Islands (Malvinas) (+500)</option>
                                <option value="urn:li:country:fo">Faroe Islands (+298)</option>
                                <option value="urn:li:country:fm">Federated States of Micronesia (+691)</option>
                                <option value="urn:li:country:fj">Fiji (+679)</option>
                                <option value="urn:li:country:fi">Finland (+358)</option>
                                <option value="urn:li:country:fr">France (+33)</option>
                                <option value="urn:li:country:gf">French Guiana (+594)</option>
                                <option value="urn:li:country:pf">French Polynesia (+689)</option>
                                <option value="urn:li:country:tf">French Southern Territories (+0)</option>
                                <option value="urn:li:country:ga">Gabon (+241)</option>
                                <option value="urn:li:country:gm">Gambia (+220)</option>
                                <option value="urn:li:country:ge">Georgia (+995)</option>
                                <option value="urn:li:country:de">Germany (+49)</option>
                                <option value="urn:li:country:gh">Ghana (+233)</option>
                                <option value="urn:li:country:gi">Gibraltar (+350)</option>
                                <option value="urn:li:country:gr">Greece (+30)</option>
                                <option value="urn:li:country:gl">Greenland (+299)</option>
                                <option value="urn:li:country:gd">Grenada (+1)</option>
                                <option value="urn:li:country:gp">Guadeloupe (+590)</option>
                                <option value="urn:li:country:gu">Guam (+1)</option>
                                <option value="urn:li:country:gt">Guatemala (+502)</option>
                                <option value="urn:li:country:gg">Guernsey (+44)</option>
                                <option value="urn:li:country:gn">Guinea (+224)</option>
                                <option value="urn:li:country:gw">Guinea-Bissau (+245)</option>
                                <option value="urn:li:country:gy">Guyana (+592)</option>
                                <option value="urn:li:country:ht">Haiti (+509)</option>
                                <option value="urn:li:country:hm">Heard Island and McDonald Islands (+0)</option>
                                <option value="urn:li:country:hn">Honduras (+504)</option>
                                <option value="urn:li:country:hk">Hong Kong (+852)</option>
                                <option value="urn:li:country:hu">Hungary (+36)</option>
                                <option value="urn:li:country:is">Iceland (+354)</option>
                                <option value="urn:li:country:in">India (+91)</option>
                                <option value="urn:li:country:id">Indonesia (+62)</option>
                                <option value="urn:li:country:ir">Iran (+98)</option>
                                <option value="urn:li:country:iq">Iraq (+964)</option>
                                <option value="urn:li:country:ie">Ireland (+353)</option>
                                <option value="urn:li:country:im">Isle of Man (+44)</option>
                                <option value="urn:li:country:il">Israel (+972)</option>
                                <option value="urn:li:country:it">Italy (+39)</option>
                                <option value="urn:li:country:jm">Jamaica (+1)</option>
                                <option value="urn:li:country:jp">Japan (+81)</option>
                                <option value="urn:li:country:je">Jersey (+44)</option>
                                <option value="urn:li:country:jo">Jordan (+962)</option>
                                <option value="urn:li:country:kz">Kazakhstan (+7)</option>
                                <option value="urn:li:country:ke">Kenya (+254)</option>
                                <option value="urn:li:country:ki">Kiribati (+686)</option>
                                <option value="urn:li:country:kr">Korea (+82)</option>
                                <option value="urn:li:country:kp">Korea (North) (+850)</option>
                                <option value="urn:li:country:ko">Kosovo (+0)</option>
                                <option value="urn:li:country:kw">Kuwait (+965)</option>
                                <option value="urn:li:country:kg">Kyrgyzstan (+996)</option>
                                <option value="urn:li:country:la">Laos (+856)</option>
                                <option value="urn:li:country:lv">Latvia (+371)</option>
                                <option value="urn:li:country:lb">Lebanon (+961)</option>
                                <option value="urn:li:country:ls">Lesotho (+266)</option>
                                <option value="urn:li:country:lr">Liberia (+231)</option>
                                <option value="urn:li:country:ly">Libya (+218)</option>
                                <option value="urn:li:country:li">Liechtenstein (+423)</option>
                                <option value="urn:li:country:lt">Lithuania (+370)</option>
                                <option value="urn:li:country:lu">Luxembourg (+352)</option>
                                <option value="urn:li:country:mo">Macao (+853)</option>
                                <option value="urn:li:country:mk">Macedonia (+389)</option>
                                <option value="urn:li:country:mg">Madagascar (+261)</option>
                                <option value="urn:li:country:mw">Malawi (+265)</option>
                                <option value="urn:li:country:my">Malaysia (+60)</option>
                                <option value="urn:li:country:mv">Maldives (+960)</option>
                                <option value="urn:li:country:ml">Mali (+223)</option>
                                <option value="urn:li:country:mt">Malta (+356)</option>
                                <option value="urn:li:country:mh">Marshall Islands (+692)</option>
                                <option value="urn:li:country:mq">Martinique (+596)</option>
                                <option value="urn:li:country:mr">Mauritania (+222)</option>
                                <option value="urn:li:country:mu">Mauritius (+230)</option>
                                <option value="urn:li:country:yt">Mayotte (+262)</option>
                                <option value="urn:li:country:mx">Mexico (+52)</option>
                                <option value="urn:li:country:md">Moldova (+373)</option>
                                <option value="urn:li:country:mc">Monaco (+377)</option>
                                <option value="urn:li:country:mn">Mongolia (+976)</option>
                                <option value="urn:li:country:me">Montenegro (+382)</option>
                                <option value="urn:li:country:ms">Montserrat (+1)</option>
                                <option value="urn:li:country:ma">Morocco (+212)</option>
                                <option value="urn:li:country:mz">Mozambique (+258)</option>
                                <option value="urn:li:country:mm">Myanmar (+95)</option>
                                <option value="urn:li:country:na">Namibia (+264)</option>
                                <option value="urn:li:country:nr">Nauru (+674)</option>
                                <option value="urn:li:country:np">Nepal (+977)</option>
                                <option value="urn:li:country:nl">Netherlands (+31)</option>
                                <option value="urn:li:country:an">Netherlands Antilles (+0)</option>
                                <option value="urn:li:country:nc">New Caledonia (+687)</option>
                                <option value="urn:li:country:nz">New Zealand (+64)</option>
                                <option value="urn:li:country:ni">Nicaragua (+505)</option>
                                <option value="urn:li:country:ne">Niger (+227)</option>
                                <option value="urn:li:country:ng">Nigeria (+234)</option>
                                <option value="urn:li:country:nu">Niue (+683)</option>
                                <option value="urn:li:country:nf">Norfolk Island (+672)</option>
                                <option value="urn:li:country:mp">Northern Mariana Islands (+1)</option>
                                <option value="urn:li:country:no">Norway (+47)</option>
                                <option value="urn:li:country:pk">Pakistan (+92)</option>
                                <option value="urn:li:country:pw">Palau (+680)</option>
                                <option value="urn:li:country:ps">Palestinian Territory (+970)</option>
                                <option value="urn:li:country:pa">Panama (+507)</option>
                                <option value="urn:li:country:pg">Papua New Guinea (+675)</option>
                                <option value="urn:li:country:py">Paraguay (+595)</option>
                                <option value="urn:li:country:pe">Peru (+51)</option>
                                <option value="urn:li:country:ph">Philippines (+63)</option>
                                <option value="urn:li:country:pn">Pitcairn (+0)</option>
                                <option value="urn:li:country:pl">Poland (+48)</option>
                                <option value="urn:li:country:pt">Portugal (+351)</option>
                                <option value="urn:li:country:pr">Puerto Rico (+1)</option>
                                <option value="urn:li:country:qa">Qatar (+974)</option>
                                <option value="urn:li:country:re">Reunion (+262)</option>
                                <option value="urn:li:country:ro">Romania (+40)</option>
                                <option value="urn:li:country:ru">Russian Federation (+7)</option>
                                <option value="urn:li:country:rw">Rwanda (+250)</option>
                                <option value="urn:li:country:gs">S. Georgia and S. Sandwich Islands (+0)</option>
                                <option value="urn:li:country:sh">Saint Helena (+290)</option>
                                <option value="urn:li:country:kn">Saint Kitts and Nevis (+1)</option>
                                <option value="urn:li:country:lc">Saint Lucia (+1)</option>
                                <option value="urn:li:country:pm">Saint Pierre and Miquelon (+508)</option>
                                <option value="urn:li:country:vc">Saint Vincent and the Grenadines (+1)</option>
                                <option value="urn:li:country:ws">Samoa (+685)</option>
                                <option value="urn:li:country:sm">San Marino (+378)</option>
                                <option value="urn:li:country:st">Sao Tome and Principe (+239)</option>
                                <option value="urn:li:country:sa">Saudi Arabia (+966)</option>
                                <option value="urn:li:country:sn">Senegal (+221)</option>
                                <option value="urn:li:country:rs">Serbia (+381)</option>
                                <option value="urn:li:country:cs">Serbia and Montenegro (+0)</option>
                                <option value="urn:li:country:sc">Seychelles (+248)</option>
                                <option value="urn:li:country:sl">Sierra Leone (+232)</option>
                                <option value="urn:li:country:sg">Singapore (+65)</option>
                                <option value="urn:li:country:sk">Slovak Republic (+421)</option>
                                <option value="urn:li:country:si">Slovenia (+386)</option>
                                <option value="urn:li:country:sb">Solomon Islands (+677)</option>
                                <option value="urn:li:country:so">Somalia (+252)</option>
                                <option value="urn:li:country:za">South Africa (+27)</option>
                                <option value="urn:li:country:ss">South Sudan (+211)</option>
                                <option value="urn:li:country:es">Spain (+34)</option>
                                <option value="urn:li:country:lk">Sri Lanka (+94)</option>
                                <option value="urn:li:country:sd">Sudan (+249)</option>
                                <option value="urn:li:country:om">Sultanate of Oman (+968)</option>
                                <option value="urn:li:country:sr">Suriname (+597)</option>
                                <option value="urn:li:country:sj">Svalbard and Jan Mayen (+47)</option>
                                <option value="urn:li:country:sz">Swaziland (+268)</option>
                                <option value="urn:li:country:se">Sweden (+46)</option>
                                <option value="urn:li:country:ch">Switzerland (+41)</option>
                                <option value="urn:li:country:sy">Syria (+963)</option>
                                <option value="urn:li:country:tw">Taiwan (+886)</option>
                                <option value="urn:li:country:tj">Tajikistan (+992)</option>
                                <option value="urn:li:country:tz">Tanzania (+255)</option>
                                <option value="urn:li:country:th">Thailand (+66)</option>
                                <option value="urn:li:country:tl">Timor-Leste (+670)</option>
                                <option value="urn:li:country:tg">Togo (+228)</option>
                                <option value="urn:li:country:tk">Tokelau (+690)</option>
                                <option value="urn:li:country:to">Tonga (+676)</option>
                                <option value="urn:li:country:tt">Trinidad and Tobago (+1)</option>
                                <option value="urn:li:country:tn">Tunisia (+216)</option>
                                <option value="urn:li:country:tr">Turkey (+90)</option>
                                <option value="urn:li:country:tm">Turkmenistan (+993)</option>
                                <option value="urn:li:country:tc">Turks and Caicos Islands (+1)</option>
                                <option value="urn:li:country:tv">Tuvalu (+688)</option>
                                <option value="urn:li:country:ug">Uganda (+256)</option>
                                <option value="urn:li:country:ua">Ukraine (+380)</option>
                                <option value="urn:li:country:ae">United Arab Emirates (+971)</option>
                                <option value="urn:li:country:gb">United Kingdom (+44)</option>
                                <option value="urn:li:country:us">United States (+1)</option>
                                <option value="urn:li:country:uy">Uruguay (+598)</option>
                                <option value="urn:li:country:uz">Uzbekistan (+998)</option>
                                <option value="urn:li:country:vu">Vanuatu (+678)</option>
                                <option value="urn:li:country:va">Vatican City State (Holy See) (+39)</option>
                                <option value="urn:li:country:ve">Venezuela (+58)</option>
                                <option value="urn:li:country:vn">Vietnam (+84)</option>
                                <option value="urn:li:country:vg">Virgin Islands (British) (+1)</option>
                                <option value="urn:li:country:vi">Virgin Islands (U.S.) (+1)</option>
                                <option value="urn:li:country:wf">Wallis and Futuna (+681)</option>
                                <option value="urn:li:country:eh">Western Sahara (+212)</option>
                                <option value="urn:li:country:ye">Yemen (+967)</option>
                                <option value="urn:li:country:zm">Zambia (+260)</option>
                                <option value="urn:li:country:zw">Zimbabwe (+263)</option>
                              </select></br>
                            <label>Phone number *</label></br>  
                            <input type="tel" name="phone" id="phone" class="contact__info__input" required></br>  
                        </div>

                        <div id="popup__apply__form__cv">
                            <label for="cv">Upload your CV</label></br>
                            <input type="file" name="cv" id="cv" required>
                        </div>

                        <div id="popup__apply__form__additional__question">

                        </div>
                        <button type="submit">Send</button>
              </form>
            </div>
        </div>
    </div>

    
</div>

    </div>
            `;
    }
}

customElements.define('recruit-detail-card', RecruitDetailCard);
