package edu.udacity.nanodegree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onMovie(View v){
        Intent intent = new Intent(MainActivity.this,MovieActivity.class);
        startActivity(intent);
    }

    public void onScores(View v){
        Toast.makeText(getApplicationContext(),"On Scores",Toast.LENGTH_SHORT).show();
    }

    public void onLibrary(View v){
        Toast.makeText(getApplicationContext(),"On Library",Toast.LENGTH_SHORT).show();
    }

    public void onBuildItBigger(View v){
        Toast.makeText(getApplicationContext(),"On BuildItBigger",Toast.LENGTH_SHORT).show();
    }

    public void onBaconReader(View v){
        Toast.makeText(getApplicationContext(),"On BaconReader",Toast.LENGTH_SHORT).show();
    }

    public void onCapstone(View v){
        Toast.makeText(getApplicationContext(),"On Capstone",Toast.LENGTH_SHORT).show();
    }

}
