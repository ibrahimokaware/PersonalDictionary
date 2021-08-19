

package com.rivierasoft.personaldictionary.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.ThemeUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rivierasoft.personaldictionary.MyDatabase;
import com.rivierasoft.personaldictionary.OnItemClickListener;
import com.rivierasoft.personaldictionary.R;
import com.rivierasoft.personaldictionary.adapter.WordsAdapter;
import com.rivierasoft.personaldictionary.databinding.FragmentWordsBinding;
import com.rivierasoft.personaldictionary.model.Word;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordsFragment extends Fragment implements TextToSpeech.OnInitListener {

    private FragmentWordsBinding binding;

    private ArrayList<Word> words;
    private WordsAdapter adapter;

    private MyDatabase database;

    private TextToSpeech textToSpeech;

    public static ArrayList<String> groupA, groupB, groupC, groupD, groupE;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WordsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WordsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordsFragment newInstance(String param1, String param2) {
        WordsFragment fragment = new WordsFragment();
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
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWordsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        words = new ArrayList<>();

        database = new MyDatabase(getContext());

        textToSpeech = new TextToSpeech(getContext(), this);

        createGroups();

        if (mParam1 == null)
            words = database.selectWords();
        else {
            switch (mParam1) {
                case "latest":
                    words = database.selectWords();
                    break;
                case "oldest":
                    words = database.selectWordsIdASC();
                    break;
                case "az":
                    words = database.selectWordsTextASC();
                    break;
                case "za":
                    words = database.selectWordsTextDESC();
                    break;
                case "fav":
                    words = database.selectWordsFavorite();
                    break;
                case "a":
                    words = database.selectWordCat(groupA);
                    break;
                case "b":
                    words = database.selectWordCat(groupB);
                    break;
                case "c":
                    words = database.selectWordCat(groupC);
                    break;
                case "d":
                    words = database.selectWordCat(groupD);
                    break;
                case "e":
                    words = database.selectWordCat(groupE);
                    break;
            }
        }

        if (words.size() == 0) {
            binding.wordsTVNo.setVisibility(View.VISIBLE);
            if (mParam1 != null)
                if (mParam1.equals("fav"))
                    binding.wordsTVNo.setText("No Favorites Added Yet!");
        }


        adapter = new WordsAdapter(getContext(), words, (position, id, type) -> {
            Word word = words.get(position);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (type) {
                case "voice" :
                    setSpeed();
                    textToSpeech.speak(words.get(position).getText(), TextToSpeech.QUEUE_FLUSH, null);
                    break;
                case "edit" :
                    fragmentTransaction.replace(R.id.mainFL, NewWordFragment.newInstance(id, null))
                            .addToBackStack(null)
                            .commit();
                    break;
                case "del" :
                    database.deleteWord(id);
                    if (mParam1 != null)
                        fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance(mParam1, null)).commit();
                    else fragmentTransaction.replace(R.id.mainFL, new WordsFragment()).commit();

                    if (words.size() == 0)
                        binding.wordsTVNo.setVisibility(View.VISIBLE);
                    else  binding.wordsTVNo.setVisibility(View.GONE);
                    break;
                case "fav" :
                    if (mParam1 != null) {
                        if (mParam1.equals("fav")) {
                            database.updateFavorite(id, false);
                            fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance(mParam1, null)).commit();

                            if (words.size() == 0) {
                                binding.wordsTVNo.setVisibility(View.VISIBLE);
                                binding.wordsTVNo.setText("No Favorites Added Yet!");
                            } else binding.wordsTVNo.setVisibility(View.GONE);
                        } else {
                            database.updateFavorite(word.getId(), !word.isFavorite());
                            fragmentTransaction.replace(R.id.mainFL, WordsFragment.newInstance(mParam1, null)).commit();
                        }
                    } else {
                        database.updateFavorite(word.getId(), !word.isFavorite());
                        fragmentTransaction.replace(R.id.mainFL, new WordsFragment()).commit();
                    }
                    break;
            }
        });

        binding.wordsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.wordsRV.setHasFixedSize(true);
        binding.wordsRV.setAdapter(adapter);
    }

    private void createGroups() {
        groupA = new ArrayList<>();
        groupB = new ArrayList<>();
        groupC = new ArrayList<>();
        groupD = new ArrayList<>();
        groupE = new ArrayList<>();

        groupA.add("A"); groupA.add("F"); groupA.add("K");
        groupA.add("P"); groupA.add("U"); groupA.add("Z");

        groupB.add("B"); groupB.add("G"); groupB.add("L"); groupB.add("Q"); groupB.add("V");

        groupC.add("C"); groupC.add("H"); groupC.add("M"); groupC.add("R"); groupC.add("W");

        groupD.add("D"); groupD.add("I"); groupD.add("N"); groupD.add("S"); groupD.add("X");

        groupE.add("E"); groupE.add("J"); groupE.add("O"); groupE.add("T"); groupE.add("Y");
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //Log.e("TTS", "LANG_NOT_SUPPORTED");
            } else {
                //buttonSpeak.setEnabled(true);
                //speakOut();
            }

        } else { //Log.e("TTS", "Error");
        }

    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void setSpeed() {
        textToSpeech.setSpeechRate(1.0f);
//        textToSpeech.setSpeechRate(0.1f);
//        textToSpeech.setSpeechRate(0.5f);
//        textToSpeech.setSpeechRate(1.5f);
//        textToSpeech.setSpeechRate(2.0f);
    }

//    private void speakOut() {
//        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//    }

//    private void showWordDialog(int position, int id) {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
//
//        DialogWordBinding dialogWordBinding = DialogWordBinding.inflate(getLayoutInflater());
//        bottomSheetDialog.setContentView(dialogWordBinding.getRoot());
//
//        Word word = database.selectWords(id).get(0);
//
//        dialogWordBinding.wordDTVText.setText(word.getText());
//        if (word.getNote() == null)
//            dialogWordBinding.wordDTVNote.setVisibility(View.GONE);
//        else dialogWordBinding.wordDTVNote.setText(word.getNote());
//        if (word.isFavorite())
//            dialogWordBinding.wordDIVFav.setImageResource(R.drawable.ic_favorite);
//        else dialogWordBinding.wordDIVFav.setImageResource(R.drawable.ic_favorite_border);
//
//        dialogWordBinding.wordDIVFav.setOnClickListener(v -> {
//            if (word.isFavorite()) {
//                dialogWordBinding.wordDIVFav.setImageResource(R.drawable.ic_favorite_border);
//                database.updateFavorite(word.getId(), false);
//                //word.setFavorite(false);
//            } else {
//                dialogWordBinding.wordDIVFav.setImageResource(R.drawable.ic_favorite);
//                database.updateFavorite(word.getId(), true);
//                //word.setFavorite(true);
//            }
//            words = database.selectWords();
//            binding.wordsRV.setAdapter(adapter);
//            //adapter.notifyItemChanged(position);
//        });
//
//        bottomSheetDialog.show();
//    }
}