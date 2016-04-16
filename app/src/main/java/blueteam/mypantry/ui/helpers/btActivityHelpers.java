package blueteam.mypantry.ui.helpers;

import android.content.Context;
import android.content.Intent;

public class btActivityHelpers {
    public static void SwitchView( Context From, Class To ) {
        From.startActivity( new Intent( From, To ) );
    }
}
