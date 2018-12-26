package com.udylity.balloffortune;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class BallofFortune extends AppCompatActivity{

	private SharedPreferences getPrefs;

	private int randomnumber;
	private String[] answers;

	private TextView tv;

	private Shaker shaker;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		answers = getResources().getStringArray(R.array.answers);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater menucreate = getMenuInflater();
		menucreate.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			Intent settingsIntent = new Intent(BallofFortune.this,Settings.class);
			startActivity(settingsIntent);
			return true;
		}
		return false;
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("fortunetext", tv.getText().toString());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		tv.setText(savedInstanceState.getString("fortunetext"));
	}

	private final Shaker.Callback onShake = new Shaker.Callback() {

		public void shakingStopped() {
			boolean settingvibrate = getPrefs.getBoolean("vibrate", true);
			if (settingvibrate) {
				Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vib.vibrate(200);
			}

			randomnumber = new Random().nextInt(answers.length);
			tv.setText(answers[randomnumber]);
		}


		public void shakingStarted() {
			// dont need to do
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		shaker.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		shaker = new Shaker(this, 1.45d, 500, onShake);
	}

}