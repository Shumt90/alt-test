public class Inner {

    int i;

    public Inner(int i) {
        this.i = i;
    }

    @Override
    public int hashCode() {
        return i;
    }
}
