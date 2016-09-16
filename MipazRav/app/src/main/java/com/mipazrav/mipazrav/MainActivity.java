package com.mipazrav.mipazrav;


import android.app.Activity;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;

import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static String baseTitle;
    private static int linkArrayPosition;

    public static String getBaseTitle() {
        return baseTitle;
    }

    public static void setBaseTitle(String title) {
        baseTitle = title;
    }

    public static int getLinkArrayPosition() {
        return linkArrayPosition;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext()));

        TabLayout tabLayout = (TabLayout)
                findViewById(R.id.slidingTabs);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
                MainActivity.hideKeyboard(MainActivity.this);
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });
        tabLayout.setupWithViewPager(viewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void aboutClickHandler(MenuItem item) {
        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_info_outline_black_24dp, null);
        showTTTDialog(d, "ABOUT", getString(R.string.Designers));
    }

    public void gitClickHandler(MenuItem item) {
        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.github, null);
        showTTTDialog(d, "GITHUB", "Fork us on Github!\n\nWe LOVE contributions");
    }

    private void showTTTDialog(Drawable icon, String title, String message) {
        // Create listener for use with dialog window (could also be created anonymously below...
        DialogInterface.OnClickListener dialogOnClickListener =
                createTTTOnClickListener();

        // Create dialog window
        AlertDialog TTTAlertDialog = initDialog(icon, title, message, dialogOnClickListener);

        // Show the dialog window
        TTTAlertDialog.show();

    }

    private AlertDialog initDialog(Drawable icon, String title, String message,
                                   DialogInterface.OnClickListener dialogOnClickListener) {
        AlertDialog TTTAlertDialog;
        TTTAlertDialog = new AlertDialog.Builder(MainActivity.this).create();
        TTTAlertDialog.setTitle(title);
        TTTAlertDialog.setIcon(icon);
        TTTAlertDialog.setMessage(message);
        TTTAlertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,
                "OK", dialogOnClickListener);
        return TTTAlertDialog;
    }

    private DialogInterface.OnClickListener createTTTOnClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing to do
            }
        };
    }


    public void sendEmail(String recipient, String body) {

        BackgroundMail bm = new BackgroundMail(this);
        bm.setGmailUserName("mipazravapp@gmail.com");
        bm.setGmailPassword(localInformation.EMAILPASSWORD);
        bm.setMailTo(recipient);
        bm.setFormSubject("Mipaz Rav App Question");
        bm.setFormBody(body);
        int i = 0;

        bm.send();

    }

    public void sendQuestion(View view) {
//TODO DATA VALIDATION for fields (and parsing) split comment up into some amount of words and insert \n

        String name,
                telephone,
                email,
                comment;

        EditText editTextName = (EditText) findViewById(R.id.fullName);
        name = editTextName.getText().toString();
        EditText editTextTelephone = (EditText) findViewById(R.id.telephone);
        telephone = editTextTelephone.getText().toString();
        EditText editTextEmail = (EditText) findViewById(R.id.email);
        email = editTextEmail.getText().toString();
        EditText editTextComment = (EditText) findViewById(R.id.comment);
        comment = editTextComment.getText().toString();


        String messageForm = "Name: " + name + "\nPhone: " + telephone + "\nEmail Address: " + email
                + "\nComment:" + comment;

        if (emailInputValidation(editTextName, editTextEmail, editTextComment)) {
            sendEmail("joshuaegoldmeier@gmail.com", messageForm);
        } else {
            Toast.makeText(this, "Please fill out the required fields", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean emailInputValidation(EditText name, EditText email, EditText comment) {
        String stringName = name.getText().toString();
        String stringEmail = email.getText().toString();
        String stringComment = comment.getText().toString();
        if (stringName.equals("")) {
            name.setHint("* Name");
            //name.setHintTextColor(ContextCompat.getColor(this, R.color.requiredFields));
        }
        if (stringEmail.equals("")) {
            email.setHint("* Email");
            //email.setHintTextColor(ContextCompat.getColor(this, R.color.requiredFields));
        }
        if (stringComment.equals("")) {
            comment.setHint("* Comment");
            //comment.setHintTextColor(ContextCompat.getColor(this, R.color.requiredFields));
        }
        return (!stringName.equals("") && !stringEmail.equals("") && !stringComment.equals(""));
    }

    public void button_listener(View view) {
        String downloadArg = "";
        String title = "";
        switch (view.getId()) {

            case R.id.Parsha:
                downloadArg = "categoryID=1";
                title = "Parasha";
                break;

            case R.id.Navi:
                downloadArg = "categoryID=2";
                title = "Navi";
                break;

            case R.id.Megillah:
                downloadArg = "categoryID=3";
                title = "Megillah";
                break;

            case R.id.Tehillim:
                downloadArg = "categoryID=17";
                title = "Tehillim";
                break;

            case R.id.Holiday:
                downloadArg = "categoryID=4";
                title = "Holiday";
                break;

            case R.id.Prayer:
                downloadArg = "categoryID=5";
                title = "Prayer";
                break;

            case R.id.Literature:
                downloadArg = "categoryID=6";
                title = "Literature";

                break;

            case R.id.Nutshell:
                downloadArg = "categoryID=7";
                title = "Nutshell";

                break;
            case R.id.Philosophy:
                downloadArg = "categoryID=8";
                title = "Philosophy";

                break;
            case R.id.Halacha:

                downloadArg = "categoryID=9";
                title = "Halacha";

                break;
            case R.id.Derech_Etz_Chaim:

                downloadArg = "categoryID=10";
                title = "Derech Etz Chaim";

                break;
            case R.id.People_in_Tanach:

                downloadArg = "categoryID=15";
                title = "People in Tanach";

                break;
            case R.id.Yahrzeit:
                downloadArg = "categoryId=18";
                title = "Yahrzeit";

                break;
            case R.id.Pirkei_Avot:
                downloadArg = "categoryId=20";
                title = "Pirkei Avot";

                break;
            case R.id.Oral_Torah:
                downloadArg = "categoryId=21";
                title = "Oral Torah";

                break;
            case R.id.Ramchal:
                downloadArg = "categoryId=22";
                title = "Ramchal";

                break;
            case R.id.Kinyan_Torah:
                downloadArg = "categoryId=19";
                title = "Kinyan Torah";

        }
        setBaseTitle(title);
        new DownloadFromWebservice(this, DownloadFromWebservice.QueryType.SHIUR, downloadArg);

    }

    public void links_button_listener(View view) {

        Intent i = new Intent(this, LinksActivity.class);
        int linkArrayPosition = 0;
        switch (view.getId()) {

            case R.id.Torah:
                linkArrayPosition = 0;
                //  i.putExtra("POSITION", 0);
                //  this.startActivity(i);
                break;

            case R.id.Hokma:
                linkArrayPosition = 1;

                break;

            case R.id.General:
                linkArrayPosition = 2;

                break;

            case R.id.Music:
                linkArrayPosition = 3;

                break;

            case R.id.Rabbi_Browser:
                linkArrayPosition = 4;

                break;

            case R.id.Recommended_this_Month:
                linkArrayPosition = 5;

                break;

        }
        setLinkArrayPosition(linkArrayPosition);

        this.startActivity(i);
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void setLinkArrayPosition(int linkArrayPosition) {
        MainActivity.linkArrayPosition = linkArrayPosition;
    }
}