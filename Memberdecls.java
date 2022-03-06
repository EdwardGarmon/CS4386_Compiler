public class Memberdecls implements Token {

    Fielddecl f;
    Memberdecls m;
    Methoddecls methods;

    public Memberdecls(Fielddecl f, Memberdecls m) {
        this.f = f;
        this.m = m;
    }

    public Memberdecls(Fielddecl f) {
        this.f = f;
    }

    public Memberdecls(Methoddecls methods) {
        this.methods = methods;
    }

    public String toString(int n) {

        if (methods != null) {
            return this.methods.toString(n + 1);
        }

        if (m != null) {
            return f.toString(n + 1) + "\n" + m.toString(n);
        }

        return f.toString(n + 1);

    }

    public void typeCheck() throws TypeException {
        if (this.f != null) {
            this.f.typeCheck();
        }
        if (this.methods != null) {
            this.methods.typeCheck();
        }
        if (this.m != null) {
            this.m.typeCheck();
        }
    }

}
