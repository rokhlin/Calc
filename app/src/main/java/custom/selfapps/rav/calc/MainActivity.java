package custom.selfapps.rav.calc;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import custom.selfapps.rav.calc.adapters.PageAdapter;
import custom.selfapps.rav.calc.currency.CurrencyLoader;
import custom.selfapps.rav.calc.currency.model.Currency;
import custom.selfapps.rav.calc.utils.Logs;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    LoaderManager.LoaderCallbacks<Currency>{


    private TabLayout tabLayout;
    private Loader<Currency> loader;
    private Currency currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {});

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Calculator").setTag(0));
        tabLayout.addTab(tabLayout.newTab().setText("History").setTag(1));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),4);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(Integer.parseInt(tab.getTag().toString()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        loader = getLoaderManager().initLoader(1,Bundle.EMPTY, this);
        loader.forceLoad();//loading actual currency values


    }

    public Currency getCurrency(){
        return this.currency;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Navigation Drawer onItem select Handler
     * @param item
     * @return Will return true for interception current behavior here.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int currentTag = 0;
        try {
            currentTag = Integer.parseInt(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getTag().toString());
        } catch (Exception e) {
            Logs.send(this, e.getMessage());
        }
        if (id == R.id.basic_calc) {
            if(currentTag != 1 || currentTag != 0);
            tabLayout.removeAllTabs();
            tabLayout.addTab(tabLayout.newTab().setText("Calculator").setTag(0));
            tabLayout.addTab(tabLayout.newTab().setText("History").setTag(1));
        } else if (id == R.id.nav_currency) {
            if(currentTag != 2 || currentTag != 3);
            tabLayout.removeAllTabs();
            tabLayout.addTab(tabLayout.newTab().setText("Currency").setTag(2));
            tabLayout.addTab(tabLayout.newTab().setText("History2").setTag(3));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     *
     * @param id - identification of current loader
     * @param args - loader args
     * @return - in Callback function the actual Currency
     */
    @Override
    public Loader<Currency> onCreateLoader(int id, Bundle args) {
         return new CurrencyLoader(getApplicationContext());
    }

    /**
     * Callback function
     * @param loader - Used Loader from holder
     * @param currency - returned value as result
     */
    @Override
    public void onLoadFinished(Loader<Currency> loader, Currency currency) {
        Logs.send(this,"onLoadFinished currency = "+currency.toString());
        this.currency = currency;
    }


    /**
     *  Error callback function will called if will some errors
     * @param loader - Used Loader from holder
     */
    @Override
    public void onLoaderReset(Loader<Currency> loader) {
        Logs.send(this,"onLoaderReset");
    }


}

