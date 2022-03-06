import java.util.Vector;

public class Argdecls implements Token {

    ArgdeclList l;

    public Argdecls(ArgdeclList l) {
        this.l = l;
    }

    public Argdecls() {

    }

    public String toString(int t) {

        if (this.l == null) {
            return "";
        }

        return this.l.toString(t);
    }

    public Vector<MySymbol> typeCheck() throws TypeException {
        ArgdeclList cur = this.l;
        // loop across and grab all of the args
        Vector<MySymbol> args = new Vector<MySymbol>();
        while (cur != null) {
            args.add(cur.a.typeCheck());
            cur = cur.l;
        }
        return args;
    }

}
