package blueteam.mypantry.http;

import blueteam.mypantry.core.btProduct;
import blueteam.mypantry.runtime.btRuntime;

public class btHttpClient {
    public static void PostToServer( btProduct PostPacket ) {
        if( PostPacket == null ) {
            btRuntime.LogMessage( "[HttpPost] bad post packet received" );
            return;
        }

        new btHttpAsyncPost().execute( PostPacket );
    }
}
