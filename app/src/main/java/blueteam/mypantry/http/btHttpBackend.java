package blueteam.mypantry.http;

import android.app.Activity;

import android.os.Bundle;
import blueteam.mypantry.core.btProduct;
import blueteam.mypantry.runtime.btRuntime;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


@SuppressWarnings( "deprecation" )
public class btHttpBackend {
    private static final String GatewayAddress = "http://cdn.executesoftware.com/csc3330_gateway.php";
    private static final String ServerAddress = "http://cdn.executesoftware.com";
    private static final int ServerPort = 443;


    public static boolean HostReady() {
        try {
            final URL GatewayURL = new URL( GatewayAddress );
            final URLConnection Connection = GatewayURL.openConnection();

            return true;
        } catch( Exception Except ) {
            btRuntime.HandleException( Except );
        }

        return false;
    }

    public String AttemptPost( btProduct Packet ) {
        if( !HostReady() ) {
            return "error:bad_connection";
        }
        
        String ServerReturnResult = null;

        try {
            HttpClient Client = new DefaultHttpClient();
            HttpPost Post = new HttpPost( GatewayAddress );
            String PostJSON = null;
            InputStream ServerStream = null;

            JSONObject JObj = new JSONObject();
            JObj.accumulate( "ProductName", Packet.Name );
            JObj.accumulate( "ProductCategory", Packet.Category );
            JObj.accumulate( "ProductPrice", Packet.Price );
            JObj.accumulate( "ProductPurchaseDate", Packet.PurchaseDate );
            JObj.accumulate( "ProductPerishable", Packet.Perishable );
            JObj.accumulate( "ProductPerishDate", Packet.PerishDate );

            PostJSON = JObj.toString();

            final StringEntity StrEntity = new StringEntity( PostJSON );

            Post.setEntity( StrEntity );
            Post.setHeader( "Accept", "application/json" );
            Post.setHeader( "Content-type", "application/json" );

            HttpResponse ServerResponse = Client.execute( Post );

            ServerStream = ServerResponse.getEntity().getContent();
            if( ServerStream == null ) {
                return "error:bad_server_response";
            }

            ServerReturnResult = ReinterpretStreamAsString( ServerStream );
        } catch( Exception Except ) {
            btRuntime.HandleException( Except );
        }

        return ServerReturnResult;
    }

    private String ReinterpretStreamAsString( InputStream Stream ) throws IOException {
        final BufferedReader Reader = new BufferedReader( new InputStreamReader( Stream ) );
        String Line = "";
        String Result = "";

        try {
            do {
                Result += Line;
            } while( ( Line = Reader.readLine() ) != null );
        } catch( Exception Except ) {
            btRuntime.HandleException( Except );
        } finally {
            Stream.close();
        }

        return Result;
    }
}
