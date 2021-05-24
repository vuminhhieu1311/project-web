import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { smallFooterStyle } from './small-footer-style';
import { commonStyles } from '../../../shared/styles/common-styles';

class SmallFooter extends MaleficComponent {
    static get styles() {
        return [smallFooterStyle];
    }
    
    render() {
        return html`
            ${commonStyles}
            <footer class="small-footer">
            <div class="small-footer-text">
                <div>
                    <a href="#">Home</a>
                    <a href="#">About Us</a>
                    <a href="#">Contact Us</a>
                    <a href="#">Privacy Policy</a>
                    <a href="#">Complaint Handling</a>
                    <a href="#">Term & Conditions</a>
                    <a href="#">Copyright &copy JobsGo</a>
                    <a href="#" style = "border: none">Update your account</a>
                </div>
               <div>
                <a href="#">Cookie Policy</a>
                <a href="#">Send Feedback</a>
                <a href="#">Language</a>
                <a href="#">Advertising</a>
                <a href="#">Cookie Policy</a>
                <a href="#">JobsGo Careers</a>
                <a href="#">Ad Choices</a>
                <a href="#">Pro Finder</a>
                <a href="#" style = "border: none">Sales Solutions</a>
               </div>  
            </div>
        </footer> 
        `;
    }
}

customElements.define('app-small-footer', SmallFooter);
