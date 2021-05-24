package com.company.pm.common.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    
    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    
    // Regex for phone number
    public static final String PHONE_REGEX = "^\\+(?:[0-9]){6,14}[0-9]$";
    
    // Regex for month
    public static final String MONTH_REGEX = "^0[1-9]$|^1[0-2]$";
    
    // Regex for date format yyyy-MM-dd
    public static final String DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    
    // Default timezone
    public static final String TIMEZONE = "GMT-0:00";
    
    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";
}
