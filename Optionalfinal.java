public class Optionalfinal implements Token {

    boolean exists;

    public Optionalfinal(boolean exists) {
        this.exists = exists;
    }

    public String toString(int t) {
        if (this.exists) {
            return "final";
        }
        return "";
    }

}
