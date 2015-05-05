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

    private Button sendButton;
    private TextView receiver;
    private TextView output;
    private EditText message;

    public SendActionListener() {
        receiver = (TextView) findViewById(R.id.receiverNameTextView);
        output = (TextView) findViewById(R.id.outputTextView);

        message = (EditText) findViewById(R.id.sendEditText);

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        String nom, msg;

        //Placer les values des Textfields dans un string
        nom = receiver.getText().toString();
        msg = message.getText().toString();

        //Dechifremment du message
        //String dechMsg = ChiffrementHandler.decrypt( chiMsg, kh.getPriKey() );
        //reMsgTxt.setText( dechMsg );

        byte[] chiMessage = null;
        String received = null;

        //loader le KeyHandler
        KeyRsaHandler key = new KeyRsaHandler(nom.hashCode());

        //chiffrer le message, retourne un array de char
        chiMessage = ChiffrementHandler.encrypt(msg, key.getPubKey());

        //afficher le message brut
        String bufferString = new String( chiMessage );
        output.setText("Message brut: " + bufferString);

        //Dechifremment du message
        received = ChiffrementHandler.decrypt(chiMessage, key.getPriKey());
        output.append(" Message Decrypter: " + received);
    }
}
