package sam.ccnb.chiffrementmobile;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Class created by Julien Guy Losier on 5/5/2015.
 * Code written by Jean-Philippe Haché
 */

import sam.ccnb.chiffrementmobile.KeyRsaHandler;
import sam.ccnb.chiffrementmobile.ChiffrementHandler;

public class SendActionListener extends ActionBarActivity implements View.OnClickListener{

    private MainActivity win;

    public SendActionListener(MainActivity win) {

        this.win = win;
    }

    @Override
    public void onClick(View v) {

        String nom, msg;

        //Placer les values des Textfields dans un string
        nom = win.getReceiver().getText().toString();   //receiver.getText().toString();
        msg = win.getMessage().getText().toString();  //message.getText().toString();

        //Dechifremment du message
        //String dechMsg = ChiffrementHandler.decrypt( chiMsg, kh.getPriKey() );
        //reMsgTxt.setText( dechMsg );

        byte[] chiMessage = null;
        String received = null;

        //loader le KeyHandler
        KeyRsaHandler key = new KeyRsaHandler(nom.hashCode(), win.getApplicationContext());

        //chiffrer le message, retourne un array de char
        chiMessage = ChiffrementHandler.encrypt(msg, key.getPubKey());

        //afficher le message brut
        String bufferString = new String( chiMessage );
        win.getOutput().setText("Message brut: " + bufferString);

        //Dechifremment du message
        received = ChiffrementHandler.decrypt(chiMessage, key.getPriKey());
        win.getOutput().append(" Message Decrypter: " + received);
    }
}
