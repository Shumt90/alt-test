public class EqualsTest {

  public static void main(String[] args) {

    System.out.println(new A("s").hashCode());
    System.out.println(new A("s").hashCode());
  }

  static class A{
    private String string;

    public A(String string) {
      this.string = string;
    }
  }
}
