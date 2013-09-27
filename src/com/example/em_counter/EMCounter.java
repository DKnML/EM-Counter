/*

EM-Counter (ver0.1 Alpha 1)

Powered by DKnML

*/

package com.example.em_counter;

import java.util.Timer;
import java.util.TimerTask;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
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
boolean recState = false;

Timer EMTimer = new Timer(); //Create timer

	public boolean dispatchKeyEvent(KeyEvent event) { //This function in called, when any key is pressed, like the Volumekeys...
		int action = event.getAction(); //get the button state
		int keyCode = event.getKeyCode(); //get the button keycode
		
		if ((keyCode == 24) && (action == 0)) {
			
			incEM(); //Increase EM-Value
				
		}
		else  if ((keyCode == 25) && (action == 0)) {
			
			incEM(); //Increase EM-Value
		}
		
		return true; //Don't ask why :D
		
	}

	public void incEM() { //Increase and show the current EM-Value
		
		emValue++; //Increment total EMs
		showEM();
		
	}
	
	public void showEM() {
		
		TextView total_display = (TextView) findViewById(R.id.textView3); //Get Textview-object to interact (For total EMs)
		total_display.setText("EMs: " + String.valueOf(emValue)); //Print EMs
	
		
	}
	
	public void initTimer() { //All initalisations for the timer
		
		EMTimer.scheduleAtFixedRate(new TimerTask() { //Config timer with "Tick-Function"
			  public void run() { //original "Tick-Function"
				  if (recState) {
					  
					  TimerOVF(); //Call TimerOVF to make the code better readable
					  
				  }
				 
			  }
			}, 1000, 1000); //End of timer config
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { //Initial function
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_em_counter);

		initTimer(); //Initalize the timer
		
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
	
	public void stopRecord(View v) {

		recState = false; //Stop Timer

	}

	public void startRecord(View v) {
		
		recState = true; //Stop Timer
		//EMTimer = new Timer(); //Create new timer
	}
	
	
	public void em_action(View v) { //Button, that is pressed by EM
		
		incEM(); //Increase EM-Value

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
			
			showEM();
			showTime();
			showEMpM();
			
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
