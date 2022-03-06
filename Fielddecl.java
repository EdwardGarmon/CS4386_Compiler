import javax.lang.model.element.Element;

public class Fielddecl extends AccessTable implements Token {

    Type type;
    String id;
    String intlit;
    Expr e;
    boolean isFinal;
    boolean isArray = false;

    // example
    // int num;
    // float num;
    public Fielddecl(Type type, String id, boolean isFinal) {
        this.id = id;
        this.type = type;
        this.isFinal = isFinal;
    }

    // example
    // int num = x + 1;
    public Fielddecl(Type type, String id, Expr e, boolean isFinal) {
        this.id = id;
        this.type = type;
        this.e = e;
        this.isFinal = isFinal;
    }

    // example
    // int num = [4];
    public Fielddecl(Type type, String id, String intlit) {
        this.type = type;
        this.id = id;
        this.intlit = intlit;
        this.isArray = true;
    }

    public String toString(int t) {

        String tabs = "";

        for (int i = 0; i < t; i++) {
            tabs += '\t';
        }

        tabs += this.isFinal ? "final" : "";

        if (intlit != null) {
            return tabs + type.toString(t) + " " + this.id + "[" + this.intlit.toString() + "]; \n";
        }

        if (this.e != null) {
            return tabs + type.toString(t) + " " + this.id + " = " + this.e.toString(t) + "; \n";
        }

        return tabs + this.type.toString(t) + " " + this.id + "; \n";

    }

    public void typeCheck() throws TypeException {

        if (intlit != null) {
            MySymbol sym = new MySymbol(this.id, this.type, this.isFinal, true);
            table.declareSymbol(sym);
        } else

        if (this.e != null) {
            MySymbol sym = new MySymbol(this.id, this.type, this.isFinal, false);
            MySymbol assignee = this.e.typeCheck();
            assignee.canCoerce(sym);
            table.declareSymbol(sym);
        } else

            table.declareSymbol(new MySymbol(this.id, this.type, this.isFinal, false));
    }
}
