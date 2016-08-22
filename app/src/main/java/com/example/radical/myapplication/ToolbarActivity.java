package com.example.radical.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.radical.myapplication.R;
import com.example.radical.myapplication.adapter.PlanetAdapter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToolbarActivity extends AppCompatActivity implements PlanetAdapter.OnItemClickListener{

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer)
    DrawerLayout drawer;

    @BindView(R.id.left_drawer)
    RecyclerView mDrawerList;

    private String[] mPlanetTitles;
    ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tbtest, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        ButterKnife.bind(this);
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        // set a custom shadow that overlays the main content when the drawer opens
        drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // improve performance by indicating the list if fixed size.
        mDrawerList.setHasFixedSize(true);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new PlanetAdapter(mPlanetTitles, this));
        // Set the list's click listener
        /*mDrawerList.setOnItemClickListener(new DrawerItemClickListener());*/

//        toolbar.setTitle("Open");
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mTitle);
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mTitle);
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();
        drawer.setDrawerListener(mDrawerToggle);
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawer.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /*public void onClick(View view) {
        drawer.openDrawer(Gravity.LEFT);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        switch (id) {
            case R.id.action_album:
                Toast.makeText(ToolbarActivity.this, "您选择了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_collection:
                Toast.makeText(ToolbarActivity.this, "您选择了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_card:
                Toast.makeText(ToolbarActivity.this, "您选择了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_settings:
                Toast.makeText(ToolbarActivity.this, "您选择了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_feed:
                Toast.makeText(ToolbarActivity.this, "你选择了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getSupportActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectItem(i);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int i) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, i);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(i, true);
        setTitle(mPlanetTitles[i]);
        drawer.closeDrawer(mDrawerList,true);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        public static Fragment newInstance(int position) {
            Fragment fragment = new PlanetFragment();
            Bundle args = new Bundle();
            args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];
            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
*/

}
