<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">
        <a href="#"><img class="logo" src="${url.resourcesPath}/images/Logo_official.png" alt="logo"/></a>
        ${msg("pageExpiredTitle")}
    <#elseif section = "form">
        <p id="instruction1" class="instruction">
            ${msg("pageExpiredMsg1")} <a id="loginRestartLink" href="${url.loginRestartFlowUrl}">${msg("doClickHere")}</a> .<br/>
            ${msg("pageExpiredMsg2")} <a id="loginContinueLink" href="${url.loginAction}">${msg("doClickHere")}</a> .
        </p>
    </#if>
</@layout.registrationLayout>
