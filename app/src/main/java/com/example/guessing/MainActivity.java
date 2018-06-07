package com.example.guessing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Random rand = new Random();
    private Button[] buttons = new Button[12];
    private String[] buttonsText = new String[12];
    private List<Integer> goodButtons = new ArrayList<>(12);
    private Button[] checkedButtons = new Button[2];
    private TextView winsText;
    private Integer winsAmount;
    private final String[] RANDOM_STRINGS = {"X", "Y", "Z", "O", "P", "G"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        winsAmount = 0;
        winsText = (TextView) findViewById(R.id.tv_Win);
        updateText();

        doRandomButtons();

    } //onCreate

    @Override
    public void onClick(View view) {
        if (checkClicked(2)) {
            return;
        } //if

        Integer hint = Integer.parseInt(((Button) view).getHint().toString()) - 1;
        ((Button) view).setText(buttonsText[hint]);

        if (checkedButtons[0] != null) {
            checkedButtons[1] = ((Button) view);
        } else {
            checkedButtons[0] = ((Button) view);
        } //if
    } //onClick

    private void doRandomButtons() {
        for (int i = 0; i < buttons.length; i++) {
            String buttonName = "btn_" + (i + 1);
            int resID = getResources().getIdentifier(buttonName, "id", getPackageName());

            do {
                buttonsText[i] = RANDOM_STRINGS[rand.nextInt(RANDOM_STRINGS.length) + 0];
            } while (checkStringContains(buttonsText, buttonsText[i], 2, i));

            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        } //for
    } //doRandomButtons

    public void checkButton(View view) {
        if (checkClicked(2)) {
            if (checkPair()) {
                goodButtons.add(Integer.parseInt(checkedButtons[0].getHint().toString()) - 1);
                goodButtons.add(Integer.parseInt(checkedButtons[1].getHint().toString()) - 1);
            } //if

            if (goodButtons.size() == 12) {
                goodButtons.clear();
                winsAmount++;
                updateText();
                doRandomButtons();
            } //if

            hideText();
        } //if
    } //onScreenClick

    private void updateText() {
        winsText.setText("Liczba wygranych: " + winsAmount);
    } //updateText()

    private boolean checkPair() {
        if (checkedButtons[0] == null && checkedButtons[1] == null) {
            return false;
        } //if

        if (checkedButtons[0].getText().equals(checkedButtons[1].getText())) {
            return true;
        } //if

        return false;
    } //checkPair

    private void hideText() {
        checkedButtons[0] = null;
        checkedButtons[1] = null;

        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].getText().equals("") && !goodButtons.contains(i)) {
                buttons[i].setText("");
            } //if
        } //for
    } //hideText

    private boolean checkClicked(Integer amount) {
        int count = 0;

        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].getText().equals("") && !goodButtons.contains(i)) {
                count++;
            } //if
        } //for

        if (count >= amount) {
            return true;
        } //if

        return false;
    } //checkClicked

    private boolean checkStringContains(String[] tableToCheck, String word, Integer amount, Integer index) {
        int count = 0;

        for (int i = 0; i < index; i++) {
            if (tableToCheck[i].toLowerCase().equals(word.toLowerCase())) {
                count++;
            } //if
        } //for

        if (count >= amount) {
            return true;
        } //if

        return false;
    } //checkStringContains
}
