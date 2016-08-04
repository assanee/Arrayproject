package xyz.stepsecret.arrayproject3;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import xyz.stepsecret.arrayproject3.Badge.Utils;
import xyz.stepsecret.arrayproject3.TabFragments.HomeFragment;
import xyz.stepsecret.arrayproject3.TabFragments.PromotionFragment;
import xyz.stepsecret.arrayproject3.TabFragments.ShopFragment;
import xyz.stepsecret.arrayproject3.TabFragments.QueueFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActionBar actionBar;

    private int[] tabIcons = {
            R.drawable.house,
            R.drawable.cart1,
            R.drawable.queue,
            R.drawable.promotion
    };

    //public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        //actionBar.setTitle("Home");
        //actionBar.setDisplayHomeAsUpEnabled(true); // show <------
       // actionBar.setDisplayHomeAsUpEnabled(false);




        //fragmentManager = getSupportFragmentManager();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        /*tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();

                actionBar.setTitle(""+tab.getText());

               // Log.e(" Main ",""+position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        */

        setupTabIcons();



    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "Home");
        adapter.addFrag(new ShopFragment(), "Shop");
        adapter.addFrag(new QueueFragment(), "My queue");
        adapter.addFrag(new PromotionFragment(), "Promotion");
        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem control = menu.findItem(R.id.action_control);
        MenuItem mail = menu.findItem(R.id.action_mail);

        // Obtener drawable del item
        LayerDrawable control_icon = (LayerDrawable) control.getIcon();
        LayerDrawable mail_icon = (LayerDrawable) mail.getIcon();

        // Actualizar el contador
        Utils.setBadgeCount(this, control_icon, 3);
        Utils.setBadgeCount(this, mail_icon, 3);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_control) {
            Log.e(" menu "," control ");

            LayerDrawable control_icon = (LayerDrawable) item.getIcon();


            Utils.setBadgeCount(this, control_icon, 0);


            return true;
        }
        else if (id == R.id.action_mail) {
            Log.e(" menu "," mail ");

            LayerDrawable mail_icon = (LayerDrawable) item.getIcon();
            Utils.setBadgeCount(this, mail_icon, 0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
