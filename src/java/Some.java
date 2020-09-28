package data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Some {

  public static void main(String[] args) {
    System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
  }
}
