package com.guppy.guppyone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    TextView Result;
    EditText ScriptField;
    Button RunButton,copyCode1,copyCode2,clearCodeButton,clearOutputButton;
    ClipboardManager cbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Result = (TextView)findViewById(R.id.result);
        Result.setMovementMethod(new ScrollingMovementMethod());
        ScriptField = (EditText)findViewById(R.id.scriptFld);
        RunButton = (Button)findViewById(R.id.runbtn);
        copyCode1 = (Button)findViewById(R.id.copyCode);
        copyCode2 = (Button)findViewById(R.id.copyOutput);
        clearCodeButton = (Button)findViewById(R.id.clearCode);
        clearOutputButton = (Button)findViewById(R.id.clearOutput);
        cbManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        RunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Python p = Python.getInstance();
                PyObject object = p.getModule("pythonCode");
                PyObject po = object.callAttr("main", ScriptField.getText().toString());
                Result.setText(po.toString());
            }
        });

        copyCode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeTextCopy = ScriptField.getText().toString();
                ClipData myClipDataOne = ClipData.newPlainText("text", codeTextCopy);
                cbManager.setPrimaryClip(myClipDataOne);
                Toast.makeText(MainActivity.this,"Code Copied", Toast.LENGTH_SHORT).show();
            }
        });

        copyCode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outputTextCopy = Result.getText().toString();
                ClipData myClipDataTwo = ClipData.newPlainText("text", outputTextCopy);
                cbManager.setPrimaryClip(myClipDataTwo);
                Toast.makeText(MainActivity.this,"Output Copied", Toast.LENGTH_SHORT).show();
            }
        });

        clearCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScriptField.setText("");
            }
        });

        clearOutputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result.setText("");
            }
        });
    }
}