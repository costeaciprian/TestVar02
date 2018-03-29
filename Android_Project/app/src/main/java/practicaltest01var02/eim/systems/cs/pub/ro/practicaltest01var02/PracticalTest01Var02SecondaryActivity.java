package practicaltest01var02.eim.systems.cs.pub.ro.practicaltest01var02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var02SecondaryActivity extends AppCompatActivity {

    private Button correct_button, incorrect_button;
    private TextView result;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.correct_button:
                    setResult(RESULT_OK, null);
                    finish();
                    break;
                case R.id.incorrect_button:
                    setResult(RESULT_CANCELED, null);
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_secondary);

        correct_button = findViewById(R.id.correct_button);
        incorrect_button = findViewById(R.id.incorrect_button);
        result = findViewById(R.id.result_text);

        correct_button.setOnClickListener(buttonClickListener);
        incorrect_button.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        result.setText(msg);
    }
}
