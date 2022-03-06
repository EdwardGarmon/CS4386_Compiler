public class Argdecl implements Token {

    Type t;
    String id;
    boolean array;

    public Argdecl(Type t, String id, boolean array) {
        this.t = t;
        this.id = id;
        this.array = array;
    }

    public String toString(int n) {
        if (array) {
            return this.t.toString(n) + " " + this.id + "[], ";
        }
        return this.t.toString(n) + " " + this.id + ", ";
    }

    public MySymbol typeCheck() throws TypeException {
        return new MySymbol(this.id, this.t, false, this.array);
    }

}
