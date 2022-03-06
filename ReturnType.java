import javax.sound.sampled.AudioFileFormat.Type;

public class ReturnType implements Token {

    Type t;

    public ReturnType(Type t) {
        this.t = t;
    }

    public ReturnType() {

    }

    public String toString(int n) {
        if (this.t != null) {
            return t.toString();
        }
        return "void";
    }

}
