import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileComparison {

  public static void main(String[] args) throws IOException {

    var elastic = Files.readAllLines(Path.of("logs.txt"));
    var postgres = Files.readAllLines(Path.of("player.csv"));

    postgres.removeAll(elastic);

    postgres.forEach(System.out::println);


  }
}
