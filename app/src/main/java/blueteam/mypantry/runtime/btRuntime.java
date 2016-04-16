package blueteam.mypantry.runtime;


public class btRuntime {
    public static final boolean IsBetaBuild = true;

    public static void HandleFatalError( String Message ) {
        LogMessage( Message );
        ShutdownApplication();
    }

    public static void LogMessage( String Message ) {
        System.out.println( Message );
    }

    public static void ShutdownApplication() {
        System.exit( -1 );
    }

    public static void HandleException( Exception Except ) {
        Except.printStackTrace( System.out );
    }

    public static void HandleException( Exception Except, boolean ShutdownAfterThrow ) {
        HandleException( Except );

        if( ShutdownAfterThrow ) {
            ShutdownApplication();
        }
    }
}