package com.core.api;

import static com.core.utils.CommonConstants.SEPARATOR_BLANK;

import java.time.ZonedDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExecContext {
    private String userSequenceId = SEPARATOR_BLANK;

    private String userId = SEPARATOR_BLANK;

    private boolean isPatchRequest = false;

    private boolean isPutRequest = false;

    private String language;

    private String userRole = SEPARATOR_BLANK;

    private String userPartyId = SEPARATOR_BLANK;

    private ZonedDateTime applicationDate = null;

    private ZonedDateTime applicationDateTime = null;

    private HttpHeaders httpHeaders = null;

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserPartyId() {
        return userPartyId;
    }

    public void setUserPartyId(String userPartyId) {
        this.userPartyId = userPartyId;
    }

    public String getUserSeqeunceId() {
        return userSequenceId;
    }

    public void setUserSequenceId(String userSequenceId) {
        this.userSequenceId = userSequenceId;
    }

    public boolean isPatchRequest() {
        return isPatchRequest;
    }

    public void setPatchRequest(boolean isPatchRequest) {
        this.isPatchRequest = isPatchRequest;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ZonedDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(ZonedDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getUserSequenceId() {
        return userSequenceId;
    }

    public ZonedDateTime getApplicationDateTime() {
        return applicationDateTime;
    }

    public void setApplicationDateTime(ZonedDateTime applicationDateTime) {
        this.applicationDateTime = applicationDateTime;
    }

    public boolean isPutRequest() {
        return isPutRequest;
    }

    public void setPutRequest(boolean isPutRequest) {
        this.isPutRequest = isPutRequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
