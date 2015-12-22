package hallogreen.settings;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || IndividualPlantPreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || DataSyncPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);
            System.out.println("Runing Genaral Settings!!!!");

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            //bindPreferenceSummaryToValue(findPreference("example_text"));
            //bindPreferenceSummaryToValue(findPreference("example_list"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            //bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSyncPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_data_sync);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            //bindPreferenceSummaryToValue(findPreference("sync_frequency"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class IndividualPlantPreferenceFragment extends PreferenceFragment{
        //获取植物列表
        public static String[] plant_list;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_individual_plant);
            PreferenceScreen screen = this.getPreferenceScreen();
            setHasOptionsMenu(true);
            //获取植物列表，假定保存在xml中
            plant_list = this.getResources().getStringArray(R.array.plant_list);

            for (String current_plant : plant_list) {
                PreferenceCategory current_category = new PreferenceCategory(screen.getContext());
                current_category.setTitle(current_plant);
                screen.addPreference(current_category);
                //health standards for editText settings
                EditTextPreference health_standard_water_sup = new EditTextPreference(screen.getContext());
                health_standard_water_sup.setTitle("植物生长健康时，湿度不高于（百分比）");
                health_standard_water_sup.setDefaultValue("90");
                health_standard_water_sup.setSummary("summary");//??
                health_standard_water_sup.setKey(current_plant + "_health_" + "water_sup");

                EditTextPreference health_standard_water_inf = new EditTextPreference(screen.getContext());
                health_standard_water_inf.setTitle("植物生长健康时，湿度不低于（百分比）");
                health_standard_water_inf.setDefaultValue("10");
                health_standard_water_inf.setSummary("summary");//??
                health_standard_water_inf.setKey(current_plant + "_health_" + "water_inf");

                EditTextPreference health_standard_temperature_sup = new EditTextPreference(screen.getContext());
                health_standard_temperature_sup.setTitle("植物生长健康时，温度不高于（摄氏度）");
                health_standard_temperature_sup.setDefaultValue("50");
                health_standard_temperature_sup.setSummary("summary");//??
                health_standard_temperature_sup.setKey(current_plant + "_health_" + "temperature_sup");

                EditTextPreference health_standard_temperature_inf = new EditTextPreference(screen.getContext());
                health_standard_temperature_inf.setTitle("植物生长健康时，温度不低于（摄氏度）");
                health_standard_temperature_inf.setDefaultValue("-10");
                health_standard_temperature_inf.setSummary("summary");//??
                health_standard_temperature_inf.setKey(current_plant + "_health_" + "temperature_inf");


                EditTextPreference health_standard_sunshine_sup = new EditTextPreference(screen.getContext());
                health_standard_sunshine_sup.setTitle("植物生长健康时，光照不高于（百分比）");
                health_standard_sunshine_sup.setDefaultValue("80");
                health_standard_sunshine_sup.setSummary("summary");//??
                health_standard_sunshine_sup.setKey(current_plant + "_health_" + "sunshine_sup");

                EditTextPreference health_standard_sunshine_inf = new EditTextPreference(screen.getContext());
                health_standard_sunshine_inf.setTitle("植物生长健康时，光照不低于（百分比）");
                health_standard_sunshine_inf.setDefaultValue("10");
                health_standard_sunshine_inf.setSummary("summary");//??
                health_standard_sunshine_inf.setKey(current_plant + "_health_" + "sunshine_inf");

                //dying standards editext settings
                EditTextPreference dying_standard_water_sup = new EditTextPreference(screen.getContext());
                dying_standard_water_sup.setTitle("植物濒临灭绝，湿度超过（百分比）");
                dying_standard_water_sup.setDefaultValue("90");
                dying_standard_water_sup.setSummary("summary");//??
                dying_standard_water_sup.setKey(current_plant + "_dying_" + "water_sup");

                EditTextPreference dying_standard_water_inf = new EditTextPreference(screen.getContext());
                dying_standard_water_inf.setTitle("植物濒临灭绝，湿度低于（百分比）");
                dying_standard_water_inf.setDefaultValue("10");
                dying_standard_water_inf.setSummary("summary");//??
                dying_standard_water_inf.setKey(current_plant + "_dying_" + "water_inf");

                EditTextPreference dying_standard_temperature_sup = new EditTextPreference(screen.getContext());
                dying_standard_temperature_sup.setTitle("植物濒临灭绝，温度超过（摄氏度）");
                dying_standard_temperature_sup.setDefaultValue("50");
                dying_standard_temperature_sup.setSummary("summary");//??
                dying_standard_temperature_sup.setKey(current_plant + "_dying_" + "temperature_sup");

                EditTextPreference dying_standard_temperature_inf = new EditTextPreference(screen.getContext());
                dying_standard_temperature_inf.setTitle("植物濒临灭绝，温度低于（摄氏度）");
                dying_standard_temperature_inf.setDefaultValue("-10");
                dying_standard_temperature_inf.setSummary("summary");//??
                dying_standard_temperature_inf.setKey(current_plant + "_dying_" + "temperature_inf");


                EditTextPreference dying_standard_sunshine_sup = new EditTextPreference(screen.getContext());
                dying_standard_sunshine_sup.setTitle("植物濒临灭绝，光照超过（百分比）");
                dying_standard_sunshine_sup.setDefaultValue("80");
                dying_standard_sunshine_sup.setSummary("summary");//??
                dying_standard_sunshine_sup.setKey(current_plant + "_dying_" + "sunshine_sup");

                EditTextPreference dying_standard_sunshine_inf = new EditTextPreference(screen.getContext());
                dying_standard_sunshine_inf.setTitle("植物濒临灭绝，光照低于（百分比）");
                dying_standard_sunshine_inf.setDefaultValue("10" );
                dying_standard_sunshine_inf.setSummary("summary");//??
                dying_standard_sunshine_inf.setKey(current_plant + "_dying_" + "sunshine_inf");

                //add preference to category
                current_category.addPreference(health_standard_sunshine_inf);
                current_category.addPreference(health_standard_sunshine_sup);
                current_category.addPreference(health_standard_temperature_inf);
                current_category.addPreference(health_standard_temperature_sup);
                current_category.addPreference(health_standard_water_inf);
                current_category.addPreference(health_standard_water_sup);
                current_category.addPreference(dying_standard_sunshine_inf);
                current_category.addPreference(dying_standard_sunshine_sup);
                current_category.addPreference(dying_standard_temperature_inf);
                current_category.addPreference(dying_standard_temperature_sup);
                current_category.addPreference(dying_standard_water_inf);
                current_category.addPreference(dying_standard_water_sup);
                setPreferenceScreen(screen);
            }

        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
