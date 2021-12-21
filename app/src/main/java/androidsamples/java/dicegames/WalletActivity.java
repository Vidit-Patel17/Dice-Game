package androidsamples.java.dicegames;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WalletActivity extends AppCompatActivity {
    private String balanceKey = "walletBalance";
    private final String dieKey = "prevDieValue";
    private WalletViewModel wallet = new WalletViewModel();
    private TextView txt_balance;
    private Button btn_die;
    private Button btn_launch_twoormore;

    private void initialise(Bundle savedInstanceState) {
        txt_balance = findViewById(R.id.txt_balance);
        btn_die = findViewById(R.id.btn_die);
        btn_launch_twoormore = findViewById(R.id.btn_launch_twoormore);

        int walletBalance;
        if(savedInstanceState == null)
            walletBalance = getIntent().getIntExtra(balanceKey, 0);
        else {
            walletBalance = savedInstanceState.getInt(balanceKey);
            btn_die.setText(savedInstanceState.getString(dieKey));
        }
        txt_balance.setText("" + walletBalance);

        wallet.setBalance(walletBalance);
        wallet.setWinValue(6);
        wallet.setIncrement(5);
        wallet.setDie(new Die6());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initialise(savedInstanceState);

        btn_die.setOnClickListener(v -> {
            wallet.rollDie();
            btn_die.setText("" + wallet.dieValue());
            txt_balance.setText("" + wallet.balance());
        });

        btn_launch_twoormore.setOnClickListener(v -> {
            Intent intent = new Intent(this, TwoOrMoreActivity.class);
            intent.putExtra(balanceKey, wallet.balance());
            startActivity(intent);
            this.finish();
        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(balanceKey, wallet.balance());
        outState.putString(dieKey, btn_die.getText().toString());
    }


}