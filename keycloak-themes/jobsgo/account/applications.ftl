<#import "template.ftl" as layout>
<@layout.mainLayout active='applications' bodyClass='applications'; section>

    <div class="profile tab-show">
        <h1>${msg("applicationsHtmlTitle")}</h1>
        <form action="${url.applicationsUrl}" method="post">
            <input type="hidden" value="${realm.name}" id="realmId">
            <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">
            <input type="hidden" id="referrer" name="referrer" value="${stateChecker}">

            <div class="limiter">
                <div class="container-table100">
                    <div class="wrap-table100">
                        <div class="table100">
                            <table id="applications">
                                <thead>
                                    <tr class="table100-head">
                                        <th class="column1">${msg("application")}</th>
                                        <th class="column2">${msg("availableRoles")}</th>
                                        <th class="column3">${msg("grantedPermissions")}</th>
                                        <th class="column4">${msg("additionalGrants")}</th>
                                        <th class="column5">${msg("action")}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list applications.applications as application>
                                        <tr>
                                            <td class="column1">
                                                <#if application.effectiveUrl?has_content><a href="${application.effectiveUrl}"></#if>
                                                    <#if application.client.name?has_content>${advancedMsg(application.client.name)}<#else>${application.client.clientId}</#if>
                                                    <#if application.effectiveUrl?has_content></a></#if>
                                            </td>

                                            <td class="column2">
                                                <#list application.realmRolesAvailable as role>
                                                    <#if role.description??>${advancedMsg(role.description)}<#else>${advancedMsg(role.name)}</#if>
                                                    <#if role_has_next>, </#if>
                                                </#list>
                                                <#list application.resourceRolesAvailable?keys as resource>
                                                    <#if application.realmRolesAvailable?has_content>, </#if>
                                                    <#list application.resourceRolesAvailable[resource] as clientRole>
                                                        <#if clientRole.roleDescription??>${advancedMsg(clientRole.roleDescription)}<#else>${advancedMsg(clientRole.roleName)}</#if>
                                                        ${msg("inResource")} <strong><#if clientRole.clientName??>${advancedMsg(clientRole.clientName)}<#else>${clientRole.clientId}</#if></strong>
                                                        <#if clientRole_has_next>, </#if>
                                                    </#list>
                                                </#list>
                                            </td>

                                            <td class="column3">
                                                <#if application.client.consentRequired>
                                                    <#list application.clientScopesGranted as claim>
                                                        ${advancedMsg(claim)}<#if claim_has_next>, </#if>
                                                    </#list>
                                                <#else>
                                                    <strong>${msg("fullAccess")}</strong>
                                                </#if>
                                            </td>

                                            <td class="column4">
                                                <#list application.additionalGrants as grant>
                                                    ${advancedMsg(grant)}<#if grant_has_next>, </#if>
                                                </#list>
                                            </td>

                                            <td class="column5">
                                                <#if (application.client.consentRequired && application.clientScopesGranted?has_content) || application.additionalGrants?has_content>
                                                    <button type='submit' class='${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!}' id='revoke-${application.client.clientId}' name='clientId' value="${application.client.id}">${msg("revoke")}</button>
                                                </#if>
                                            </td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

</@layout.mainLayout>
