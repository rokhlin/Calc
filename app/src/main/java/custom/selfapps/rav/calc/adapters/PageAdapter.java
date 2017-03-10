package custom.selfapps.rav.calc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import custom.selfapps.rav.calc.fragments.BasicCalcHistoryFragment;
import custom.selfapps.rav.calc.fragments.BasicCalcMainFragment;
import custom.selfapps.rav.calc.fragments.CurrencyConverterFragment;
import custom.selfapps.rav.calc.fragments.CurrencyConverterHistoryFragment;


public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BasicCalcMainFragment.newInstance();
            case 1:
                return BasicCalcHistoryFragment.newInstance("basic_calc",1);
            case 2:
                return CurrencyConverterFragment.newInstance();
            case 3:
                return CurrencyConverterHistoryFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
