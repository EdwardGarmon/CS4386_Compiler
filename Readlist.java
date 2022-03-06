public class Readlist implements Token {

    Name n;
    Readlist rl;

    public Readlist(Name n, Readlist rl) {
        this.n = n;
        this.rl = rl;
    }

    public Readlist(Name n) {
        this.n = n;
    }

    public String toString(int t) {
        if (this.rl != null) {
            return this.n.toString(t) + ", " + this.rl.toString(t);
        }
        return this.n.toString(t);
    }

    public void typeCheck() throws TypeException {

        MySymbol readName = this.n.typeCheck();

        if (readName.isArray || readName instanceof MethodSymbol || readName.isFinal) {
            throw new TypeException(readName.name + " can not be read into");
        }

        if (this.rl != null) {
            this.rl.typeCheck();
        }

    }
}
