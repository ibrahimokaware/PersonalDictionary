package com.rivierasoft.personaldictionary.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rivierasoft.personaldictionary.MyDatabase;
import com.rivierasoft.personaldictionary.R;
import com.rivierasoft.personaldictionary.databinding.FragmentCategoriesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment implements View.OnClickListener {

    private FragmentCategoriesBinding binding;

    private MyDatabase database;

    private int countAll, countA, countB, countC, countD, countE;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new MyDatabase(getContext());

        countAll = database.selectWords().size();
        countA = database.selectWordCat(WordsFragment.groupA).size();
        countB = database.selectWordCat(WordsFragment.groupB).size();
        countC = database.selectWordCat(WordsFragment.groupC).size();
        countD = database.selectWordCat(WordsFragment.groupD).size();
        countE = database.selectWordCat(WordsFragment.groupE).size();

        binding.catTVAll.setText("("+countAll+")");
        binding.catTVA.setText("("+countA+")");
        binding.catTVB.setText("("+countB+")");
        binding.catTVC.setText("("+countC+")");
        binding.catTVD.setText("("+countD+")");
        binding.catTVE.setText("("+countE+")");

        binding.catLLAll.setOnClickListener(this::onClick);
        binding.catLLA.setOnClickListener(this::onClick);
        binding.catLLB.setOnClickListener(this::onClick);
        binding.catLLC.setOnClickListener(this::onClick);
        binding.catLLD.setOnClickListener(this::onClick);
        binding.catLLE.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (countAll != 0)
            switch (v.getId()) {
                case R.id.catLLAll:
                    BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.mainBNV);
                    bottomNavigationView.setSelectedItemId(R.id.item_words);
                    break;
                case R.id.catLLA:
                    if (countA != 0)
                        fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("a", null))
                                .addToBackStack(null)
                                .commit();
                    break;
                case R.id.catLLB:
                    if (countB != 0)
                        fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("b", null))
                                .addToBackStack(null)
                                .commit();
                    break;
                case R.id.catLLC:
                    if (countC != 0)
                        fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("c", null))
                                .addToBackStack(null)
                                .commit();
                    break;
                case R.id.catLLD:
                    if (countD != 0)
                        fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("d", null))
                                .addToBackStack(null)
                                .commit();
                    break;
                case R.id.catLLE:
                    if (countE != 0)
                        fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance("e", null))
                                .addToBackStack(null)
                                .commit();
                    break;
        }
    }
}