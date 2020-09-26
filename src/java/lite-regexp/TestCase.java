public class TestCase {
  public String regexp;
  public String value;
  public boolean isMatch;

  public TestCase(String regexp, String value, boolean isMatch) {
    this.regexp = regexp;
    this.value = value;
    this.isMatch = isMatch;
  }

  @Override
  public String toString() {
    return "TestCase{" +
        "regexp='" + regexp + '\'' +
        ", value='" + value + '\'' +
        ", isMatch=" + isMatch +
        '}';
  }
}
