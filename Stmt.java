import java.util.Vector;

class Stmt extends AccessTable implements Token {

  Expr e;
  Stmt s;

  IfEnd i;

  Readlist rl;

  Printlist pl;

  Printlinelist pll;

  String id;

  Args a;

  Name n;
  String operation;

  Fielddecls fs;
  Stmts ss;
  Optionalsemi os;

  boolean brackets;

  public Stmt(Expr e, Stmt s, IfEnd i) {
    this.e = e;
    this.s = s;
    this.i = i;
  }

  public Stmt(Expr e, Stmt s) {
    this.e = e;
    this.s = s;
  }

  public Stmt(Name n, Expr e) {
    this.n = n;
    this.e = e;
  }

  public Stmt(Readlist rl) {
    this.rl = rl;
  }

  public Stmt(Printlist pl) {
    this.pl = pl;
  }

  public Stmt(Printlinelist pll) {
    this.pll = pll;
  }

  public Stmt(String id) {
    this.id = id;
  }

  public Stmt(String id, Args a) {
    this.id = id;
    this.a = a;
  }

  public Stmt() {

  }

  public Stmt(Expr e) {
    this.e = e;
  }

  public Stmt(Name n, String operation) {
    this.n = n;
    this.operation = operation;
  }

  public Stmt(Fielddecls fs, Stmts ss, Optionalsemi os, boolean brackets) {
    this.fs = fs;
    this.ss = ss;
    this.os = os;
    this.brackets = brackets;
  }

  public String toString(int t) {

    String tabs = "";

    for (int i = 0; i < t; i++) {
      tabs += '\t';
    }

    if (this.e != null && this.s != null) {
      if (this.i != null) {
        return tabs + "if (" + e.toString(t) + ") { \n" + s.toString(t + 1) + tabs + " }" + i.toString(t) + "\n";
      }
      return tabs + "while (" + e.toString(t) + ") { \n" + s.toString(t + 1) + tabs + "} \n";
    }

    if (this.n != null && this.e != null)

    {
      return tabs + this.n.toString(t) + " = " + this.e.toString(t) + ";\n";
    }

    if (this.rl != null) {
      return tabs + "read (" + this.rl.toString(t) + "); " + "\n";
    }

    if (this.pl != null) {
      return tabs + "print (" + this.pl.toString(t) + "); " + "\n";
    }

    if (this.pll != null) {
      return tabs + "println (" + this.pll.toString(t) + "); " + "\n";
    }

    if (this.id != null) {
      if (this.a != null) {
        return tabs + this.id + "(" + this.a.toString(t) + ");" + "\n";
      }
      return tabs + this.id + "()" + "\n";
    }

    if (this.n != null && this.operation != null) {
      return tabs + this.n.toString(t) + operation + ";\n";
    }

    if (brackets) {
      return tabs + "{ \n" + (this.fs != null ? this.fs.toString(t + 1) : "") + " "
          + (this.ss != null ? this.ss.toString(t + 1) : "") + tabs + "}"
          + (this.os != null ? this.os.toString(t + 1) : "") + "\n";
    }

    if (this.e != null) {
      return tabs + "return " + this.e.toString(t) + ";";
    }

    return tabs + "return;";

  }

  public void typeCheck() throws TypeException {

    // if and while statement
    if (this.e != null && this.s != null) {
      MySymbol boolE = e.typeCheck();
      if (!boolE.canCoerce(new MySymbol("test", EType.Bool, false, false))) {
        throw new TypeException("must have boolean expression in control flow");
      }
      s.typeCheck();
      return;
    }

    // assignment
    if (this.n != null && this.e != null)

    {
      MySymbol name = n.typeCheck();
      MySymbol assignee = this.e.typeCheck();

      assignee.canCoerce(name);

      if (name.isFinal) {
        throw new TypeException("Can't reassign to a final variable");
      }

      return;
    }

    // ToDo implement typecheck for rl
    if (this.rl != null) {
      this.rl.typeCheck();
      return;
    }

    // ToDo implement typcheck for printlist
    if (this.pl != null) {
      this.pl.typeCheck();
      return;
    }

    // ToDo implement typcheck for printlist
    if (this.pll != null) {
      this.pll.typeCheck();
      return;
    }

    if (this.id != null) {

      MySymbol func = table.lookUp(this.id);

      if (!(func instanceof MethodSymbol)) {
        throw new TypeException(this.id + "  is not callable");
      }

      if (this.a != null) {
        MethodSymbol funcCall = (MethodSymbol) table.lookUp(this.id);
        Vector<MySymbol> types = this.a.typeCheck();
        funcCall.validArgs(types);
      }

      // check if it has zero params
      MethodSymbol funcCall = (MethodSymbol) table.lookUp(this.id);
      funcCall.validArgs(new Vector<MySymbol>());
      return;
    }

    if (this.n != null && this.operation != null) {
      MySymbol value = this.n.typeCheck();

      if (value.isFinal) {
        throw new TypeException("You cannot assign to a final");
      }

      if (value.type != EType.Integer && value.type != EType.Float) {
        throw new TypeException("Can't increment/decrement a non integral type");
      }
      return;
    }

    if (brackets) {

      table.enterScope();

      if (this.fs != null) {
        this.fs.typeCheck();
      }

      if (this.ss != null) {
        this.ss.typeCheck();
      }

      table.exitScope();
      return;
    }

    if (this.e != null) {
      MySymbol returnS = this.e.typeCheck();

      if (table.currentMethod().type != EType.Void) {
        if (table.currentMethod().type != returnS.type) {
          throw new TypeException(
              "Method return type and return statement types must match " + this.e.toString(0) + " " + returnS.type
                  + " "
                  + table.currentMethod().type + " " + table.currentMethod().name);
        } else {
          table.currentMethod().didReturn = true;
        }
      }

      return;
    }
  }

}
