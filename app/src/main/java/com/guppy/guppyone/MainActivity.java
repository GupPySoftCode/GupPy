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

    TextView Output;
    EditText CodeArea;
    Button Run,copyCode1,copyCode2,clearCodeButton,clearOutputButton;

    ClipboardManager clipboardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Output = (TextView)findViewById(R.id.output);
        Output.setMovementMethod(new ScrollingMovementMethod());
        CodeArea = (EditText)findViewById(R.id.codeArea);
        Run = (Button)findViewById(R.id.run);
        copyCode1 = (Button)findViewById(R.id.copyCode);
        copyCode2 = (Button)findViewById(R.id.copyOutput);
        clearCodeButton = (Button)findViewById(R.id.clearCode);
        clearOutputButton = (Button)findViewById(R.id.clearOutput);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        //now on click on run button we will extract code from code area data and send that data to our python script...

        Run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Python py = Python.getInstance();

                //here we call out script with the name "myscript"


                PyObject pyobj = py.getModule("myscript");

                //and call main method inside script....//pass data here
                PyObject obj = pyobj.callAttr("main", CodeArea.getText().toString());

                //here we will set returned value of our python script to our output textview
                Output.setText(obj.toString());
            }
        });

        copyCode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeTextCopy = CodeArea.getText().toString();
                ClipData clipData = ClipData.newPlainText("text", codeTextCopy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this,"Code Copied", Toast.LENGTH_SHORT).show();
            }
        });


        copyCode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outputTextCopy = Output.getText().toString();
                ClipData clipData = ClipData.newPlainText("text", outputTextCopy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this,"Output Copied", Toast.LENGTH_SHORT).show();
            }
        });

        clearCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeArea.setText("");
            }
        });

        clearOutputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Output.setText("");
            }
        });

        //this will start python

        //now create python instance
        //Python py = Python.getInstance();
        //now create python object
        //PyObject pyobj = py.getModule("myscript"); //give python script name

        //now call this function

        //PyObject obj = pyobj.callAttr("main");

        //now set returned text to textview

        //textView.setText(obj.toString());


    }
}