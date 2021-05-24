<#import "template.ftl" as layout>
<@layout.mainLayout active='social' bodyClass='social'; section>

    <div class="profile tab-show">
        <h1>${msg("federatedIdentitiesHtmlTitle")}</h1>
        <div id="federated-identities">
            <#list federatedIdentity.identities as identity>
                <div class="row">
                    <div class="col span-1-of-4 title">
                        <h5>${identity.displayName!}</h5>
                    </div>
                    <div class="col span-2-of-4 info">
                        <input disabled class="input" value="${identity.userName!}">
                    </div>
                    <div class="col span-1-of-4">
                        <#if identity.connected>
                            <#if federatedIdentity.removeLinkPossible>
                                <form action="${url.socialUrl}" method="post">
                                    <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">
                                    <input type="hidden" id="action" name="action" value="remove">
                                    <input type="hidden" id="providerId" name="providerId" value="${identity.providerId!}">
                                    <button id="remove-link-${identity.providerId!}" class="btn-post">${msg("doRemove")}</button>
                                </form>
                            </#if>
                        <#else>
                            <form action="${url.socialUrl}" method="post">
                                <input type="hidden" value="${realm.name}" id="realmId">
                                <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">
                                <input type="hidden" id="action" name="action" value="add">
                                <input type="hidden" id="providerId" name="providerId" value="${identity.providerId!}">
                                <button id="add-link-${identity.providerId!}" class="btn-save" style="border-radius: 7px">${msg("doAdd")}</button>
                            </form>
                        </#if>
                    </div>
                </div>
            </#list>
        </div>
    </div>

</@layout.mainLayout>
