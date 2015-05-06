/*  Programmeur: Samuel Lavoie, Jean-Philippe Haché
    Date:   Avril, 2015

    Première mise a jour;
        - (Samuel Lavoie, 28 Avril, 2015) implémentation du JAR (pour l'API du chifremment)

    Dernière mise a jour;
        -(Julien Guy Losier, 5 mai, 2015) changer l'application de départ du projet
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

public class MainActivity extends ActionBarActivity{

    private Button sendButton;
    private TextView receiver;
    private TextView output;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = (TextView) findViewById(R.id.receiverNameTextView);
        output = (TextView) findViewById(R.id.outputTextView);
        message = (EditText) findViewById(R.id.sendEditText);

        SendActionListener listener = new SendActionListener(this);

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(listener);
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

    public TextView getReceiver() {
        return receiver;
    }

    public TextView getOutput() {
        return output;
    }

    public EditText getMessage() {
        return message;
    }
}

