
enum EType {
    Float,
    Integer,
    Char,
    String,
    Bool,
    Void,
}

public class MySymbol {

    String name;

    EType type;

    boolean isFinal;

    boolean isArray;

    MySymbol(String name, EType type, boolean ifFinal, boolean isArray) {
        this.isFinal = ifFinal;
        this.type = type;
        this.name = name;
        this.isArray = isArray;
    }

    MySymbol(String name, Type type, boolean ifFinal, boolean isArray) {

        if (type != null) {
            this.type = mapType(type.toString(0));
        } else {
            this.type = EType.Void;
        }

        this.isFinal = ifFinal;

        this.name = name;
        this.isArray = isArray;
    }

    public static EType castEType(EType from, EType to) throws TypeException {

        throw new TypeException("can't cast to type");
    }

    public boolean canCoerce(MySymbol to) throws TypeException {

        if (canCoerce(this.type, to.type) && this.isArray == to.isArray) {
            return true;
        }

        throw new TypeException(
                "can't implictly coerce type " + this.name + " " + this.type + " " + " to " + to.name + " " + to.type);
    }

    public static boolean canCoerce(EType from, EType to) {

        if (from == EType.Integer && (to == EType.Bool || to == EType.Float)) {
            return true;
        }

        if (to == EType.String) {
            return true;
        }

        return from == to;

    }

    public static EType mapType(String type) {
        switch (type) {
            case "int":
                return EType.Integer;
            case "bool":
                return EType.Bool;
            case "char":
                return EType.Char;
            case "float":
                return EType.Float;
            case "string":
                return EType.String;
            case "void":
                return EType.Void;

            // in case a null pointer is passed
            default:
                return EType.Void;

        }
    }

}
