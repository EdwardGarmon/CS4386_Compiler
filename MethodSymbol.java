import java.util.Vector;

public class MethodSymbol extends MySymbol {

    Vector<MySymbol> args;

    public boolean didReturn = false;

    MethodSymbol(String name, Type type, Vector<MySymbol> args) {
        super(name, type, false, false);

        this.args = args;

    }

    MethodSymbol(String name, EType type, Vector<MySymbol> args) {
        super(name, type, false, false);

        this.args = args;
    }

    public boolean validArgs(Vector<MySymbol> args) throws TypeException {

        if (args.size() != this.args.size()) {
            throw new TypeException("not enough arguments for method " + this.name);
        }

        for (int i = 0; i < this.args.size(); i++) {
            args.get(i).canCoerce(this.args.get(i));
        }

        return true;
    }

}
