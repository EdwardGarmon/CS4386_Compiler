import java.util.Hashtable;
import java.util.Vector;

public class SymbolTable {

    private Vector<Hashtable<String, MySymbol>> table;
    private Vector<MethodSymbol> methodDefStack;

    SymbolTable() {
        this.methodDefStack = new Vector<MethodSymbol>();
        this.table = new Vector<Hashtable<String, MySymbol>>();
    }

    public MethodSymbol currentMethod() {
        return this.methodDefStack.lastElement();
    }

    public void pushMethodDef(MethodSymbol defineMethod) {
        this.methodDefStack.add(defineMethod);
    }

    public void popMethodDef() {
        this.methodDefStack.remove(this.methodDefStack.size() - 1);
    }

    public void enterScope() {
        this.table.add(new Hashtable<String, MySymbol>());
    }

    public void exitScope() {

        this.table.remove(this.table.size() - 1);
    }

    public MySymbol lookUp(String name) throws TypeException {

        for (Hashtable<String, MySymbol> hash : this.table) {
            if (hash.keySet().contains(name)) {
                return hash.get(name);
            }
        }

        throw new TypeException(" trying to access undeclared variable " + name);
    }

    public boolean declareSymbol(MySymbol sym) throws TypeException {

        for (Hashtable hash : this.table) {
            if (hash.keySet().contains(sym.name)) {
                if (hash.get(sym.name) instanceof MethodSymbol && sym instanceof MethodSymbol) {
                    throw new TypeException(sym.name + " was already a declared method");
                } else if (hash.get(sym.name) instanceof MySymbol && !(hash.get(sym.name) instanceof MethodSymbol)
                        && (sym instanceof MySymbol
                                && !(sym instanceof MethodSymbol))) {
                    throw new TypeException(sym.name + " was already a declared variable");
                }
            }
        }

        this.table.lastElement().put(sym.name, sym);
        return true;

    }

}
