public class TypeException extends Exception {

    String fault;

    public TypeException(String s) {
        fault = s;
    }

    public String toString() {
        return fault;
    }
}
