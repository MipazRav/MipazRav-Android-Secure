package com.mipazrav.mipazrav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LinksActivity extends AppCompatActivity {
    private int mPosition;
    String[] Torah = new String[]{"http://www.thelivingtree.org/", "http://maran1.com/", "http://www.halachayomit.co.il/",
            "http://www.steinsaltz.org/", "http://www.mastertorah.com/", "http://www.rabbiwein.com/", "http://kodesh.snunit.k12.il/i/t/t0.htm",
            "http://www.mechon-mamre.org/p/pt/pt0.htm", "http://www.beverlyhillschabad.com/torah-reading/BERESHIT/13Lech-Lecha.htm",
            "http://www.e-daf.com/", "http://kodesh.snunit.k12.il/b/l/l0.htm", "http://www.daat.ac.il/daat/mahshevt/more/shaar-2.htm",
            "http://www.daat.ac.il/daat/mahshevt/mesilat/shaar-2.htm", "http://www.daat.ac.il/daat/mahshevt/mekorot/shaar-2.htm",
            "http://www.daat.ac.il/daat/mahshevt/mahadurot/sefer-daat-2.htm", "http://www.tanach.org/", "http://www.torahcentral.com/"};


    String[] TorahTitles = new String[]{"The Living Tree",
            "Hakham Ovadia (Audio Classes)",
            "Daily Halakha From Hakham Ovadia (Hebrew)",
            "Rabbi Adin Steinsaltz",
            "Master Torah with Rabbi Pogrow!",
            "Rabbi Berel Wein",
            "Online Tanakh",
            "Hebrew English Bible",
            "Online Tikun Kor'im (Ashkenazi)",
            "See the Daf Gemara online!",
            "Online Gemara",
            "Online Moreh Nevuchim",
            "Online Mesilat Yesharim",
            "Online Derekh HaShem",
            "Online Daat Tevunot",
            "Rabbi Menachem Leibtag on Tanach",
            "Torah Central: Sample Other Approaches"};


    String[] Hokhma = new String[]{"http://www.ted.com/", "http://www.markhelprin.com/",
            "https://genographic.nationalgeographic.com/genographic/index.html",
            "http://www.worldsciencefestival.com/video/notes-neurons-full",
            "http://www.annmcmaster.com/", "http://townhall.com/columnists/DennisPrager",
            "http://www.worldwidewords.org/index.htm", "http://www.transmogrifier.org/ch/strips/index",
            "http://sciencenews.org/", "http://www.everythingismiscellaneous.com/"};

    String[] HokhmaTitles = new String[]{
            "TED: Talks on a Vast Array of Thought Provoking Subjects",
            "MARK HELPRIN",
            "The Human genetic development across planet Earth",
            "Better Understand why Torah is called \"Shira\" - wonders of music.",
            "Word's of Wisdom from My Friend Ann Mcmaster",
            "DENNIS PRAGER",
            "WORLD WIDE WORDS",
            "CALVIN AND HOBBES DATABASE",
            "SCIENCE NEWS",
            "Valuelessness in Triumph - Meet the World you Live In"};


    String[] General = new String[]{"http://en.wikipedia.org/wiki/Main_Page",
            "http://dictionary.reference.com/", "http://www.onlineconversion.com/",
            "http://online.wsj.com/public/us"};

    String[] GeneralTitles = new String[]{
            "WIKIPEDIA",
            "DICTIONARY",
            "Convert Any Measurement (Helpful in Halakha)",
            "THE WALL STREET JOURNAL"};


    String[] Music = new String[]{"http://www.pizmonim.org/", "http://www.piyut.org.il/",
            "http://www.maqamworld.com/", "http://www.medi1.com/musique/liste_titres.php?t=&chanteur=MOHAMED_ABDELWAHAB&l=debut"};

    String[] MusicTitles = new String[]{
            "All About Pizmonim",
            "All About Piyutim",
            "All About Arabic Music and Makamat",
            "Listen to songs of Mohamed Abdelwahab"};

    String[] RabbisBrowser = new String[]{"http://www.mipazrav.com/thisisjasonsilva.com"};

    String[] RabbiBrowserTitles = new String[]{"Jason Silva - He could be from our Bet Midrash (almost) :)"};

    String[] RecommendedThisMonth = new String[]{};

    String[] RecommendedThisMonthTitles = new String[]{"Not Supported Yet"};
    ListView listView;
    String[] displayArray;
    String[] linksArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.links_activity);
        int position;
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();//try this with savedInstanceState
            position = bundle.getInt("POSITION");
            Log.d(String.valueOf(position), "this is the position");
        } else {
            savedInstanceState = getIntent().getExtras();
            position = savedInstanceState.getInt("POSITION");
            Log.d(String.valueOf(position), "this is the position");
        }
        mPosition = position;
        checkSourcePosition();
        listView = (ListView) findViewById(R.id.lvItems);


        CustomerCriteriaAdapter mAdapter = new CustomerCriteriaAdapter(this, R.layout.custom_list_item, displayArray);
        listView.setAdapter(mAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendUrlToBrowser(i);
            }
        });
    }

    private void sendUrlToBrowser(int i) {
        String url;
        int position = i;
        url = linksArray[position];
        Log.d(linksArray[position], "this is the linksArray output");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void checkSourcePosition() {
        switch (mPosition) {

            case 0:
                displayArray = TorahTitles;
                linksArray = Torah;
                break;
            case 1:
                displayArray = HokhmaTitles;
                linksArray = Hokhma;
                break;
            case 2:
                displayArray = GeneralTitles;
                linksArray = General;
                break;
            case 3:
                displayArray = MusicTitles;
                linksArray = Music;
                break;
            case 4:
                displayArray = RabbiBrowserTitles;
                linksArray = RabbisBrowser;

                break;
            case 5:
                displayArray = RecommendedThisMonthTitles;
                linksArray = RecommendedThisMonth;
                break;

        }

    }
    class CustomerCriteriaAdapter extends ArrayAdapter<String> {

        public CustomerCriteriaAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getViewOptimize(position, convertView, parent);
        }

        public View getViewOptimize(int position, View convertView, ViewGroup parent) {
            String result = getItem(position);

            View row = convertView;
            ViewHolder viewHolder = null;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater)
                        LinksActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.custom_list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) row.findViewById(R.id.itemName);

                row.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) row.getTag();
            }
            setTexviewsText(viewHolder, result);
            return row;
        }

        private void setTexviewsText(ViewHolder viewHolder, String result) {
            Typeface TrajanProRegular = Typeface.createFromAsset(LinksActivity.this.getAssets(), "fonts/TrajanPro-Regular.ttf");
            viewHolder.name.setTypeface(TrajanProRegular);

            viewHolder.name.setText(result);
        }

    }

    private class ViewHolder {


        public TextView name;


    }

    private boolean notNull(String r) {
        if (r != null) {
            return true;
        }
        return false;
    }
}
