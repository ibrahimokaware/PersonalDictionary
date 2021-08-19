package com.rivierasoft.personaldictionary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.rivierasoft.personaldictionary.R;
import com.rivierasoft.personaldictionary.databinding.ActivityMainBinding;
import com.rivierasoft.personaldictionary.fragment.CategoriesFragment;
import com.rivierasoft.personaldictionary.fragment.NewWordFragment;
import com.rivierasoft.personaldictionary.fragment.WordsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private boolean isItemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        setSupportActionBar(mainBinding.mainTB);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mainBinding.mainBNV.setOnItemSelectedListener(onItemSelectedListener);

        fragmentTransaction.replace(R.id.mainFL, new WordsFragment()).commit();
    }

    NavigationBarView.OnItemSelectedListener onItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            supportInvalidateOptionsMenu();

            switch (item.getItemId()) {
                case R.id.item_words:
                    isItemSelected = false;
                    fragmentTransaction.replace(R.id.mainFL, new WordsFragment()).commit();
                    break;
                case R.id.item_categories:
                    isItemSelected = true;
                    fragmentTransaction.replace(R.id.mainFL, new CategoriesFragment()).commit();
                    break;
                case R.id.item_new_word:
                    //showAddDialog();
                    isItemSelected = true;
                    fragmentTransaction.replace(R.id.mainFL, new NewWordFragment()).commit();
                    break;
            }
            return true;
        }
    };

//    private void showAddDialog() {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//
//        DialogAddBinding addBinding = DialogAddBinding.inflate(getLayoutInflater());
//        bottomSheetDialog.setContentView(addBinding.getRoot());
//
//        bottomSheetDialog.show();
//
//        addBinding.addWord.setOnClickListener(v -> {
//            startActivity(new Intent(this, AddActivity.class).putExtra("fragment", "words"));
//        });
//
//        addBinding.addCategory.setOnClickListener(v -> {
//            startActivity(new Intent(this, AddActivity.class).putExtra("fragment", "categories"));
//        });
//
//        bottomSheetDialog.setOnDismissListener(dialog -> {
//            mainBinding.mainBNV.setSelectedItemId(selectedItemId);
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (isItemSelected) {
            //menu.findItem(R.id.item_search).setVisible(false);
            menu.findItem(R.id.item_sort).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.item_latest:
                fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("latest", null)).commit();
                break;
            case R.id.item_oldest:
                fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("oldest", null)).commit();
                break;
            case R.id.item_az:
                fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("az", null)).commit();
                break;
            case R.id.item_za:
                fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("za", null)).commit();
                break;
            case R.id.item_faz:
                fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("fav", null)).commit();
                break;
        }
        return true;
    }

    //    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        if (mainBinding.mainBNV.getSelectedItemId() != R.id.item_words)
//            mainBinding.mainBNV.setSelectedItemId(R.id.item_words);
//        else finishAffinity();
//    }
}