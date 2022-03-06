import java.util.Vector;

class Program extends AccessTable implements Token {

  Memberdecls members;
  String name;

  public Program(Memberdecls m, String name) {
    this.members = m;
    this.name = name;
    table = new SymbolTable();
  }

  public String toString(int t) {
    return ("Program:\n" + '\t' + "class " + this.name + "\n\t" + "{" + "\n"
        + (this.members != null ? this.members.toString(t + 1) : "") + "\t\n}\n");
  }

  public void typeCheck() throws TypeException {
    table.pushMethodDef(new MethodSymbol("main", EType.Void, new Vector<MySymbol>()));
    table.enterScope();
    if (this.members != null)
      this.members.typeCheck();
    table.exitScope();
    table.popMethodDef();
  }

}
