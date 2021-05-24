<#import "template.ftl" as layout>
<@layout.mainLayout active='password' bodyClass='password'; section>

    <div class="profile tab-show">
        <h1>${msg("changePasswordHtmlTitle")}</h1>
        <div class="row">
            <div class="col span-2-of-3">
                <h3>Update your password</h3>
            </div>
            <div class="col span-1-of-3">
                <span class="subtitle">${msg("allFieldsRequired")}</span>
            </div>
        </div>

        <div class="row">
            <div class="col span-1-of-4 title">
                <#if password.passwordSet>
                    <h5>${msg("password")}</h5>
                </#if>
                <h5>${msg("passwordNew")}</h5>
                <h5>${msg("passwordConfirm")}</h5>
            </div>

            <form action="${url.passwordUrl}" class="col span-3-of-4 info" method="post">
                <input type="hidden" value="${realm.name}" id="realmId">

                <input type="text" id="username" name="username" value="${(account.username!'')}" autocomplete="username" readonly="readonly" style="display:none;">

                <#if password.passwordSet>
                    <input type="password" class="input" id="password" name="password" autofocus autocomplete="current-password">
                </#if>

                <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">

                <input type="password" class="input" id="password-new" name="password-new" autocomplete="new-password">
                <input type="password" class="input" id="password-confirm" name="password-confirm" autocomplete="new-password">

                <div class="update-btn">
                    <button type="submit" class="btn-save" style="border-radius: 7px" name="submitAction" value="Save">${msg("doSave")}</button>
                </div>
            </form>
        </div>
    </div>

</@layout.mainLayout>
