package pt.ipp.estgf.tourguide.Activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 12/01/2016.
 */
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.settingsFrame, new Preferencias());
        ft.commit();
    }
}
