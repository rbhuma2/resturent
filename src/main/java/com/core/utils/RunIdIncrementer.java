package com.core.utils;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class RunIdIncrementer implements JobParametersIncrementer {

    private static final String RUN_ID_KEY = "run.id";
    private String key;

    public RunIdIncrementer() {
        this.key = RUN_ID_KEY;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters params = parameters == null ? new JobParameters() : parameters;
        long id = getNextKey();
        return new JobParametersBuilder(params).addLong(getKey(), Long.valueOf(id)).toJobParameters();
    }

    public Long getNextKey() {
        return System.currentTimeMillis();
    }

}
