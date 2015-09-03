package com.uninorte.androidsensors;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.Locale;

/**
 * Created by Luis on 19/04/2015.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if (position  == 0)
            return Accelerometter.newInstance(position + 1);
        else if (position  == 1)
            return GPS.newInstance(position + 1);
        else if(position == 2)
            return Gyroscope.newInstance(position + 1);
        else if(position == 3)
            return Compass.newInstance(position + 1);
        else return Light.newInstance(position+1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        Resources res = Resources.getSystem();
        switch (position) {
            case 0:
                return res.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return res.getString(R.string.title_section2).toUpperCase(l);
            case 3:
                return "Section 3".toUpperCase(l);
            case 4:
                return "Section 4".toUpperCase(l);
            case 5:
                return "Section 5".toUpperCase(l);
        }
        return null;
    }
}
