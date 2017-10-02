package com.example.zijian1_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
 * Main activity for the display
 */

public class MainActivity extends Activity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView counter_List;
    private int choice = -1;
    private ArrayList<Counter> counters;
    private ArrayAdapter<Counter> adapter;

    /**
     * constructor of main activity
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button new_Button = (Button) findViewById(R.id.add);
        Button edit_Button = (Button) findViewById(R.id.edit);
        Button incre_Button = (Button) findViewById(R.id.increase);
        Button decre_Button = (Button) findViewById(R.id.decrease);
        Button delete_Button = (Button) findViewById(R.id.delete);
        Button reset_Button = (Button) findViewById(R.id.reset);
        counter_List = (ListView) findViewById(R.id.counterList);

        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // respond to the click
                choice = position;
            }
        };
        counter_List.setOnItemClickListener(mMessageClickedHandler);

        new_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                add_intent(v);
                loadFromFile();
                adapter.notifyDataSetChanged();
            }

        });

        edit_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (choice != -1){
                    setResult(RESULT_OK);
                    edit_intent();
                    loadFromFile();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        incre_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (choice != -1){
                    setResult(RESULT_OK);
                    Counter s=counters.get(choice);
                    s.increase();
                    saveInFile();
                    adapter.notifyDataSetChanged();
                }
            }

        });

        decre_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (choice != -1){
                    setResult(RESULT_OK);
                    Counter s=counters.get(choice);
                    s.decrease();
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });

        delete_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (choice != -1){
                    setResult(RESULT_OK);
                    counters.remove(choice);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });

        reset_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (choice != -1){
                    setResult(RESULT_OK);
                    Counter s=counters.get(choice);
                    int init=s.getInit();
                    s.setValue(init);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });

    }

    /**
     * Switch to edit activity
     */

    public void edit_intent(){
        Intent edit = new Intent(this, Edit_Activity.class);
        edit.putExtra(EXTRA_MESSAGE, choice);
        startActivityForResult(edit,RESULT_OK);
    }

    /**
     * Switch to add activity
     * @param view
     */

    public void add_intent(View view) {
        Intent intent = new Intent(this, Add_Activity.class);
        startActivityForResult(intent,RESULT_OK);
    }

    /**
     * Backing up
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
     * save the current counters into file
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
     * start the activity and create the adapter
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Counter>(this,
                R.layout.list_item, counters);//adapter converts tweet to string
        counter_List.setAdapter(adapter);
    }

}
