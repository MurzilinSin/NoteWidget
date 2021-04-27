package com.example.noteswidget;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.noteswidget.fragments.AboutAppFragment;
import com.example.noteswidget.fragments.NotesFragment;
import com.example.noteswidget.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class Navigation {
    private final FragmentManager manager;

    public Navigation(FragmentManager manager){
        this.manager = manager;
    }

    public void addFragment(Fragment fragment, boolean useBackStack) {
        FragmentTransaction fragmentTransaction =
                manager.beginTransaction();
        fragmentTransaction.replace(R.id.notes, fragment);
        if (useBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public void initDrawer(Toolbar toolbar, Activity activity) {
        final DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(navigateFragment(id)){
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    private boolean navigateFragment(int id) {
        switch (id){
            case R.id.action_settings:
                addFragment(new SettingsFragment(),true);
                return true;
            case R.id.action_about:
                addFragment(new AboutAppFragment(),true);
                return true;
            case R.id.action_notes:
                addFragment(new NotesFragment(),true);
                return true;
        }
        return false;
    }
}
