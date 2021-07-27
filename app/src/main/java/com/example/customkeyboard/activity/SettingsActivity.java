package com.example.customkeyboard.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.customkeyboard.CustomIME;
import com.example.customkeyboard.R;
import com.example.customkeyboard.views.KeyboardView;

import java.security.Key;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    EditText editTextTest;
    Button buttonEnableKeyboard;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextTest = findViewById(R.id.etTest);
        buttonEnableKeyboard = findViewById(R.id.button_enable_keyboard);

        buttonEnableKeyboard.setOnClickListener(v -> enableKeyboard());
    }

//    private void setupSize() {
//        RadioGroup group = findViewById(R.id.size);
//
//        group.check(R.id.large);
//
//        group.setOnCheckedChangeListener((group1, checkedId) -> {
//            KeyboardLayout.Size size = null;
//            switch (checkedId) {
//                case R.id.small:
//                    size = KeyboardLayout.Size.SMALL;
//                    break;
//                case R.id.large:
//                    size = KeyboardLayout.Size.LARGE;
//                    break;
//                case R.id.medium:
//                    size = KeyboardLayout.Size.MEDIUM;
//            }
////            SPManager.setSize(getApplicationContext(), size);
//            changeSize(size);
//        });
//    }

    private void changeSize(KeyboardView.Size size) {
        CustomIME ime = CustomIME.getInstance();
        if (ime != null)
            ime.setSize(size);
    }

    void enableKeyboard() {
        InputMethodManager imeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        boolean enabled = false;
        List<InputMethodInfo> listOfKeyboards = imeManager.getEnabledInputMethodList();
        for (InputMethodInfo i : listOfKeyboards) {
            if (i.getPackageName().compareTo(getPackageName()) == 0) {
                if (i.getId().equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD))) {
                    Toast.makeText(SettingsActivity.this, "Keyboard is enabled", Toast.LENGTH_SHORT).show();
                    return;
                }
                enabled = true;
                break;
            }
        }
        if (!enabled) {
            Intent enableIntent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            enableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(enableIntent);
        } else
            imeManager.showInputMethodPicker();
    }
}