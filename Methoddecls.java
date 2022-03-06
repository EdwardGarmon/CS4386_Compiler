public class Methoddecls implements Token {

    Methoddecls decls;
    Methoddecl decl;

    public Methoddecls(Methoddecl decl) {
        this.decl = decl;
        this.decls = null;
    }

    public Methoddecls(Methoddecl decl, Methoddecls decls) {
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
        if (this.decl != null) {
            this.decl.typeCheck();
        }
        if (this.decls != null) {
            this.decls.typeCheck();
        }
    }

}
