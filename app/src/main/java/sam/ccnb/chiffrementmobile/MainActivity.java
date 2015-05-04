/*  Programmeur: Samuel Lavoie, Jean-Philippe Haché
    Date:   Avril, 2015

    Dernière mise a jour;
        - (Samuel Lavoie, 28 Avril, 2015) implémentation du JAR (pour l'API du chifremment)
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


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private Button sendButton;
    private TextView receiverTextView;
    private TextView output;
    private EditText sent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiverTextView = (TextView) findViewById(R.id.receiverNameTextView);
        output = (TextView) findViewById(R.id.outputTextView);

        sent = (EditText) findViewById(R.id.sendEditText);

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {

        byte[] message = null;
        String received = null;

        KeyRsaHandler key = new KeyRsaHandler(1); //temporaire 

        ChiffrementHandler chiffrement = new ChiffrementHandler();

        message = chiffrement.encrypt(sent.getText().toString(),key.getPubKey());

        received = chiffrement.decrypt(message,key.getPriKey());

        output.setText(received);
    }
}
