package org.example;

import ch.qos.logback.core.util.TimeUtil;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpClient;

/**
 * Hello world!
 */
public class App4 {

  public static void main(String[] args) throws InterruptedException {

    var conf = new FileHostingConfig();
    var client = conf.fileHostingClient();

    while (true) {

      var thread = new Thread(() -> {
        client.getMetadata(List.of("6d58d550-1af6-487d-a3cc-304a6bf1deca", "3786c4ac-9e4d-4c63-9eee-fc6c555da133",
                                   "61dd501d-2c39-462a-a02b-e3740c34b13f", "ea735d65-7590-4a53-b512-8c4509720b60",
                                   "badc3004-9ec0-453b-ae52-108445f90291", "a2323f5b-f8c8-40ac-a4e7-a7d850999db9"));
      });
      thread.start();

      TimeUnit.MILLISECONDS.sleep(50);
    }

  }
}
