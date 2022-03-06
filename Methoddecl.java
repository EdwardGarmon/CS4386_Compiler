import java.util.Vector;

public class Methoddecl extends AccessTable implements Token {

    Type r;
    String id;
    Argdecls a;

    Fielddecls f;

    Stmts s;
    Optionalsemi o;

    public Methoddecl(Type type, String id, Argdecls a, Fielddecls f, Stmts s, Optionalsemi o) {
        this.r = type;
        this.id = id;
        this.a = a;
        this.f = f;
        this.s = s;
        this.o = o;
    }

    public Methoddecl(String id, Argdecls a, Fielddecls f, Stmts s, Optionalsemi o) {
        this.id = id;
        this.a = a;
        this.f = f;
        this.s = s;
        this.o = o;
        this.r = new Type("void");
    }

    public String toString(int t) {

        String tabs = "";

        for (int i = 0; i < t; i++) {
            tabs += '\t';
        }

        return tabs + (this.r != null ? this.r.toString(t) : "void") + " " + this.id + " (" + this.a.toString(t) + ")\n"
                + tabs + "{" + "\n " + (this.f != null ? this.f.toString(t + 1) : "") + "\n " + this.s.toString(t + 1)
                + " \n" + tabs + "}" + this.o.toString(t) + "\n\n";

    }

    public void typeCheck() throws TypeException {

        Vector<MySymbol> args = this.a.typeCheck();
        MethodSymbol sym = new MethodSymbol(this.id, this.r, args);

        table.declareSymbol(sym);

        table.enterScope();

        table.pushMethodDef(sym);

        for (MySymbol a : args) {
            table.declareSymbol(a);
        }

        if (f != null)
            f.typeCheck();

        if (this.s != null) {
            this.s.typeCheck();
        }

        table.exitScope();

        if (sym.type != EType.Void && !sym.didReturn) {
            throw new TypeException(sym.name + " non void method did not return");
        }

        table.popMethodDef();

    }

}
