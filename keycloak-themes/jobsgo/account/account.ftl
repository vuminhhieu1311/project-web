<#import "template.ftl" as layout>
<@layout.mainLayout active='account' bodyClass='user'; section>
    <#if section = "content">
        <div class="profile tab-show">
            <h1>${msg("editAccountHtmlTitle")}</h1>
            <div class="row">
                <div class="col span-2-of-3">
                    <h3>Manage your account</h3>
                </div>
                <div class="col span-1-of-3">
                    <span class="subtitle"><span class="required">*</span> ${msg("requiredFields")}</span>
                </div>
            </div>

            <div class="row">
                <div class="col span-1-of-4 title">
                    <h5 ${messagesPerField.printIfExists('username','has-error')}>
                        ${msg("username")} <#if realm.editUsernameAllowed><span class="required">*</span></#if>
                    </h5>
                    <h5 ${messagesPerField.printIfExists('email','has-error')}>
                        ${msg("email")} <span class="required">*</span>
                    </h5>
                    <h5 ${messagesPerField.printIfExists('firstName','has-error')}>
                        ${msg("firstName")} <span class="required">*</span>
                    </h5>
                    <h5 ${messagesPerField.printIfExists('lastName','has-error')}>
                        ${msg("lastName")} <span class="required">*</span>
                    </h5>
                </div>
                <form action="${url.accountUrl}" class="col span-3-of-4 info" method="post">
                    <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">

                    <#if !realm.registrationEmailAsUsername>
                        <input type="text" class="input" id="username" name="username" <#if !realm.editUsernameAllowed>disabled="disabled"</#if> value="${(account.username!'')}"/>
                    </#if>
                    <input type="text" class="input" id="email" name="email" autofocus value="${(account.email!'')}"/>
                    <input type="text" class="input" id="firstName" name="firstName" value="${(account.firstName!'')}"/>
                    <input type="text" class="input" id="lastName" name="lastName" value="${(account.lastName!'')}"/>

                    <div class="update-btn">
                        <#if url.referrerURI??><a href="${url.referrerURI}">${kcSanitize(msg("backToApplication")?no_esc)}</a></#if>
                        <button type="submit" class="btn-save" name="submitAction" value="Save">${msg("doSave")}</button>
                        <button type="submit" class="btn-cancel" name="submitAction" value="Cancel">${msg("doCancel")}</button>
                    </div>
                </form>
            </div>
        </div>
    <#elseif section = "avatar-modal">
        <form class="avt-modal" id="avt-modal">
            <div data-close-button id="close-button"><i class="fas fa-times"></i></div>
            <div class="wrapper">
                <div class="image">
                    <img id="user-avatar" src="${url.resourcesPath}/images/avatar.png" alt="avatar">
                </div>
                <div class="content">
                    <div class="icon"><i class="fas fa-cloud-upload-alt"></i></div>
                    <div class="text">No file chosen</div>
                </div>
            </div>
            <input type="file" id="default-btn" name="image" accept="image/*" hidden>

            <input type="hidden" value="${realm.name}" id="realmId">
            <input type="hidden" name="stateChecker" value="${stateChecker}">

            <button type="button" id="custom-btn">CHOOSE A FILE</button>
            <button type="reset" class="cancel-btn">CANCEL</button>
            <button type="submit" class="close-button">SAVE</button>

            <div class="sk-chase">
                <div class="sk-chase-dot"></div>
                <div class="sk-chase-dot"></div>
                <div class="sk-chase-dot"></div>
                <div class="sk-chase-dot"></div>
                <div class="sk-chase-dot"></div>
                <div class="sk-chase-dot"></div>
            </div>
        </form>
    <#elseif section = "avatar-form">
        <div class="col span-1-of-5 avt-image">
            <div id="main-avatar">
                <img id="user-avatar-form" src="${url.resourcesPath}/images/avatar.png" alt="avatar">
            </div>

            <div class="d-flex-center">
                <a data-modal-target="#avt-modal" class="btn-add" href="#" style="margin-left: 0">
                    <i class="fas fa-camera"></i>
                    Select Image
                </a>
            </div>
        </div>
    </#if>

</@layout.mainLayout>
