package org.example;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Hello world!
 */
public class App2 {

  public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {


    WebClient client2 = WebClient.create("http://ebiblioteca.org/");
    long time = System.currentTimeMillis();

    for (int i = 0; i < 200; i++) {

      System.out.println("++++++++++++++++");
      client2.post().exchange().map(v->{

        System.out.println(v.statusCode());
        return v;
      }).block();

      System.out.println("------------");

    }

    System.out.println("time: " + (System.currentTimeMillis() - time)/200);
    //time: 1943
  }
}
