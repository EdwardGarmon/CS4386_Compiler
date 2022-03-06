public class IfEnd {

    Stmt s;

    public IfEnd(Stmt s) {
        this.s = s;
    }

    public IfEnd() {

    }

    public String toString(int t) {

        String tabs = "";

        for (int i = 0; i < t; i++) {
            tabs += '\t';
        }

        if (this.s != null) {
            return "else { \n" + s.toString(t + 1) + tabs + "}";
        }
        return ";";
    }
}
