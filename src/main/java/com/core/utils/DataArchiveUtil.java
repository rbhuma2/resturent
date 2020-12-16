package com.core.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.batch.core.JobExecution;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataArchiveUtil {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String NEW_SEPERATOR_LINE = "\r\n";

    public void writeData(List<?> objectList, String fileName, String directoryName)
            throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        List<Map<String, String>> csvList = new ArrayList<>();
        Map<String, String> objectMap = null;
        for (Object object : objectList) {
            objectMap = new LinkedHashMap<>();

            objectMap = getColumnData(object, objectMap);

            csvList.add(objectMap);
        }
        writeToFlatFile(csvList, fileName, directoryName);
    }
    
	public void writeJson(List<?> objectList, String fileName, String directoryName) throws IOException {

		File file = new File(directoryName + File.separatorChar + fileName);
		try (FileWriter fileWriter = new FileWriter(file, false)) {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(objectList);
			fileWriter.write(jsonString);
			fileWriter.write(NEW_SEPERATOR_LINE);
			fileWriter.flush();
		}
	}

    private Map<String, String> getColumnData(Object object, Map<String, String> objectMap)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Field field : object.getClass().getDeclaredFields()) {

            if (field.getAnnotation(EmbeddedId.class) != null) {
                objectMap = getEmbeddedIdColumnData(object, field, objectMap);
            }
            Column column = field.getAnnotation(Column.class);
            if (column != null) {

                if ((field.getType().equals(ZonedDateTime.class))
                        && (PropertyUtils.getProperty(object, field.getName())) != null) {
                    if (field.getAnnotation(DateTimeFormat.class) != null) {

                        objectMap.put(column.name(), DateRoutine.dateTimeAsYYYYMMDDString(
                                (ZonedDateTime) (PropertyUtils.getProperty(object, field.getName()))));
                    }

                    else {

                        objectMap.put(column.name(), DateRoutine.dateTimeAsYYYYMMDDHHhhmmssSSSString(
                                (ZonedDateTime) (PropertyUtils.getProperty(object, field.getName()))));
                    }
                }

                else {

                    objectMap.put(column.name(), BeanUtils.getProperty(object, field.getName()));
                }

            }
        }
        return objectMap;
    }

    private Map<String, String> getEmbeddedIdColumnData(Object object, Field field, Map<String, String> objectMap)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        Object embeddedIdObject = propertyUtilsBean.getNestedProperty(object, field.getName());

        for (Field embeddedField : field.getType().getDeclaredFields()) {
            Column column = embeddedField.getAnnotation(Column.class);
            if (column != null) {

                if (embeddedField.getType().equals(ZonedDateTime.class)) {
                    if (embeddedField.getAnnotation(DateTimeFormat.class) != null) {

                        objectMap.put(column.name(), DateRoutine.dateTimeAsYYYYMMDDString((ZonedDateTime) (PropertyUtils
                                .getProperty(embeddedIdObject, embeddedField.getName()))));
                    }

                    else {

                        objectMap.put(column.name(),
                                DateRoutine.dateTimeAsYYYYMMDDHHhhmmssSSSString((ZonedDateTime) (PropertyUtils
                                        .getProperty(embeddedIdObject, embeddedField.getName()))));
                    }
                } else {
                    objectMap.put(column.name(), BeanUtils.getProperty(embeddedIdObject, embeddedField.getName()));
                }
            }
        }

        return objectMap;
    }

    private void writeToFlatFile(List<Map<String, String>> csvList, String fileName, String directoryName)
            throws IOException {

        File file = new File(directoryName + File.separatorChar + fileName);
        boolean exists = file.exists();

        FileWriter fileWriter = new FileWriter(file, true);

        if (!exists) {
            fileWriter.append(csvList.get(0).keySet().toString().replaceAll("\\[|\\]", ""));
            fileWriter.append(NEW_LINE_SEPARATOR);
        }

        for (int i = 0; i < csvList.size(); i++) {
            fileWriter.append(csvList.get(i).values().toString().replaceAll("\\[|\\]", ""));
            fileWriter.append(NEW_LINE_SEPARATOR);

        }

        fileWriter.flush();
        fileWriter.close();

    }

    public void writeToTextFile(List<String> csvList, String fileName, String directoryName) throws IOException {

        File file = new File(directoryName + File.separatorChar + fileName);
        FileWriter fileWriter = new FileWriter(file, true);

        for (String Str : csvList) {
            fileWriter.append(Str);
            fileWriter.append(NEW_SEPERATOR_LINE);
        }
        fileWriter.flush();
        fileWriter.close();

    }

    public String prepareDirectory(JobExecution jobExecution, String location) {
        Long jobExecutionId = jobExecution.getJobId();
        String jobId = jobExecution.getJobInstance().getJobName();
        String fileId = jobId.concat("_JobInstance_").concat(jobExecutionId.toString());
        String directoryName = location.concat(fileId);
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directoryName;
    }

}
