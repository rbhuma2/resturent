package com.core.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.core.mongo.converter.CustomTimestampReaderConvertor;
import com.core.mongo.converter.ZonedDateTimeReadConverter;
import com.core.mongo.converter.ZonedDateTimeWriteConverter;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = { "batch.core.mongo.data", "batch.billing.mongo.data",
        "batch.disbursement.mongo.data" })
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Value("${app.localTimeZone}")
    String localTimeZone;

    @Value("${mongo.db.name}")
    String database;

    @Value("${mongo.db.host}")
    String host;

    @Value("${mongo.db.port}")
    String port;

    @Value("${mongo.db.user}")
    String user;

    @Value("${mongo.db.password}")
    String password;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new ZonedDateTimeWriteConverter());
        converterList.add(new ZonedDateTimeReadConverter(localTimeZone));
        converterList.add(new CustomTimestampReaderConvertor());
        return new MongoCustomConversions(converterList);
    }

    @Override
    public MongoClient mongoClient() {
        if (user != null && !user.isEmpty()) {
            MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());

            MongoClientSettings settings = MongoClientSettings.builder().credential(credential)
                    .applyToClusterSettings(
                            builder -> builder.hosts(Arrays.asList(new ServerAddress(host, Integer.valueOf(port)))))
                    .build();

            return MongoClients.create(settings);
        } else {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyToClusterSettings(
                            builder -> builder.hosts(Arrays.asList(new ServerAddress(host, Integer.valueOf(port)))))
                    .build();
            return MongoClients.create(settings);
        }
    }

    @Override
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory,
            MongoCustomConversions customConversions, MongoMappingContext mappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
        converter.setCustomConversions(customConversions());
        converter.setCodecRegistryProvider(mongoDbFactory());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    @Override
    public boolean autoIndexCreation() {
        return true;
    }
}
