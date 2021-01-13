package org.example;

import static java.util.Collections.singletonList;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class FileHostingConfig {

    @Bean
    FileHostingClient fileHostingClient() {
        return new FileHostingClientImpl(fileHostingRestTemplate());
    }

    @SneakyThrows
    public RestTemplate fileHostingRestTemplate() {
        var requestFactory = requestFactory();

        var rt = new RestTemplate();
        rt.setRequestFactory(requestFactory);
        rt.setInterceptors(singletonList((request, body, execution) -> {

            var time = System.currentTimeMillis();
            var resp = execution.execute(request, body);

            if ((System.currentTimeMillis() - time) > 1000) {
                log.warn("slow request to media host. Time: {}, Url: {}", System.currentTimeMillis() - time, request.getURI());
            }

            log.debug("Request to media host. method: {}, time: {}", request.getMethod(), System.currentTimeMillis() - time);

            return resp;
        }));
        rt.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return !clientHttpResponse.getStatusCode().is2xxSuccessful();
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                var error = new String(clientHttpResponse.getBody().readAllBytes());

                if (clientHttpResponse.getStatusCode().is5xxServerError()) {
                    log.error("get {} from media host: {}", clientHttpResponse.getStatusCode(), error);
                }

                if (clientHttpResponse.getStatusCode().is4xxClientError()) {
                    log.debug("get {} from media host: {}", clientHttpResponse.getStatusCode(), error);
                }

            }
        });

        return rt;
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
            RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // Increase max total connection to 200
        connectionManager.setMaxTotal(1);
        // Increase default max connection per route to 20
        connectionManager.setDefaultMaxPerRoute(1);
        // Increase max connections fo

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
            .setConnectionManager(connectionManager).build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        requestFactory.setConnectTimeout(1001);
        requestFactory.setReadTimeout(5000);

        return requestFactory;
    }
}
