package sam.ccnb.chiffrementmobile;

/**
 * Created by Sam on 2015-04-28.
 */

/* Programmeur:		Samuel Lavoie
 * Date:			mars, 2015
 *
 * La classe est pour cleaner up mon <<spaghetti logic code>>.
 *
 *
 * @methode
 * 		encrypt( String, PublicKey )
 * 			@return byte[]
 * 				BUG FIX: le return type est un array de byte since les messages
 * 						 sont souvent au dessu de 256 bytes en total.
 *
 * @methode
 * 		decrypt( Byte[], PrivateKey )
 * 			@return String
 */

import java.security.PrivateKey;
import java.security.PublicKey;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;

public class ChiffrementHandler {
    private static String _ALGORITHM = "RSA";
    private static String _ENCODING_FORMAT = "ISO-8859-15";

    public static byte[] encrypt(String message, PublicKey pubKey) {
        byte[] cipherTxt = null;

        try {
            final Cipher cipher = Cipher.getInstance( _ALGORITHM );

            // chifrement avec la cle public
            cipher.init( Cipher.ENCRYPT_MODE, pubKey );
            cipherTxt = cipher.doFinal( message.getBytes( _ENCODING_FORMAT ) );

        } catch ( Exception e ) { e.printStackTrace(); }

        return cipherTxt ;
    }

    public static String decrypt( byte[] message, PrivateKey priKey ) {
        byte[] cipherTxt = null;
        String buffer = null;

        try {
            final Cipher cipher = Cipher.getInstance( _ALGORITHM );

            // dechifrement avec la cle prive
            cipher.init( Cipher.DECRYPT_MODE, priKey );
            cipherTxt = cipher.doFinal( message ); //BUG FIX: don't touch the UTF-8 format pl0x

            buffer = new String( cipherTxt, _ENCODING_FORMAT );

        } catch ( Exception ex ) { ex.printStackTrace(); }

        return buffer;
    }
}
