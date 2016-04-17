package blueteam.mypantry.async;

import blueteam.mypantry.core.btInventoryHandler;
import blueteam.mypantry.core.btProduct;
import blueteam.mypantry.http.btHttpClient;
import blueteam.mypantry.runtime.btRuntime;

import java.util.Timer;
import java.util.TimerTask;


public class btRemoteServerSync extends Thread {
    private static final int TaskCooldownMS = 5000;

    @Override
    public void run() {
        InternalTimer.schedule( InternalTask, TaskCooldownMS, TaskCooldownMS );
    }

    // Oh, I wish Android supported Lambda's.
    private Timer InternalTimer = new Timer();
    private TimerTask InternalTask = new TimerTask() {
        @Override
        public void run() {
            synchronized( this ) {
                if( btConnectionWatcher.ConnectedToServer ) {
                    // Sync Pantry
                    for( btProduct Product : btInventoryHandler.PantryContents() ) {
                        btHttpClient.PostToServer( Product );
                    }

                    for( btProduct Product : btInventoryHandler.ShoppingListContents() ) {
                        btHttpClient.PostToServer( Product );
                    }
                }
            }
        }
    };
}
