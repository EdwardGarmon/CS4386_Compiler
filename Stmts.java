class Stmts implements Token {
  Stmts stmts;
  Stmt stmt;

  public Stmts(Stmt s) {
    stmts = null;
    stmt = s;
  }

  public Stmts(Stmt s, Stmts sl) {
    stmts = sl;
    stmt = s;
  }

  public Stmts() {
    stmts = null;
    stmt = null;
  }

  public String toString(int t) {

    if (stmts == null && stmt == null) {
      return " ";
    }

    if (stmts != null) {

      return stmt.toString(t) + stmts.toString(t);
    } else {
      return stmt.toString(t);
    }

  }

  public void typeCheck() throws TypeException {

    if (stmt != null) {
      stmt.typeCheck();
    }

    if (stmts != null) {
      stmts.typeCheck();
    }

  }
}
