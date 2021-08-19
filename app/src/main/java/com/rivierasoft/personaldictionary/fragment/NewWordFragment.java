package com.rivierasoft.personaldictionary.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rivierasoft.personaldictionary.MyDatabase;
import com.rivierasoft.personaldictionary.R;
import com.rivierasoft.personaldictionary.databinding.FragmentNewWordBinding;
import com.rivierasoft.personaldictionary.model.Word;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewWordFragment extends Fragment {

    private FragmentNewWordBinding binding;

    private MyDatabase database;

    private Word word;

    private boolean isExist = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1 = -1;
    private String mParam2;

    public NewWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddWordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewWordFragment newInstance(int param1, String param2) {
        NewWordFragment fragment = new NewWordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewWordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new MyDatabase(getContext());

        if (mParam1 != -1) {
            word = database.selectWords(mParam1).get(0);
            binding.addWordET.setText(word.getText());
            if (word.getNote() != null)
                binding.addWordETNote.setText(word.getNote());
            binding.addWordButton.setText("SAVE");
        }

        binding.addWordET.addTextChangedListener(textWatcher);
        checkValidation();

        binding.addWordButton.setOnClickListener(v -> {
            String text = binding.addWordET.getText().toString().trim();
            String note;
            if (isEmpty(binding.addWordETNote))
                note = null;
            else note = binding.addWordETNote.getText().toString().trim();
            String firstChar = String.valueOf(text.charAt(0));

            for (Word w : database.selectWords()) {
                if (w.getText().equals(text)) {
                    if (mParam1 != -1) {
                        if (w.getNote() != null) {
                            if (w.getNote().equals(note)) {
                                Toast.makeText(getContext(),"Word Already Exist!", Toast.LENGTH_SHORT).show();
                                isExist = true;
                            } else {
                                isExist = false;
                            }
                        } else {
                            if (note == null) {
                                Toast.makeText(getContext(),"Word Already Exist!", Toast.LENGTH_SHORT).show();
                                isExist = true;
                            } else {
                                isExist = false;
                            }
                        }
                    } else {
                        Toast.makeText(getContext(),"Word Already Exist!", Toast.LENGTH_SHORT).show();
                        isExist = true;
                    }
                    break;
                } else {
                    isExist = false;
                }
            }

            if (mParam1 != -1) {
                if (!isExist) {
                    boolean result = database.updateWord(word.getId(), text, note, getColor(firstChar));

                    if (result) {
                        binding.addWordET.setText("");
                        binding.addWordETNote.setText("");
                        checkValidation();
//                    View parentLayout = getActivity().findViewById(android.R.id.content);
//                    Snackbar.make(parentLayout, "Success Edit!", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Success Edit!",
                                Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getContext(), "Something Went Wrong, Try Again!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!isExist) {
                    boolean result = database.insertWord(new Word(text, note, false,
                            getColor(firstChar)));

                    if (result) {
                        binding.addWordET.setText("");
                        binding.addWordETNote.setText("");
                        checkValidation();
//                    View parentLayout = getActivity().findViewById(android.R.id.content);
//                    Snackbar.make(parentLayout, "Success Add!", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Success Add!",
                                Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getContext(), "Something Went Wrong, Try Again!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private int getColor(String firstChar) {
        int color = 0;

        for (String c : WordsFragment.groupA)
            if (firstChar.equalsIgnoreCase(c)) {
                color = R.color.purple_200;
                break;
            }

        if (color == 0)
            for (String c : WordsFragment.groupB)
                if (firstChar.equalsIgnoreCase(c)) {
                    color = R.color.teal_200;
                    break;
                }

        if (color == 0)
            for (String c : WordsFragment.groupC)
                if (firstChar.equalsIgnoreCase(c)) {
                    color = android.R.color.holo_orange_dark;
                    break;
                }

        if (color == 0)
            for (String c : WordsFragment.groupD)
                if (firstChar.equalsIgnoreCase(c)) {
                    color = android.R.color.holo_red_light;
                    break;
                }

        if (color == 0)
            for (String c : WordsFragment.groupE)
                if (firstChar.equalsIgnoreCase(c)) {
                    color = android.R.color.holo_green_light;
                    break;
                }

        return color;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkValidation();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    void checkValidation() {
        if (isEmpty(binding.addWordET)) {
            binding.addWordButton.setEnabled(false);
            binding.addWordButton.setTextColor(getActivity().getColor(R.color.statusBarLight));
        } else {
            binding.addWordButton.setEnabled(true);
            binding.addWordButton.setTextColor(getActivity().getColor(R.color.primary));
        }
    }

    private boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }
}