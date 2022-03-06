public class Fielddecls implements Token {

    Fielddecls decls;
    Fielddecl decl;

    public Fielddecls(Fielddecl decl) {
        this.decl = decl;
        this.decls = null;
    }

    public Fielddecls(Fielddecl decl, Fielddecls decls) {
        this.decl = decl;
        this.decls = decls;
    }

    public String toString(int t) {
        if (decls != null) {
            return decl.toString(t) + decls.toString(t);
        } else {
            return decl.toString(t);
        }
    }

    public void typeCheck() throws TypeException {

        this.decl.typeCheck();

        if (this.decls != null) {
            this.decls.typeCheck();
        }
    }

}
