package blueteam.mypantry.async;

import blueteam.mypantry.runtime.btRuntime;

import java.util.Timer;
import java.util.TimerTask;


public class btLocalDatabaseSyncer extends Thread {
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
                    ConnectedToServer = true;
                    btRuntime.LogMessage( "[Async] connection watcher -> connected to server." );
                } else {
                    ConnectedToServer = false;
                    btRuntime.LogMessage( "[Async] connection water -> disconnected from server" );
                }
            }
        }
    };

    public static volatile boolean ConnectedToServer = false;
}
