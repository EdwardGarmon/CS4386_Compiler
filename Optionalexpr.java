class Optionalexpr implements Token {
  Expr expr;
  boolean exists;

  public Optionalexpr(Expr e) {
    expr = e;
    exists = true;
  }

  public Optionalexpr() {
    exists = false;
  }

  public String toString(int t) {
    if (!exists)
      return "";
    return " = " + expr.toString(t);
  }
}
