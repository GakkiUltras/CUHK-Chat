package hk.edu.cuhk.ie.iems5722.group7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {


    ArrayList<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fragments = new ArrayList<>();
        fragments.add(ChatsListFragment.newInstance());
        fragments.add(FriendsListFragment.newInstance("",""));
        fragments.add(MeFragment.newInstance("",""));
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, fragments.get(0), "CHATS")
                .add(R.id.fragment_container, fragments.get(1), "DASHBOARD")
                .add(R.id.fragment_container, fragments.get(2), "NOTICE")
                .commit();

        fm.beginTransaction()
                .hide(fragments.get(1))
                .hide(fragments.get(2))
                .commit();

        navigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.menu_chats:
                    fm.beginTransaction()
                            .hide(fragments.get(1))
                            .hide(fragments.get(2))
                            .show(fragments.get(0))
                            .commit();
                    return true;
                case R.id.menu_friends:
                    fm.beginTransaction()
                            .hide(fragments.get(0))
                            .hide(fragments.get(2))
                            .show(fragments.get(1))
                            .commit();
                    return true;
                case R.id.menu_me:
                    fm.beginTransaction()
                            .hide(fragments.get(0))
                            .hide(fragments.get(1))
                            .show(fragments.get(2))
                            .commit();
                    return true;
            }
            return false;
        });
    }
}