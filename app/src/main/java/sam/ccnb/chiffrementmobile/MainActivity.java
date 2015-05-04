/*  Programmeur: Samuel Lavoie, Jean-Philippe Haché
    Date:   Avril, 2015

    Dernière mise a jour;
        - (Samuel Lavoie, 28 Avril, 2015) implémentation du JAR (pour l'API du chifremment)
        - (Samuel Lavoie) 29 Avril, 2015) implementation d'un layout pour tester le systeme de chiffrement
 */

package sam.ccnb.chiffrementmobile;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Where the magic happens
import sam.ccnb.chiffrementmobile.KeyRsaHandler;
import sam.ccnb.chiffrementmobile.ChiffrementHandler;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Reset a activity_main si vous ne tester pas le system
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_test_system);

        //Comment out si vous ne tester pas le systeme
        final Button chiffrerBtn = (Button) findViewById(R.id.chiffrerButton);

        chiffrerBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.outputTextView);
                EditText nomEdTxt = (EditText) findViewById(R.id.nomEditText);
                EditText msgEdTxt = (EditText) findViewById(R.id.msgEditText);
                String nom, msg;

                //Placer les values des Textfields dans un string
                nom = nomEdTxt.getText().toString();
                msg = msgEdTxt.getText().toString();

                //loader le KeyHandler
                KeyRsaHandler kh = new KeyRsaHandler( nom.hashCode(), getApplicationContext() );

                //chiffrer le message, retourne un array de char
                byte[] chiMsg;
                chiMsg = ChiffrementHandler.encrypt( msg, kh.getPubKey() );

                //afficher le message brut
                String buffer = new String( chiMsg );
                output.setText( "Verify log." ); //new String(chiMsg)
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
