public class Printlinelist implements Token {

    Printlist pl;

    public Printlinelist(Printlist pl) {
        this.pl = pl;
    }

    public Printlinelist() {

    }

    public String toString(int t) {
        if (this.pl != null) {
            return this.pl.toString(0);
        }
        return "";
    }

    public void typeCheck() throws TypeException {
        if (this.pl != null) {
            this.pl.typeCheck();
        }
    }

}
