<#macro mainLayout active bodyClass>
    <!doctype html>
    <html lang="en">
        <head>
            <meta charset="utf-8">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta name="robots" content="noindex, nofollow">

            <title>Account Settings</title>
            <link rel="preconnect" href="https://fonts.gstatic.com" />
            <link href="https://fonts.googleapis.com/css2?family=Lato:wght@300;400;700;900&display=swap" rel="stylesheet" />
            <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
            <link rel="icon" href="${url.resourcesPath}/images/j.svg">
            <#if properties.styles?has_content>
                <#list properties.styles?split(' ') as style>
                    <link href="${url.resourcesPath}/${style}" rel="stylesheet"/>
                </#list>
            </#if>
            <script src="https://kit.fontawesome.com/9b7bd019f1.js" crossorigin="anonymous"></script>
        </head>
        <body>
            <header id="header">
                <div id="left-header">
                    <a href="#"><img class="logo" src="${url.resourcesPath}/images/Logo_official.png" alt="logo"/></a>
                </div>
                <div id="overlay"></div>
                <div id="right-header">
                    <nav id="nav-menu">
                        <#if referrer?has_content && referrer.url?has_content>
                            <div class="menu-icons referrer">
                                <a href="${referrer.url}">${msg("backTo",referrer.name)}</a>
                            </div>
                        </#if>
                        <div class="avt-image dropbtn">
                            <img id="header-avatar" width="40" height="40" style="margin-top: 10px;cursor: pointer"
                                 src="${url.resourcesPath}/images/avatar.png" alt="avatar">
                        </div>

                        <div class="dropdown-menu">
                            <div class="profile">
                                <img id="dropdown-avatar" src="${url.resourcesPath}/images/avatar.png" alt="avatar">
                                <h3>${account.username!''} <br> <span>Student at Hanoi University of Science and Technology</span></h3>
                            </div>
                            <div class="setting-list">
                                <ul>
                                    <li><i class="fas fa-user-circle"></i><a href="#">View Profile</a></li>
                                    <li><i class="fas fa-edit"></i><a href="#">Edit Profile</a></li>
                                    <li><i class="fas fa-users-cog"></i><a href="${url.accountUrl}">Account Settings</a></li>
                                    <li><i class="fas fa-sign-out-alt"></i><a href="${url.logoutUrl}">Sign Out</a></li>
                                </ul>
                            </div>
                        </div>
                    </nav>
                </div>
            </header>

            <section class="container" style="min-height: calc(100% - 6rem)">
                <#if active=='account'>
                    <#nested "avatar-modal">
                </#if>
                <div class="row">
                    <div class="col span-1-of-5 sidemenu">
                        <nav>
                            <a href="${url.accountUrl}" class="tab <#if active=='account'>tab-active</#if>">
                                <i class="fas fa-user"></i>
                                <span class="setting-title">${msg("account")}</span>
                            </a>
                            <#if features.passwordUpdateSupported>
                                <a href="${url.passwordUrl}" class="tab <#if active=='password'>tab-active</#if>">
                                    <i class="fas fa-unlock-alt"></i>
                                    <span class="setting-title">${msg("password")}</span>
                                </a>
                            </#if>
                            <a href="${url.totpUrl}" class="tab <#if active=='totp'>tab-active</#if>">
                                <i class="fas fa-key"></i>
                                <span class="setting-title">${msg("authenticator")}</span>
                            </a>
                            <#if features.identityFederation>
                                <a href="${url.socialUrl}" class="tab <#if active=='social'>tab-active</#if>">
                                    <i class="fas fa-passport"></i>
                                    <span class="setting-title">${msg("federatedIdentity")}</span>
                                </a>
                            </#if>
                            <a href="${url.sessionsUrl}" class="tab <#if active=='sessions'>tab-active</#if>">
                                <i class="fas fa-cookie"></i>
                                <span class="setting-title">${msg("sessions")}</span>
                            </a>
                            <a href="${url.applicationsUrl}" class="tab <#if active=='applications'>tab-active</#if>">
                                <i class="fas fa-receipt"></i>
                                <span class="setting-title">${msg("applications")}</span>
                            </a>
                            <#if features.log>
                                <a href="${url.logUrl}" class="tab <#if active=='log'>tab-active</#if>">
                                    <i class="fas fa-terminal"></i>
                                    <span class="setting-title">${msg("log")}</span>
                                </a>
                            </#if>
                            <#if realm.userManagedAccessAllowed && features.authorization>
                                <a href="${url.resourceUrl}" class="tab <#if active=='authorization'>tab-active</#if>">
                                    <i class="fas fa-folder"></i>
                                    <span class="setting-title">${msg("myResources")}</span>
                                </a>
                            </#if>
                        </nav>
                    </div>

                    <div class="col span-3-of-5 account-info">
                        <#if message?has_content>
                            <div class="alert alert-${message.type}">
                                <#if message.type=='success' ><span class="pficon pficon-ok"></span></#if>
                                <#if message.type=='error' ><span class="pficon pficon-error-circle-o"></span></#if>
                                <span class="kc-feedback-text">${kcSanitize(message.summary)?no_esc}</span>
                            </div>
                        </#if>

                        <#nested "content">
                    </div>

                    <#if active=='account'>
                        <#nested "avatar-form">
                    </#if>
                </div>
            </section>

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
        </body>
    <#if properties.scripts?has_content>
        <#list properties.scripts?split(' ') as script>
            <script type="text/javascript" src="${url.resourcesPath}/${script}" async></script>
        </#list>
    </#if>
    </html>
</#macro>
