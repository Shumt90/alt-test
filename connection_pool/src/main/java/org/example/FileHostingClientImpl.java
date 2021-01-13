package org.example;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
public class FileHostingClientImpl implements FileHostingClient {

    private final RestTemplate fileHostingRestTemplate;

    private final String fileHostingUrl = "https://media-server.sport24.gg/api/v1/images/";
    private final String token = "Y0aDXRpMH2W8fmCB5YXnCnyLYu84m59xmXmnX827iwHJNzqzNAra82DJfsA9hhjp";

    @Override
    public String getMetadata(List<String> ids) {
        try {
            HttpHeaders mainHeaders = new HttpHeaders();
            mainHeaders.setContentType(MediaType.APPLICATION_JSON);
            mainHeaders.add("Authorization", "Bearer " + this.token);

            String url = fileHostingUrl + "metadata";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            List<String> params = ids.stream().filter(Objects::nonNull).collect(Collectors.toList());
            if (params.size() == 0) {
                return null;
            }
            params.forEach(s -> builder.queryParam("id", s));

            var uploadDto = fileHostingRestTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(mainHeaders),
                String.class
            );

            Assert.notNull(uploadDto.getBody(), "No image key from mediahosting server for filehostingclient");

            System.out.println("-----------------------------------------");
            System.out.println("-----------------------------------------");
            System.out.println("-----------------------------------------");
            System.out.println("-----------------------------------------");
            System.out.println(uploadDto.getHeaders());

            return uploadDto.getBody();
        } catch (Exception e) {
            log.error("Error get metadata image from fileHosting ", e);

            System.exit(-1);
            return "";
        }
    }
}
