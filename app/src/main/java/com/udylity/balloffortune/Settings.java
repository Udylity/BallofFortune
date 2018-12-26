package com.udylity.balloffortune;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.MenuItem;

public class Settings extends AppCompatPreferenceActivity implements Preference.OnPreferenceClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			checkvibrator();
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Preference moreapps = findPreference("dev");
		Preference rate = findPreference("rate");

		moreapps.setOnPreferenceClickListener(this);
		rate.setOnPreferenceClickListener(this);

	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		switch (preference.getKey()){
			case "dev":
				Intent moreappsIntent = new Intent(Intent.ACTION_VIEW);
				moreappsIntent.setData(
						Uri.parse("https://play.google.com/store/apps/dev?id=4899527104545843739")
				);
				startActivity(moreappsIntent);
				break;
			case "rate":
				Intent rateIntent = new Intent(Intent.ACTION_VIEW);
				String packageName = getApplication().getPackageName();
				rateIntent.setData(
						Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)
				);
				startActivity(rateIntent);
				break;
		}
		return true;
	}

	@TargetApi(11)
	private void checkvibrator() {
		Vibrator myvibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		boolean hasVibrator = myvibrator.hasVibrator();
		if (!hasVibrator) {
			Preference vibrater = findPreference("vibrate");
			vibrater.setEnabled(false);
			vibrater.setSelectable(false);
			vibrater.setSummary(R.string.novibrator);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
