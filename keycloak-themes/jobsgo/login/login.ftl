<#import "template.ftl" as layout>
<@layout.registrationLayout title="JobsGo Login" displayMessage=!messagesPerField.existsError('username','password'); section>
    <#if section = "header">
        <div id="left-header">
            <a href="#"><img class="logo" src="${url.resourcesPath}/images/Logo_official.png" alt="logo"/></a>
        </div>
    <#elseif section = "form">
        <div class="form">
            <#if realm.password>
                <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}"
                      method="post">
                    <h1>SIGN IN</h1>

                    <div class="input" id="username-div">
                        <#if usernameEditDisabled??>
                            <input tabindex="1" id="username" name="username"
                                   value="${(login.username!'')}" type="text" disabled/>
                        <#else>
                            <input tabindex="1" id="username" name="username"
                                   placeholder="<#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if>"
                                   value="${(login.username!'')}" type="text" autofocus autocomplete="off"
                                   aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>"
                            />
                        </#if>
                    </div>

                    <div class="input" id="password-div">
                        <input tabindex="2" id="password" name="password"
                               placeholder="${msg("password")}"
                               type="password" autocomplete="off"
                               aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>"
                        />
                    </div>

                    <#if messagesPerField.existsError('username','password')>
                        <span id="input-error" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
                            ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                        </span>
                    </#if>

                    <div id="kc-form-options">
                        <#if realm.rememberMe && !usernameEditDisabled??>
                            <div class="checkbox">
                                <label>
                                    <#if login.rememberMe??>
                                        <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox"
                                               checked> ${msg("rememberMe")}
                                    <#else>
                                        <input tabindex="3" id="rememberMe" name="rememberMe"
                                               type="checkbox"> ${msg("rememberMe")}
                                    </#if>
                                </label>
                            </div>
                        </#if>
                    </div>

                    <div id="kc-form-buttons">
                        <input type="hidden" id="id-hidden-input" name="credentialId"
                               <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
                        <button name="login" id="btn" type="submit" value="${msg("doLogIn")}">
                            <a>Sign In</a>
                        </button>
                    </div>
                    <#if realm.resetPasswordAllowed>
                        <h5><a tabindex="5"
                               href="${url.loginResetCredentialsUrl}">${msg("doForgotPassword")}</a></h5>
                    </#if>

                    <#if realm.password && social.providers??>
                        <h5 style="margin-top: 10px">${msg("identity-provider-login-label")}</h5>

                        <div id="icon-div" class="${properties.kcFormSocialAccountListClass!} <#if social.providers?size gt 3>${properties.kcFormSocialAccountListGridClass!}</#if>">
                            <#list social.providers as p>
                                <a id="social-${p.alias}"
                                   class="${properties.kcFormSocialAccountListButtonClass!} <#if social.providers?size gt 3>${properties.kcFormSocialAccountGridItem!}</#if>"
                                   type="button" href="${p.loginUrl}">
                                        <img id="google-icon" src="${url.resourcesPath}/images/${p.alias}_icon.svg">
                                </a>
                            </#list>
                        </div>
                    </#if>

                    <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
                        <h5 style="margin-top: 20px">Don't have an account? <a href="${url.registrationUrl}">Join now</a></h5>
                    </#if>
                </form>
            </#if>
        </div>

    </#if>

</@layout.registrationLayout>
