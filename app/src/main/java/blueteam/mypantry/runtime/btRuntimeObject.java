package blueteam.mypantry.runtime;


public class btRuntimeObject extends Object {
    public btRuntimeObject() {
        InternalObject = new Object();
    }

    public void Rebind( Object Obj ) {
        InternalObject = Obj;
    }

    public void Cast( Object Obj ) {
        InternalObject = (Object)Obj;
    }

    public Object InternalObject = null;
}
