package androidsamples.java.dicegames;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TwoOrMoreActivity extends AppCompatActivity {
    private String balanceKey = "walletBalance";
    private String dieKey = "prevDieValues";
    private TwoOrMoreViewModel Wallet = new TwoOrMoreViewModel();
    private Button btn_go;
    private Button btn_back;
    private Button btn_info;
    private RadioGroup radioGroup;
    private TextView txt_balance_twoormore;
    private TextView txt_die1;
    private TextView txt_die2;
    private TextView txt_die3;
    private TextView txt_die4;
    private EditText edit_wager;

    private void setIntent(){
        Intent intent = new Intent(this, WalletActivity.class);
        intent.putExtra(balanceKey, Wallet.balance());
        startActivity(intent);
        this.finish();
    }

    private void initialise (Bundle savedInstanceState) {
        btn_go = findViewById(R.id.btn_go);
        btn_back = findViewById(R.id.btn_back);
        btn_info = findViewById(R.id.btn_info);
        radioGroup = findViewById(R.id.radioGroup);
        txt_balance_twoormore = findViewById(R.id.txt_balance_twoormore);
        txt_die1 = findViewById(R.id.txt_die1);
        txt_die2 = findViewById(R.id.txt_die2);
        txt_die3 = findViewById(R.id.txt_die3);
        txt_die4 = findViewById(R.id.txt_die4);
        edit_wager = findViewById(R.id.edit_wager);

        int walletBalance;
        if(savedInstanceState == null)
            walletBalance = getIntent().getIntExtra(balanceKey, 0);
        else {
            walletBalance = savedInstanceState.getInt(balanceKey);
            setDie_values(savedInstanceState.getStringArrayList(dieKey));
        }
        txt_balance_twoormore.setText("" + walletBalance);

        Wallet.setBalance(walletBalance);
        for(int i=0; i<4; i++)
            Wallet.addDie(new Die6());
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_or_more);
        initialise(savedInstanceState);

        btn_go.setOnClickListener(v -> {
            int wager;
            GameResult gameResult;
            GameType gameType = getGameType();

            try {
                wager = Integer.parseInt(edit_wager.getText().toString());
            } catch(Exception e) {
                Toast.makeText(this, "Enter wager amount", Toast.LENGTH_SHORT).show();
                return;
            }

            Wallet.setWager(wager);
            Wallet.setGameType(gameType);

            try {
                gameResult = Wallet.play();
            } catch (Exception e) {
                Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                return;
            }

            setDie_values(Wallet.diceValues());
            txt_balance_twoormore.setText("" + Wallet.balance());
            edit_wager.setText("");
            edit_wager.onEditorAction(EditorInfo.IME_ACTION_DONE);

            if(gameResult == GameResult.LOSS)
                Toast.makeText(this, "You lost the bet", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "You won the bet", Toast.LENGTH_SHORT).show();
        });

        btn_back.setOnClickListener(v -> { setIntent(); });

        btn_info.setOnClickListener(v -> startActivity(new Intent(this, InfoActivity.class)));
    }

    @Override
    public void onBackPressed(){
        setIntent();
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(balanceKey, Wallet.balance());
        outState.putStringArrayList(dieKey, getDie_values());
    }
    
    private GameType getGameType() {
        GameType gameType;
        int radioBtnId = radioGroup.getCheckedRadioButtonId();
        if(radioBtnId == R.id.btn_2alike)
            gameType = GameType.TWO_ALIKE;
        else if(radioBtnId == R.id.btn_3alike)
            gameType = GameType.THREE_ALIKE;
        else
            gameType = GameType.FOUR_ALIKE;
        return gameType;
    }

    private ArrayList<String> getDie_values() {
        ArrayList<String> die_values = new ArrayList<>();
        die_values.add(txt_die1.getText().toString());
        die_values.add(txt_die2.getText().toString());
        die_values.add(txt_die3.getText().toString());
        die_values.add(txt_die4.getText().toString());
        return die_values;
    }

    private void setDie_values(ArrayList<String> die_values) {
        txt_die1.setText(die_values.get(0));
        txt_die2.setText(die_values.get(1));
        txt_die3.setText(die_values.get(2));
        txt_die4.setText(die_values.get(3));
    }

    private void setDie_values(List<Integer> die_values) {
        txt_die1.setText("" + die_values.get(0));
        txt_die2.setText("" + die_values.get(1));
        txt_die3.setText("" + die_values.get(2));
        txt_die4.setText("" + die_values.get(3));
    }
}