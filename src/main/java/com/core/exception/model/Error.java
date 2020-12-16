package com.core.exception.model;

import java.time.ZonedDateTime;

import org.springframework.hateoas.RepresentationModel;

import com.core.utils.AppDateDeserializer;
import com.core.utils.AppDateSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Error extends RepresentationModel<Error> {

    private String errorKey;
    private String errorSubCode;
    @JsonSerialize(using = AppDateSerializer.class)
    @JsonDeserialize(using = AppDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private ZonedDateTime failTimeStamp;
    private String message;

    @JsonCreator
    public Error(@JsonProperty("errorKey") String errorKey, @JsonProperty("errorSubCode") String errorSubCode,
            @JsonProperty("failTimeStamp") ZonedDateTime failTimeStamp,
            @JsonProperty("errorDescription") String message) {
        super();
        this.errorKey = errorKey;
        this.errorSubCode = errorSubCode;
        this.failTimeStamp = failTimeStamp;
        this.message = message;

    }

    public Error() {
    }

    public Error(Error error) {
        this(error.getErrorKey(), error.getErrorSubCode(), error.getFailTimeStamp(), error.getMessage());
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public String getErrorSubCode() {
        return errorSubCode;
    }

    public void setErrorSubCode(String errorSubCode) {
        this.errorSubCode = errorSubCode;
    }

    public ZonedDateTime getFailTimeStamp() {
        return failTimeStamp;
    }

    public void setFailTimeStamp(ZonedDateTime failTimeStamp) {
        this.failTimeStamp = failTimeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
