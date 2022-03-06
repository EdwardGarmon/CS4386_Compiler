public class ArgdeclList implements Token {

    Argdecl a;
    ArgdeclList l;

    public ArgdeclList(Argdecl a, ArgdeclList l) {
        this.a = a;
        this.l = l;
    }

    public ArgdeclList(Argdecl a) {
        this.a = a;
    }

    public String toString(int n) {
        if (this.l == null) {
            return this.a.toString(n);

        }
        return this.a.toString(n) + this.l.toString(n);
    }
}
