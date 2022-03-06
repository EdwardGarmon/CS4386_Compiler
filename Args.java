import java.util.Vector;

public class Args implements Token {

    Expr e;
    Args a;

    public Args(Expr e, Args a) {
        this.e = e;
        this.a = a;

    }

    public Args(Expr e) {
        this.e = e;
    }

    public String toString(int t) {
        if (this.a != null) {
            return this.e.toString(t) + ", " + this.a.toString(t);
        }
        return this.e.toString(t);

    }

    public Vector<MySymbol> typeCheck() throws TypeException {
        Vector<MySymbol> types = new Vector<MySymbol>();
        Args cur = this;

        while (cur != null) {
            types.add(cur.e.typeCheck());
            cur = cur.a;
        }

        return types;
    }

}
