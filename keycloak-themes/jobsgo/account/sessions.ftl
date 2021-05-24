<#import "template.ftl" as layout>
<@layout.mainLayout active='sessions' bodyClass='sessions'; section>

    <div class="profile tab-show">
        <h1>${msg("sessionsHtmlTitle")}</h1>

        <form action="${url.sessionsUrl}" method="post" style="text-align: center">
            <input type="hidden" value="${realm.name}" id="realmId">
            <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">

            <div class="limiter">
                <div class="container-table100">
                    <div class="wrap-table100">
                        <div class="table100">
                            <table id="sessions">
                                <thead>
                                    <tr class="table100-head">
                                        <th class="column1">${msg("ip")}</th>
                                        <th class="column2">${msg("started")}</th>
                                        <th class="column3">${msg("lastAccess")}</th>
                                        <th class="column4">${msg("expires")}</th>
                                        <th class="column5">${msg("clients")}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list sessions.sessions as session>
                                        <tr>
                                            <td class="column1">${session.ipAddress}</td>
                                            <td class="column2">${session.started?datetime}</td>
                                            <td class="column3">${session.lastAccess?datetime}</td>
                                            <td class="column4">${session.expires?datetime}</td>
                                            <td class="column5">
                                                <#list session.clients as client>
                                                    ${client}<br/>
                                                </#list>
                                            </td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <button id="logout-all-sessions" class="btn-cancel" style="width: fit-content;border-radius: 7px">${msg("doLogOutAllSessions")}</button>
        </form>
    </div>

</@layout.mainLayout>
