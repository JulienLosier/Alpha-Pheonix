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

public class KeyRsaHandler {
    private static int _STRENGTH = 4096; //2048 bits est le "maximum" (4096 work for some reason) du framework (128, 256, 512, 1024)
    private static String _ALGORITHM = "RSA";
    private static String _KEYDIR = "data/";

    private int contactID;
    private PublicKey pubKey;
    private PrivateKey priKey;
    private String pubKeyPath;
    private String priKeyPath;

    public KeyRsaHandler( int contactID ) {
        super();
        this.contactID = contactID;

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
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance( _ALGORITHM );
            keyGen.initialize( _STRENGTH );
            final KeyPair key = keyGen.generateKeyPair();

            File privateKeyFile = new File( priKeyPath );
            File publicKeyFile  = new File( pubKeyPath );

            // Verifier le directoire et cree les fichier pour stocker les cles
            if ( privateKeyFile.getParentFile() != null )
                privateKeyFile.getParentFile().mkdirs();
            privateKeyFile.createNewFile();

            if ( publicKeyFile.getParentFile() != null )
                publicKeyFile.getParentFile().mkdirs();
            publicKeyFile.createNewFile();

            // Enregistre la cle public dans un fichier
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                    new FileOutputStream( publicKeyFile ) );
            publicKeyOS.writeObject( key.getPublic() );
            publicKeyOS.close();
            /////////////////////////////////////////////////////////
            // Enregistre la cle prive dans un fichier
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream( privateKeyFile ) );
            privateKeyOS.writeObject( key.getPrivate() );
            privateKeyOS.close();

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
            inpStrm = new ObjectInputStream(
                    new FileInputStream( pubKeyPath ));
            pubKey = (PublicKey) inpStrm.readObject();

            ///////////////////
            inpStrm.close(); //BUG FIX: broken pipe, don't touch
            ///////////////////

            inpStrm = new ObjectInputStream(
                    new FileInputStream( priKeyPath ));
            priKey = (PrivateKey) inpStrm.readObject();

            inpStrm.close();
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

        File privateKey = new File( priKeyPath );
        File publicKey  = new File( pubKeyPath );

        if ( privateKey.exists() && publicKey.exists() )
            return true;

        return false;
    }

    //Accesseur
    public int getContactID() 		{ return contactID; }
    public PublicKey getPubKey() 	{ return pubKey; }
    public PrivateKey getPriKey() 	{ return priKey; }
}
