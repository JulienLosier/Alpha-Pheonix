package sam.ccnb.chiffrementmobile;

/**
 * Created by Sam on 2015-04-28.
 */

/* Programmeur:		Samuel Lavoie
 * Date:			mars, 2015
 *
 * La classe fait la gestion de les clés d'un tel Contact
 *
 * @Constructeur
 * 		RsaKeyHandler( int contactID )
 * 			vérifie si qu'une paire de cle existe déja pour le contact en question.
 * 			si non, on génère une paire de clé associés avec le contact
 *
 * @methode
 * 		generateKey()
 * 			méthode pour génèrer une paire de clé
 *
 * 		loadKeys()
 * 			place les clés associées à un contact en mémoire
 *
 * 		areKeysPresent()
 * 			éxiste seulement pour verifier si que des cle existe pour l'id
 *
 * 		getContactID | getPubKey | getPrivKey
 * 			getters pour right now, still en mode conception
 */

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import android.util.Log;

public class KeyRsaHandler {
    private static int _STRENGTH = 128; //2048 bits est le "maximum" (4096 work for some reason) du framework (128, 256, 512, 1024)
    private static String _ALGORITHM = "RSA";
    private static String _KEYDIR = "data/";

    //Log stuff
    private static final String TAG = MainActivity.class.getSimpleName();

    private int contactID;
    private PublicKey pubKey;
    private PrivateKey priKey;
    private String pubKeyPath;
    private String priKeyPath;
    private Context ctx;

    public KeyRsaHandler( int contactID , Context ctx) {
        super();
        this.contactID = contactID;
        this.ctx = ctx;

        pubKeyPath = _KEYDIR + Integer.toString( contactID ) + ".pub"; //public key extention
        priKeyPath = _KEYDIR + Integer.toString( contactID );

        if ( !areKeysPresent() )
            generateKey();

        loadKeys();
    }

    /**
     * Cree une paire de clées (public, prive) et les sauvegarder sur le system
     *
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws FileNotFoundException
     *
     * @return nope
     */
    private void generateKey() {
        try {
            Log.d( TAG, "Generation des clées");

            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance( _ALGORITHM );
            keyGen.initialize( _STRENGTH );
            final KeyPair key = keyGen.generateKeyPair();

            File privateKeyFile = new File( ctx.getFilesDir(), priKeyPath );
            File publicKeyFile  = new File( ctx.getFilesDir(), pubKeyPath );

            Log.d( TAG, "Directoire: " + ctx.getFilesDir()+"/"+pubKeyPath);

            // Verifier le directoire et cree les fichier pour stocker les cles
            if ( privateKeyFile.getParentFile() != null )
                privateKeyFile.getParentFile().mkdirs();
            privateKeyFile.createNewFile();

            Log.d( TAG, "Public key crée.");

            if ( publicKeyFile.getParentFile() != null )
                publicKeyFile.getParentFile().mkdirs();
            publicKeyFile.createNewFile();

            Log.d( TAG, "Private key crée");

            // Enregistre la cle public dans un fichier
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                    new FileOutputStream( publicKeyFile )
            );
            publicKeyOS.writeObject( key.getPublic() );
            publicKeyOS.close();

            Log.d( TAG, "Public key est dans la memoire.");
            /////////////////////////////////////////////////////////
            // Enregistre la cle prive dans un fichier
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream( privateKeyFile )
            );
            privateKeyOS.writeObject( key.getPrivate() );
            privateKeyOS.close();
            Log.d( TAG, "Private key est dans la memoire.");

        } catch ( Exception e ) { e.printStackTrace(); }
    }

    /**
     * Méthode pour placer les clées dans la mémoire
     *
     * @return nope
     */
    private void loadKeys() {
        ObjectInputStream inpStrm = null;

        //Load les cles en memoire
        try {
            Log.d( TAG, "Attempt to load keys ... " );
            Log.d( TAG, ctx.getFilesDir() +"/"+ pubKeyPath);

            inpStrm = new ObjectInputStream(
                    new FileInputStream( ctx.getFilesDir()+"/"+ pubKeyPath ));
            pubKey = (PublicKey) inpStrm.readObject();

            ///////////////////
            inpStrm.close(); //BUG FIX: broken pipe, don't touch
            ///////////////////

            Log.d( TAG, ctx.getFilesDir() +"/"+ priKeyPath);
            inpStrm = new ObjectInputStream(
                    new FileInputStream( ctx.getFilesDir()+"/"+priKeyPath ));
            priKey = (PrivateKey) inpStrm.readObject();

            inpStrm.close();
            Log.d( TAG, "Loaded keys from internal memory.");
        }
        catch (FileNotFoundException e) 	{ e.printStackTrace(); }
        catch (IOException e) 			    { e.printStackTrace(); }
        catch (ClassNotFoundException e) 	{ e.printStackTrace(); }
        catch (Exception e)				    { e.printStackTrace(); }
    }

    /**
     * La méthode verifier si la paire de clée éxiste sur le system
     *
     * @return un oui ou non
     */
    private boolean areKeysPresent() {

        File privateKey = new File( ctx.getFilesDir(), priKeyPath );
        File publicKey  = new File( ctx.getFilesDir(), pubKeyPath );

        if ( privateKey.exists() && publicKey.exists() ) {
            Log.d( TAG, "Clé déja existente. Skip!");
            return true;
        }

        return false;
    }

    //Accesseur
    public int getContactID() 		{ return contactID; }
    public PublicKey getPubKey() 	{ return pubKey; }
    public PrivateKey getPriKey() 	{ return priKey; }
}
