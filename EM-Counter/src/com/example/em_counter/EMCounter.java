package com.example.em_counter;

import java.util.Timer;
import java.util.TimerTask;
//import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class EMCounter extends Activity {

double seconds = 0; //Global variable to count the seconds
double EMpM = 0; //Global variable to count the EMs/min
int emValue = 0; //Global variable to count the EMs
int emReset = 0; //Global variable to count the reset-trys
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { //Initial function
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_em_counter);
		Timer timer = new Timer(); //Create timer
		timer.scheduleAtFixedRate(new TimerTask() { //Config timer with "Tick-Function"
			  public void run() { //original "Tick-Function"
				  TimerOVF(); //Call TimerOVF to make the code better readable
			  }
			}, 1000, 1000); //End of timer config
		
	}

	public void TimerOVF() { //Execute this Method every second
		seconds++;	//Increment Seconds 
		showTime();	//Call function, that shows the Recording time
		showEMpM();	//Display the EMs per minute (EMs/min)
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_em_counter, menu);
		return true;
	}
	
	public void em_action(View v) { //Button, that is pressed by EM
		emValue++; //Increment total EMs
		TextView total_display = (TextView) findViewById(R.id.textView3); //Get Textview-object to interact (For total EMs)
		total_display.setText("EMs: " + String.valueOf(emValue)); //Print EMs
			
	}

	public void showEMpM() {
		final TextView EMpMDisplay = (TextView) findViewById(R.id.textView1); //Get Textview-object to interact (For EMs/min)
		
		EMpM = (emValue / (seconds / 60)); //Calculate EMs/min
		
	    this.runOnUiThread(new Runnable() { //Make new thread to print the EMs/min-Text out of timerroutine
	        public void run() { //Threadfunction
	            EMpMDisplay.setText(String.valueOf(((double) ((int) (EMpM * 100))) / 100) + " EMs/min"); //Round EMs/min-value
	        } //End of threadfunction
	    });
	}
	
	public void showTime() { //Show the recording-time
		final TextView timeDisplay = (TextView) findViewById(R.id.textView2);//Get Textview-object to interact (For Time)
	    this.runOnUiThread(new Runnable() { //Make new thread to print the time out of timerroutine
	        public void run() { //Threadfunction
	            timeDisplay.setText(String.valueOf((int) seconds) + " seconds"); //Show time
	        }
	    });
	}
	
	public void em_reset(View v) { //Reset-button pressed

		emReset++; //Count Resetbuton-touchs
		if (emReset == 10) { //If resetbutton was pressed 10 times -> reset all variables

			emReset = 0; //Set the resetcounter to 0
			emValue = 0; //Set the total EMs to 0
			seconds = 0; //Set the seconds
			TextView total_display = (TextView) findViewById(R.id.textView3); //Get Textview-object to interact (For EMs after Reset)
			total_display.setText("EMs: " + String.valueOf(emValue));
			TextView reset_display = (TextView) findViewById(R.id.textView5); //Get Textview-object to interact (Reset-display after reset)
			reset_display.setText(""); //Reset reset-display :D
		} else {
			
			TextView reset_display = (TextView) findViewById(R.id.textView5); //Get Textview-object to interact (Reset-display)
			reset_display.setText(String.valueOf(emReset)); //Display how many times you pressed the reset button
			
		}
	}

		
	
}
