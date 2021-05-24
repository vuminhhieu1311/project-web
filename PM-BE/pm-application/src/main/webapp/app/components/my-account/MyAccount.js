import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { myAccountStyle } from './my-account-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';

class MyAccount extends MaleficComponent {
    static get styles() {
        return [myAccountStyle];
    }

    render() {
        const  days = [];
        const months = [];
        const years = [];
        for(var i = 1; i <= 31; i++) {
            days.push(i);
        }
        for(var i = 1; i <= 12; i++) {
            months.push(i);
        }
        for(var i = 1980; i <= 2004; i++) {
            years.push(i);
        }

        return html`
            ${commonStyles}
         
            <h1>Personal Information</h1>
            <div class="row">
                <div class="col span-1-of-4 title">
                    <h5>First Name</h5>
                    <h5>Last Name</h5>
                    <h5>Email</h5>
                    <h5>Phone Number</h5>
                    <h5>Date Of Birth</h5>
                    <h5>Gender</h5>
                    <h5>Country</h5>
                    <h5>Location</h5>
                    <h5>Address</h5>
                    <h5>About me</h5>
                    <h5>Industry</h5>
                </div>
                <div class="col span-3-of-4 info">
                    <input type="text" class="input" id="first-name" value="admin">
                    <input type="text" class="input" id="last-name" value="Alex">
                    <input type="text" class="input" id="email" value="Hieu@gmail.com">
                    <input type="text" class="input" id="phone" value="0366951607">
                    <div class="dob" data-="selectors">
                        <select class="selector" aria-label="Day" name="birthday_day" id="day">
                            <option value="0">Day</option>
                            ${days.map((day) => 
                                html`<option value="${day}">${day}</option>`
                            )}
                        </select>
                        <select class="selector" aria-label="Month" name="birthday_month" id="month">
                            <option value="0">Month</option>
                            ${months.map((month) => 
                                html`<option value="${month}">${month}</option>`
                            )}
                        </select>
                        <select class="selector" aria-label="Year" name="birthday_year" id="year">
                            <option value="0">Year</option>
                            ${years.map((year) => 
                                html`<option value="${year}">${year}</option>`
                            )}
                        </select>
                    </div>
                    <div class="gender" data-="radio">
                        <span class="select">
                            <input type="radio" name="gender" id="fmale">
                            <label class="choose" for="fmale">Female</label>
                        </span>
                        <span class="select">
                            <input type="radio" name="gender" id="male">
                            <label class="choose" for="male">Male</label>
                        </span>
                        <span class="select">
                            <input type="radio" name="gender" id="other">
                            <label class="choose" for="other">Other</label>
                        </span>
                    </div>
                    <input type="text" class="input" id="country" value="Vietnam">
                    <input type="text" class="input" id="location" value="Hanoi">
                    <input type="text" class="input" id="address" value="294 Minh Khai, Ha Noi">
                    <input type="text" class="input" id="about" value="I am a happy person">
                    <input type="text" class="input" id="industry" value="Information technology">
                    <div class="update-btn">
                        <app-button btnclass="btn-save">Save</app-button>
                        <app-button btnclass="btn-cancel">Cancel</app-button>
                    </div>
                </div>

            </div>
      
        `;
    }
}

customElements.define('my-account', MyAccount);
