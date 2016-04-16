package blueteam.mypantry.http;


import android.os.AsyncTask;
import blueteam.mypantry.core.btProduct;
import blueteam.mypantry.runtime.btRuntime;

public class btHttpAsyncPost extends AsyncTask<btProduct, Void, String > {
    @Override
    protected String doInBackground( btProduct... PostData ) {
        return HttpBackend.AttemptPost( PostData[ 0 ] );
    }

    @Override
    protected void onPostExecute( String Result ) {
        btRuntime.LogMessage( "[Async] data sent -> " + Result );
    }

    private btHttpBackend HttpBackend = new btHttpBackend();
}
