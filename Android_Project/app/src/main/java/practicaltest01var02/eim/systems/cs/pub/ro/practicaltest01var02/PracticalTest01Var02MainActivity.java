package practicaltest01var02.eim.systems.cs.pub.ro.practicaltest01var02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var02MainActivity extends AppCompatActivity {

    private Button plus_button, minus_button, navigate_button, stop_service_button;
    private EditText input1, input2;
    private TextView out_text;
    private int REQUEST_CODE = 1;
    private boolean op_done = false;
    private boolean isServiceStarted = false;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = null;

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            Log.d("[BroadcastReceiver]", msg);
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.plus_button:
                    if(verifyIntegrity()) {
                        int arg1 = Integer.parseInt(input1.getText().toString());
                        int arg2 = Integer.parseInt(input2.getText().toString());
                        out_text.setText(input1.getText().toString() + " + " + input2.getText().toString() +
                                " = " + String.valueOf(arg1 + arg2));
                        op_done = true;
                    }
                    break;
                case R.id.minus_button:
                    if(verifyIntegrity()) {
                        int arg11 = Integer.parseInt(input1.getText().toString());
                        int arg22 = Integer.parseInt(input2.getText().toString());
                        out_text.setText(input1.getText().toString() + " - " + input2.getText().toString() +
                                " = " + String.valueOf(arg11 - arg22));
                        op_done = true;
                    }
                    break;
                case R.id.navigate_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02SecondaryActivity.class);
                    if(verifyIntegrity()) {
                        intent.putExtra("msg", out_text.getText().toString());
                    }
                    else {
                        intent.putExtra("msg", "NULL");
                    }
                    startActivityForResult(intent, REQUEST_CODE);
                    break;
                case R.id.stop_service_button:
                    Intent intent_stop_service = new Intent(getApplicationContext(), PracticalTest01Var02Service.class);
                    stopService(intent_stop_service);
                    isServiceStarted = false;
                    op_done = false;
                    break;
            }

            ShouldStartService();
        }
    }

    public boolean verifyIntegrity() {
        if ( (input1.getText().toString().matches("")) ||
                (input2.getText().toString().matches(""))) {
            Toast.makeText(getApplicationContext(), "Error: One of the inputs are NULL",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void ShouldStartService() {
        if(op_done && !isServiceStarted) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02Service.class);

            int arg1 = Integer.parseInt(input1.getText().toString());
            int arg2 = Integer.parseInt(input2.getText().toString());
            intent.putExtra("arg1", arg1);
            intent.putExtra("arg2", arg2);

            startService(intent);
            isServiceStarted = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_main);

        input1 = findViewById(R.id.input_up);
        input2 = findViewById(R.id.input_down);
        plus_button = findViewById(R.id.plus_button);
        minus_button = findViewById(R.id.minus_button);
        navigate_button = findViewById(R.id.navigate_button);
        out_text = findViewById(R.id.result_text);
        stop_service_button = findViewById(R.id.stop_service_button);

        plus_button.setOnClickListener(buttonClickListener);
        minus_button.setOnClickListener(buttonClickListener);
        navigate_button.setOnClickListener(buttonClickListener);
        stop_service_button.setOnClickListener(buttonClickListener);

        intentFilter = new IntentFilter();
        for(int i = 0; i < Constants.actions.length; ++i) {
            intentFilter.addAction(Constants.actions[i]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, PracticalTest01Var02Service.class);
        stopService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(!input1.getText().toString().matches("")) {
            int arg1 = Integer.parseInt(input1.getText().toString());
            outState.putInt("arg1", arg1);
        }

        if(!input2.getText().toString().matches("")) {
            int arg2 = Integer.parseInt(input2.getText().toString());
            outState.putInt("arg2", arg2);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey("arg1")) {
                Toast.makeText(getApplicationContext(), "First number is: " +
                    String.valueOf(savedInstanceState.getInt("arg1")), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "First number is NULL", Toast.LENGTH_SHORT).show();
            }
            if(savedInstanceState.containsKey("arg2")) {
                Toast.makeText(getApplicationContext(), "Second number is: " +
                    String.valueOf(savedInstanceState.getInt("arg2")), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Second number is NULL", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "First && Second number is NULL", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            Toast.makeText(getApplicationContext(), "Activity returned with result: " + resultCode,
                    Toast.LENGTH_LONG).show();
        }
    }
}
