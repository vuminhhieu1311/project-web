<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true displayMessage=!messagesPerField.existsError('username'); section>
    <#if section = "header">
        <div id="left-header">
            <a href="#"><img class="logo" src="${url.resourcesPath}/images/Logo_official.png" alt="logo"/></a>
        </div>
        <div id="right-header">
            <ul class="main-nav">
                <li><a href="${url.loginUrl}">Sign In</a></li>
                <li><a href="${url.registrationUrl}">Sign Up</a></li>
            </ul>
        </div>
    <#elseif section = "form">
        <div class="form">
            <form id="kc-reset-password-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
                <h2 style="margin: 15px">Forgot Password?</h2>
                <div>
                    <#if auth?has_content && auth.showUsername()>
                        <input class="${properties.kcInputClass!}" type="text"
                               id="username" name="username" autofocus value="${auth.attemptedUsername}" aria-invalid="<#if messagesPerField.existsError('username')>true</#if>"
                               placeholder="<#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if>"
                        />
                    <#else>
                        <input class="${properties.kcInputClass!}" type="text"
                               id="username" name="username" autofocus aria-invalid="<#if messagesPerField.existsError('username')>true</#if>"
                               placeholder="<#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if>"
                        />
                    </#if>

                    <#if messagesPerField.existsError('username')>
                        <span id="input-error-username" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
                                ${kcSanitize(messagesPerField.get('username'))?no_esc}
                        </span>
                    </#if>
                </div>
                <div class="${properties.kcFormGroupClass!} ${properties.kcFormSettingClass!}">
                    <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                        <button id="btn" type="submit" value="${msg("doSubmit")}"><a>Reset Password</a></button>
                    </div>

                    <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                        <div class="${properties.kcFormOptionsWrapperClass!}">
                            <span><a href="${url.loginUrl}">${kcSanitize(msg("backToLogin"))?no_esc}</a></span>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    <#elseif section = "info" >
        <p>*${msg("emailInstruction")}</p>
    </#if>
</@layout.registrationLayout>
