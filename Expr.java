import java.util.Vector;

class Expr extends AccessTable implements Token {

  Name n;

  Object id;

  Args a;

  String literal;

  EType literalType;

  Expr e;
  String op;

  Type t;

  Expr e2;
  Binaryop o;

  boolean fCall;

  Expr e3;

  public Expr(Name n) {
    this.n = n;

  }

  public Expr(Object id) {
    this.id = id;

  }

  public Expr(Object literal, EType literalType) {
    this.literal = literal.toString();
    this.literalType = literalType;

  }

  public Expr(String id, Args a, boolean fCall) {
    this.id = id;
    this.a = a;
    this.fCall = fCall;

  }

  public Expr(Expr e, String op) {
    this.e = e;
    this.op = op;

  }

  public Expr(Type t, Expr e) {
    this.t = t;
    this.e = e;

  }

  public Expr(Expr e, Binaryop o, Expr e2) {
    this.e = e;
    this.e2 = e2;
    this.o = o;

  }

  public Expr(Expr e1, Expr e2, Expr e3) {
    this.e = e1;
    this.e2 = e2;
    this.e3 = e3;

  }

  public String toString(int t) {

    if (this.n != null) {
      return this.n.toString(0);
    }

    if (this.id != null) {
      if (this.a != null) {
        return this.id + "(" + this.a.toString(t) + ")";
      }

      if (this.fCall) {
        return this.id + "()";
      }

    }
    if (this.literal != null) {
      return this.literal;
    }

    if (this.e != null) {

      if (this.t != null) {
        return "(" + this.t.toString(t) + ")" + this.e.toString(0);
      }

      if (e2 != null) {

        if (e3 != null) {
          return "(" + this.e.toString(0) + " ? " + this.e2.toString(0) + " : " + this.e3.toString(0) + ")";
        }

        if (this.o != null) {
          return "(" + this.e.toString(0) + " " + this.o.toString(0) + " " + this.e2.toString(0) + ")";
        }

      }

      if (this.op != null) {

        if (this.op == "(") {
          return "(" + this.e.toString(t) + " )";
        }

        return "(" + this.op + this.e.toString(t) + ")";

      }
    }

    if (this.fCall) {
      return this.id + "()";
    }

    return " something went wrong in expr.java";

  }

  public MySymbol typeCheck() throws TypeException {

    if (this.n != null) {
      return this.n.typeCheck();
    }

    if (this.id != null) {
      if (this.a != null) {

        MySymbol call = table.lookUp(this.id.toString());

        if (!(call instanceof MethodSymbol)) {
          throw new TypeException("can't call " + this.id);
        }

        MethodSymbol funcCall = (MethodSymbol) table.lookUp(this.id.toString());
        Vector<MySymbol> types = this.a.typeCheck();

        funcCall.validArgs(types);

        return funcCall;
      }

      if (this.fCall) {
        MethodSymbol funcCall = (MethodSymbol) table.lookUp(this.id.toString());
        funcCall.validArgs(new Vector<MySymbol>());
        return new MySymbol(funcCall.name, funcCall.type, false, funcCall.isArray);
      }

    }
    if (this.literal != null) {
      return new MySymbol("literal", literalType, false, false);
    }

    if (this.e != null) {

      if (this.t != null) {

        MySymbol old = this.e.typeCheck();
        MySymbol.castEType(old.type, MySymbol.mapType(this.t.toString()));
        return new MySymbol(old.name, MySymbol.mapType(this.t.toString()), old.isFinal, old.isArray);
      }

      if (e2 != null) {

        if (e3 != null) {

          MySymbol boolE = this.e.typeCheck();
          MySymbol trueRe = this.e2.typeCheck();
          MySymbol falseRe = this.e3.typeCheck();

          if (boolE.type != EType.Bool) {
            throw new TypeException("? first operand must be a boolean");
          }

          if (trueRe.type != falseRe.type) {
            throw new TypeException("? both returns must be of the same type");
          }

          return trueRe;

        }

        if (this.o != null) {

          MySymbol f = e.typeCheck();
          MySymbol s = e2.typeCheck();

          return handleBinOperations(f, this.o.toString(0), s);

        }

      }

      if (this.op != null) {

        if (this.op == "(") {
          return this.e.typeCheck();
        }
        return handleSingleOperation(e.typeCheck(), this.op);
      }
    }

    if (this.fCall) {
      return table.lookUp(this.id.toString());
    }

    throw new TypeException("Type not managed in expr.java");
  }

  private MySymbol handleBinOperations(MySymbol e1, String operation, MySymbol e2) throws TypeException {

    if (e2.isArray || e1.isArray) {
      throw new TypeException("only dereferenced arrays allowed for binary operations");
    }

    if ((e1.type == EType.String || e2.type == EType.String) && (!e1.isArray && !e2.isArray)) {
      if (operation == "+") {
        return new MySymbol("result", EType.String, false, false);
      } else
        throw new TypeException("no other valid bin operation for two strings");
    }

    if (operation == "+" || operation == "-" || operation == "/" || operation == "*") {
      if (e1.type == EType.Float || e2.type == EType.Float) {
        return new MySymbol("result", EType.Float, false, false);
      } else if (e1.type == EType.Integer && e2.type == EType.Integer) {
        return new MySymbol("result", EType.Integer, false, false);
      }
      throw new TypeException("arithmetic operators only allowed for numbers " + this.toString(0));
    }

    if (operation == "<=" || operation == ">=" || operation == ">" || operation == "<") {
      if ((e1.type == EType.Float || e1.type == EType.Integer)
          && (e2.type == EType.Float || e2.type == EType.Integer)) {
        return new MySymbol("result", EType.Bool, false, false);
      }
      throw new TypeException(
          "comparison operators only allowed for numbers " + e1.type + " " + e2.type + " " + this.toString(0));
    }

    if (operation == "&&" || operation == "||") {
      MySymbol boolTest = new MySymbol("test", EType.Bool, false, false);
      if (e1.canCoerce(boolTest) && e2.canCoerce(boolTest)) {
        return boolTest;
      }
      throw new TypeException("can't coerce to bool");
    }

    throw new TypeException("couldn't handle binary expression types " + this.toString(0) + " " + operation);

  }

  private MySymbol handleSingleOperation(MySymbol e1, String operation) throws TypeException {
    if (operation == "(") {
      return e1;
    }
    if (operation == "~") {
      MySymbol boolTest = new MySymbol("test", EType.Bool, false, false);
      if (e1.canCoerce(boolTest)) {
        return boolTest;
      } else {
        throw new TypeException("can't coerce to bool");
      }
    }

    if (operation == "+" || operation == "-") {
      if ((e1.type == EType.Float || e1.type == EType.Integer) && !e1.isArray) {
        return new MySymbol("result", e1.type, false, false);
      }
      throw new TypeException("arithmetic operators only allowed for numbers");
    }

    throw new TypeException("couldn't handle unary operation type");
  }

}
