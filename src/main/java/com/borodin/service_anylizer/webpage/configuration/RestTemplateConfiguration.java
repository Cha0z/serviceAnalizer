package com.borodin.service_anylizer.webpage.configuration;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() throws Exception {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(createHttpClient()));
    }


    /**
     * Method is Responsible For Creating the HTTP Client.
     */
    private org.apache.http.client.HttpClient createHttpClient() throws Exception {
        log.info("Message = Creating HTTP client");


        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())

                .build();
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                registry);

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
    }


}
