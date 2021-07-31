//package com.example.dailyexpenses;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import com.google.android.material.navigation.NavigationView;
//
//public  class MyAdapter extends AppCompatActivity {
//
//    private DrawerLayout dl;
//    private ActionBarDrawerToggle t;
//    private NavigationView nv;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.draweble_layout);
//
//       // dl = (DrawerLayout)findViewById(R.id.incomefrom);
//       // t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
//
//        dl.addDrawerListener(t);
//      //  t.syncState();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        nv = (NavigationView)findViewById(R.id.nv);
//        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                switch(id)
//                {
//                    case R.id.account:
//                        Toast.makeText(MyAdapter.this, "My Account",Toast.LENGTH_SHORT).show();break;
//                    case R.id.settings:
//                        Toast.makeText(MyAdapter.this, "Settings",Toast.LENGTH_SHORT).show();break;
//                    case R.id.mycart:
//                        Toast.makeText(MyAdapter.this, "My Cart",Toast.LENGTH_SHORT).show();break;
//                    default:
//                        return true;
//                }
//
//
//                return true;
//
//            }
//        });
//
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(t.onOptionsItemSelected(item))
//            return true;
//
//        return super.onOptionsItemSelected(item);
//    }
//}