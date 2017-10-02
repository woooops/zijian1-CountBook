package com.example.zijian1_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Edit activity
 */

public class Edit_Activity extends Activity {

    private static final String FILENAME = "file.sav";
    private int choice = 0;
    private ArrayList<Counter> counters;

    private Counter edited_Counter;
    private EditText name_Text;
    private EditText init_Text;
    private EditText curr_Text;
    private EditText comm_Text;

    /**
     * Constructor of edit activity
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        Button subButton = (Button) findViewById(R.id.submit);
        Intent intent = getIntent();

        choice =  intent.getIntExtra(MainActivity.EXTRA_MESSAGE,0);
        name_Text = (EditText) findViewById(R.id.name);
        init_Text = (EditText) findViewById(R.id.init);
        curr_Text = (EditText) findViewById(R.id.current);
        comm_Text = (EditText) findViewById(R.id.com);

        subButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                setResult(RESULT_OK);
                String name = name_Text.getText().toString();
                int init=(int) Integer.valueOf(init_Text.getText().toString());
                int cur=(int) Integer.valueOf(curr_Text.getText().toString());
                String com = comm_Text.getText().toString();

                edited_Counter.setComment(com);
                edited_Counter.setInit(init);
                edited_Counter.setValue(cur);
                edited_Counter.setName(name);
                saveInFile();
                end();
            }
        });


    }

    /**
     * End the edit processing
     */

    private void end(){
        this.finish();
    }

    /**
     * Load the old counters
     */
    private void loadFromFile() {
        try {
            FileInputStream fis=openFileInput(FILENAME);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Gson gson= new Gson();


            Type listType =new TypeToken<ArrayList<Counter>>(){}.getType();
            counters = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counters= new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Save the result
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);//goes stream based on filename
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(fos)); //create buffer writer
            Gson gson = new Gson();
            gson.toJson(counters,out);//convert java object to json string & save in output
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    /*
     * Load the edited counter to the cuurent one
     */
    public void loadCounter(Counter edited_Counter){

        name_Text.setText(edited_Counter.getName(), TextView.BufferType.EDITABLE);
        init_Text.setText(Integer.toString(edited_Counter.getInit()), TextView.BufferType.EDITABLE);
        comm_Text.setText(edited_Counter.getComment(), TextView.BufferType.EDITABLE);
        curr_Text.setText(Integer.toString(edited_Counter.getCurr()), TextView.BufferType.EDITABLE);

    }

    /**
     * Start activity
     */

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        edited_Counter = counters.get(choice);
        loadCounter(edited_Counter);
    }
}