package blueteam.mypantry.runtime;

import java.util.HashMap;

public class btLocalScopeAccessor {
    public void Bind( String BindingName, Object LocalBind ) {
        btRuntimeObject Obj = new btRuntimeObject();
        Obj.Cast( LocalBind );

        BoundVars.put( BindingName, Obj );
    }

    public btRuntimeObject Access( String BindingName ) {
        return BoundVars.get( BindingName );
    }

    private static HashMap< String, btRuntimeObject > BoundVars = new HashMap<>();
}
