import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { introStyle } from './intro-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../../components/layouts/Header/Intro/IntroHeader';
import '../../components/layouts/Footer/Footer';
import '../../components/Button/Button';

class Intro extends MaleficComponent {
    static get styles() {
        return [introStyle];
    }
    
    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
    }
    
    render() {
        return html`
            ${commonStyles}
            
            <app-intro-header></app-intro-header>
            
            <section class="find-jobs-section">
                <div class="row">
                    <h2>Find open jobs and internships</h2>
                    <p class="p-long">Here we have a large number of good jobs in every fields for you to choose. Let's find
                        the
                        job which is suitable for you and apply for it now!</p>
                </div>
                <div class="row">
                    <div class="col span-1-of-4 jobs-picture">
                        <img src="content/images/IT.jpeg" alt="job1">
                        <p class="job-title">Information Technology</p>
                    </div>
                    <div class="col span-1-of-4 jobs-picture">
                        <img src="content/images/engineering2.jpeg" alt="job2">
                        <p class="job-title">Engineering</p>
                    </div>
                    <div class="col span-1-of-4 jobs-picture">
                        <img src="content/images/designer.jpg" alt="job3">
                        <p class="job-title">Art And Design</p>
                    </div>
                    <div class="col span-1-of-4 jobs-picture">
                        <img src="content/images/human-res.jpeg" alt="job4">
                        <p class="job-title">Human Resources</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col span-1-of-4 jobs-picture">
                        <img src="content/images/finance.jpeg" alt="job5">
                        <p class="job-title">Finance</p>
                    </div>
                    <div class="col span-1-of-4 jobs-picture">
                        <img src="content/images/Woman-and-personal-assistant-1446702447-600x360.jpeg" alt="job2">
                        <p class="job-title">Assistant</p>
                    </div>
                    <div class="col span-1-of-4 jobs-picture">
                        <img src="content/images/marketing.jpeg" alt="job3">
                        <p class="job-title">Marketing</p>
                    </div>
                    <div class="col span-1-of-4 jobs-picture">
                        <img src="content/images/customer-service.png" alt="job4">
                        <p class="job-title">Customer Service</p>
                    </div>
                </div>
            </section>

            <section class="post-jobs-section">
                <div class="row">
                    <div class="col span-1-of-3">
                        <h2>Post your job and find your employees</h2>
                        <p class="p-long">We welcome all companies and organizations to post a job detail. Our mission is to
                            connect
                            recruiters with the right candidates!</p>
                        <app-button btnclass="btn-post">Post A Job</app-button>
                    </div>
                    <div class="col span-2-of-3 job-picture">
                        <img src="content/images/post-jobs.png" alt="post-a-job-picture">
                    </div>
                </div>
            </section>

            <section class="connect-people-section">
                <div class="row">
                    <div class="col span-2-of-3">
                        <img src="content/images/connect-people.png" alt="post-a-job-picture">
                    </div>
                    <div class="col span-1-of-3 job-picture">
                        <h2>Connect with anyone that can helps you</h2>
                        <p class="p-long">Try searching for your co-worker, classmate, professor, or friend. Conversations
                            today
                            could lead to opportunity tomorrow</p>
                        <app-button btnclass="btn-post">Find people</a>
                    </div>
                </div>
            </section>
            
            <app-footer></app-footer>
        `;
    }
}

customElements.define('app-intro', Intro);
