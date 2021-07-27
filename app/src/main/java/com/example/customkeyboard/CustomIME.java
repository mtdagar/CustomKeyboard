package com.example.customkeyboard;


import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.example.customkeyboard.views.CandidateView;
import com.example.customkeyboard.views.KeyboardView;

/**
 * Input method implementation for Qwerty keyboard.
 */

public class CustomIME extends InputMethodService implements KeyboardView.KeyboardListener, CandidateView.CandidateListener {
    private final static String TAG = "Playground";
    private StringBuilder currentWord;
    private KeyboardView keyboardView;
    private CandidateView candidateLayout;
    private InputConnection inputConnection;
    //private IterativeWordSuggestion iterativeWordSuggestion;
    private static CustomIME instance;

    public void setSize(KeyboardView.Size size) {
        if (keyboardView != null)
            keyboardView.setSize(size);
    }

    public static CustomIME getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate called");
        instance = this;
        currentWord = new StringBuilder();
        //iterativeWordSuggestion = new IterativeWordSuggestion(getApplicationContext());
        super.onCreate();
    }

    @Override
    public void onInitializeInterface() {
        Log.d(TAG, "onInitialiseInterface called");
    }


    @Override
    public View onCreateInputView() {
        Log.d(TAG, "onCreateInputView called");
        keyboardView = new KeyboardView(getApplicationContext());
        keyboardView.setKeyboardListener(this);
        setCandidatesViewShown(true);
        return keyboardView;
    }

    @Override
    public View onCreateCandidatesView() {
        Log.d(TAG, "onCreateCandidateView called");
        candidateLayout = new CandidateView(getApplicationContext());
        candidateLayout.setWordSelectedListener(this);
        return candidateLayout;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        Log.d(TAG, "onStartInputView"+ KeyboardView.Size.LARGE);
        keyboardView.setSize(KeyboardView.Size.LARGE);
        inputConnection = getCurrentInputConnection();
        keyboardView.reset();
    }

    @Override
    public void onFinishInput() {
        Log.d(TAG, "onFinishInput called");
        super.onFinishInput();
    }

    @Override
    public void onDestroy() {
        //iterativeWordSuggestion.close();
        Log.d(TAG, "onDestroy called");
        super.onDestroy();
    }

    @Override
    public void onCharacterKey(char a) {
        inputConnection.commitText(String.valueOf(a), 1);
        currentWord.append(a);
        //candidateLayout.setSuggestion(iterativeWordSuggestion.getSuggestion(a));
    }

    @Override
    public void onBackspace() {
        inputConnection.deleteSurroundingText(1, 0);
        candidateLayout.setSuggestion(null);
        //iterativeWordSuggestion.reset();
        try {
            currentWord.deleteCharAt(currentWord.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            currentWord = new StringBuilder();
        }
    }

    @Override
    public void onComputeInsets(InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

    @Override
    public void onSpace() {
        inputConnection.commitText(" ", 1);
        candidateLayout.setSuggestion(null);
        //iterativeWordSuggestion.reset();
        currentWord = new StringBuilder();
    }

    @Override
    public void onCap() {
        //TODO
    }

    @Override
    public void onReturn() {
        inputConnection.commitText("\n", 1);
        currentWord = new StringBuilder();
    }

    @Override
    public void onSelectSuggestion(String s) {
        inputConnection.commitText(s.substring(currentWord.length()) + " ", 1);
        //iterativeWordSuggestion.reset();
        //iterativeWordSuggestion.updateFrequency(s);
        currentWord = new StringBuilder();
    }
}
