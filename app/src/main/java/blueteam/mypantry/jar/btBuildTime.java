package blueteam.mypantry.jar;


import blueteam.mypantry.runtime.btRuntime;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class btBuildTime {
    public Date Get() throws URISyntaxException {
        long CallingClassHandle = CallingFilesHandle();

        assert( CallingClassHandle == 0 );

        Date BuildDate = null;
        Class< ? > CurrentClass = new Object(){}.getClass().getEnclosingClass();
        URL CurrentClassURL = CurrentClass.getResource( CurrentClass.getSimpleName() + ".class" );

        if( CurrentClassURL != null ) {
            File NewFile = null;
            String Path = null;

            switch (CurrentClassURL.getProtocol()) {
                case "file":
                    NewFile = new File(CurrentClassURL.toURI());
                    BuildDate = new Date(NewFile.lastModified());
                    break;

                case "jar":
                    Path = CurrentClassURL.getPath();
                    NewFile = new File(Path.substring(5, Path.indexOf("!")));
                    BuildDate = new Date(NewFile.lastModified());

                case "zip":
                    Path = CurrentClassURL.getPath();
                    NewFile = new File(Path.substring(0, Path.indexOf("!")));

                    try {
                        final JarFile CallingJar = new JarFile(NewFile);
                        final ZipEntry CallingZip = CallingJar.getEntry(Path.substring(Path.indexOf("!")));
                        final long ZipFileTimeLong = CallingZip.getTime();
                        final Date ZipFileTimeDate = new Date(ZipFileTimeLong);

                        BuildDate = ZipFileTimeDate;
                    } catch (Exception Except) {
                        btRuntime.HandleException( Except );
                    }
            }
        }

        return BuildDate;
    }

    private long CallingFilesHandle() throws URISyntaxException {
        final URL CallingFile = getClass().getResource(getClass().getSimpleName() + ".class" );

        assert( CallingFile == null );

        Long CallingFileHandle = null;

        switch( CallingFile.getProtocol() ) {
            case "file":
                CallingFileHandle = new File( CallingFile.toURI() ).lastModified();
                break;

            case "jar":
                final String CallingFileClassPath = CallingFile.getPath();
                CallingFileHandle = new File(CallingFileClassPath.substring( 5, CallingFileClassPath.indexOf( "!" ) ) ).lastModified();
                break;

            default:
                btRuntime.LogMessage( "Calling file is of unknown file type." );
        }

        return CallingFileHandle;
    }
}
