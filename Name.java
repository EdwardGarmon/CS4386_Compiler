public class Name extends AccessTable {

    String id;
    Expr e;

    public Name(String id) {
        this.id = id;
    }

    public Name(String id, Expr e) {
        this.id = id;
        this.e = e;
    }

    public String toString(int t) {

        if (this.e != null) {
            return this.id + "[" + this.e.toString(t) + "]";
        }

        return this.id;
    }

    public MySymbol typeCheck() throws TypeException {

        if (this.e != null) {

            MySymbol index = this.e.typeCheck();

            if (index.type != EType.Integer) {
                throw new TypeException("index access must be by integer");
            }

            MySymbol array = table.lookUp(this.id);

            if (!array.isArray) {
                throw new TypeException("Can't dereference a non array type");
            }

            return new MySymbol(array.name, array.type, array.isFinal, false);

        }

        return table.lookUp(this.id);
    }
}
