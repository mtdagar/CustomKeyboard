package com.example.customkeyboard.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.customkeyboard.R;

import java.util.Arrays;

public class CandidateView extends ConstraintLayout {
    TextView[] list;

    public CandidateView(Context context) {
        super(context);
        init();
    }


    public CandidateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CandidateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.candidate_view, this);
        list = new TextView[3];
        list[0] = findViewById(R.id.suggestion1);
        list[1] = findViewById(R.id.suggestion2);
        list[2] = findViewById(R.id.suggestion3);
    }

    public void setWordSelectedListener(CandidateListener listener) {
        for (TextView view : list) {
            view.setOnClickListener(v -> {
                String text = ((TextView) v).getText().toString();
                if (text.compareTo("") != 0)
                    listener.onSelectSuggestion(text);
                for (TextView t : list)
                    t.setText("");
            });
        }
    }

    public void setSuggestion(String[] suggestion) {
        Log.d("abcd", Arrays.toString(suggestion));
        if (suggestion == null)
            for (TextView t : list)
                t.setText("");
        else {
            list[0].setText(suggestion[0] == null ? "" : suggestion[0]);
            list[1].setText(suggestion[1] == null ? "" : suggestion[1]);
            list[2].setText(suggestion[2] == null ? "" : suggestion[2]);
        }
    }

    public interface CandidateListener {
        void onSelectSuggestion(String s);
    }
}