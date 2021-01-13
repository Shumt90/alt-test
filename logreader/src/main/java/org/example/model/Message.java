package org.example.model;


import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
  private String method;
  private Instant date;
  private String uriPAth;
  private String uriParam;
  private int code;
  private long time;
  private String fromPage;
  private String agent;
}
