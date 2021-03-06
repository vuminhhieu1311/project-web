import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { footerStyle } from './footer-style';
import { commonStyles } from '../../../shared/styles/common-styles';

class Footer extends MaleficComponent {
    static get styles() {
        return [footerStyle];
    }
    
    render() {
        return html`
            ${commonStyles}
            
            <footer>
                <section class="footer-text">
                    <div class="row">
                        <div class="col span-1-of-3 col-footer">
                            <p>About Us</p>
                            <ul class="text-footer">
                                <li>
                                    <a href="#">Intro</a>
                                </li>
                                <li>
                                    <a href="#">About Us</a>
                                </li>
                                <li>
                                    <a href="#">Contact Us</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col span-1-of-3 col-footer">
                            <p>Terms & Conditions</p>
                            <ul class="text-footer">
                                <li>
                                    <a href="#">Privacy Policy</a>
                                </li>
                                <li>
                                    <a href="#">Complaint Handling</a>
                                </li>
                                <li>
                                    <a href="#">Term & Conditions</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col span-1-of-3 col-footer">
                            <p>Copyright &copy JobsGo</p>
                            <p>Tax code: 0312192258</p>
                            <div class="contact-detail">
                                <ul class="information">
                                    <li><i class="fas fa-map-marker-alt small-icon"></i>Address: 21 Le Duan, Ha Noi</li>
                                    <li><i class="fas fa-envelope small-icon"></i>Email: jobsgo@gmail.com</li>
                                    <li><i class="fas fa-phone small-icon"></i>S??T: (+084 )099-923-232-322</li>
                                </ul>
                            </div>
                            <div class="social-contact">
                                <ul class="social-icons">
                                    <li><i class="fab fa-facebook"></i></li>
                                    <li><i class="fab fa-twitter-square"></i></li>
                                    <li><i class="fab fa-instagram"></i></li>
                                    <li><i class="fab fa-google-plus-square"></i></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </section>
            </footer>
        `;
    }
}

customElements.define('app-footer', Footer);
