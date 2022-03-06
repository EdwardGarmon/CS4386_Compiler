public class Optionalsemi implements Token {

    boolean exists;

    public Optionalsemi(boolean exists) {
        this.exists = exists;
    }

    public String toString(int n) {
        if (this.exists) {
            return ";";
        }
        return "";
    }

}
