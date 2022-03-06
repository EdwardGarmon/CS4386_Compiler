public class Printlist implements Token {

    Expr n;
    Printlist rl;

    public Printlist(Expr n, Printlist rl) {
        this.n = n;
        this.rl = rl;
    }

    public Printlist(Expr n) {
        this.n = n;
    }

    public String toString(int t) {
        if (this.rl != null) {
            return this.n.toString(t) + ", " + this.rl.toString(t);
        }
        return this.n.toString(t);
    }

    public void typeCheck() throws TypeException {

        MySymbol printName = this.n.typeCheck();

        if (printName.isArray || printName.type == EType.Void) {
            throw new TypeException(printName.name + " can't be printed");
        }

        if (this.rl != null) {
            this.rl.typeCheck();
        }

    }

}
