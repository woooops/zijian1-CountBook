package com.example.zijian1_countbook;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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



/**
 * Creating a new counter
 */

public class Add_Activity extends Activity {
    private static final String FILENAME = "file.sav";
    private ArrayList<Counter> counters;

    private EditText name_Text;
    private EditText init_Text;
    private EditText comm_Text;

    /**
     * constructor for an activity
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        Button finButton = (Button) findViewById(R.id.finish);
        name_Text = (EditText) findViewById(R.id.name);
        init_Text = (EditText) findViewById(R.id.init);
        comm_Text = (EditText) findViewById(R.id.com);
        finButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                if (!(init_Text.getText().toString().equals(""))){

                    String name = name_Text.getText().toString();
                    String com = comm_Text.getText().toString();

                    int init_value = (int) Integer.valueOf(init_Text.getText().toString());
                    Counter new_counter = new Counter(name,init_value,com);

                    counters.add(new_counter);
                    saveInFile();
                }
                end();
            }
        });
    }

    /**
     * finish the activity
     */

    private void end(){
        this.finish();
    }

    /**
     * load the old counters
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
     * save the result to file
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

    /**
     * load file when start
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
    }
}