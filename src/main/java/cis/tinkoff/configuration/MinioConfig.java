package cis.tinkoff.configuration;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;
import io.minio.MinioClient;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

@Factory
public class MinioConfig {

    @Property(name = "minio.url")
    private String url;

    @Property(name = "minio.key.access")
    private String accessKey;

    @Property(name = "minio.key.secret")
    private String secretKey;

    @Singleton
    public MinioClient getClient() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .build();

        return MinioClient.builder()
                .endpoint(url)
                .httpClient(httpClient)
                .credentials(accessKey, secretKey)
                .build();
    }
}
