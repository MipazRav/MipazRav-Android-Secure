package com.mipazrav.mipazrav;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShiurimList extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ArrayList resultsListItems;

    ListView listView;
    Boolean playAudio;
    Boolean pdfRead;
    private Uri myUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playAudio = getIntent().getBooleanExtra("playAudio", false);
        pdfRead = getIntent().getBooleanExtra("loadPDF", false);
        setTitle(checkTitle());
        setContentView(R.layout.shiurim_list_activity);

        resultsListItems = CurrentListOfShiurim.r;
        listView = (ListView) findViewById(R.id.list);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shiur r = (Shiur) resultsListItems.get(i);
                setTitle(checkTitle());
                if (playAudio) {

                    //  playAudio = false;
                    myUri = Uri.parse("http://www.mipazrav.com/audio/" + r.getDescription().replace(" ", "%20"));//server expects whitespaces as %20

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {

                                case DialogInterface.BUTTON_POSITIVE:

                                    Intent intent = new Intent();
                                    ComponentName comp = new ComponentName("com.android.music", "com.android.music.MediaPlaybackActivity");
                                    intent.setComponent(comp);
                                    intent.setAction(android.content.Intent.ACTION_VIEW);
                                    intent.setDataAndType(myUri, "audio/*");
                                    if (isCallable(intent)) {
                                        startActivity(intent);
                                    } else {
                                        Intent intentB = new Intent(android.content.Intent.ACTION_VIEW);
                                        intentB.setDataAndType(myUri, "audio/*");
                                        startActivity(intentB);
                                    }
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:

                                    if (isStoragePermissionGranted()) {
                                        downloadAudio(myUri);
                                    }


                                    break;

                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(ShiurimList.this);
                    builder.setMessage(r.getName()).setPositiveButton("Stream", dialogClickListener)
                            .setNegativeButton("Download", dialogClickListener).show();


//                    CustomDialogClass cdd = new CustomDialogClass(ShiurimList.this);
//                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    cdd.show();


                } else if (pdfRead) {
                    //   pdfRead=false;
                    Intent j = new Intent(ShiurimList.this, ViewPDF.class);
                    j.putExtra("filename", r.getDescription());
                    startActivity(j);

//                    String filename = r.getDescription();
//                    Intent j = new Intent(Intent.ACTION_VIEW);//will crash if no pdf reader available
//                    j.setData(Uri.parse("http://www.mipazrav.com/audio/"+filename.replace(" ","%20")));
//                    startActivity(j);

                } else {
                    new DownloadFromWebservice(ShiurimList.this, DownloadFromWebservice.QueryType.SHIUR, "subcategoryid=" + r.getRecID());

                }


            }


        });
        CustomerCriteriaAdapter mAdapter = new CustomerCriteriaAdapter(this, R.layout.custom_list_item, resultsListItems);
        listView.setAdapter(mAdapter);

        assert listView != null;


    }

    private boolean isCallable(Intent intent) {
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private String checkTitle() {
        String title = "";
        if (playAudio) {
            title = "Audio Files";
        } else if (pdfRead) {
            title = "PDF files";
        } else {
            title = MainActivity.getBaseTitle();
        }
        return title;
    }

    class CustomerCriteriaAdapter extends ArrayAdapter<Shiur> {

        public CustomerCriteriaAdapter(Context context, int textViewResourceId, List<Shiur> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getViewOptimize(position, convertView, parent);
        }

        public View getViewOptimize(int position, View convertView, ViewGroup parent) {
            Shiur result = getItem(position);

            View row = convertView;
            ViewHolder viewHolder = null;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater)
                        ShiurimList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        private void setTexviewsText(ViewHolder viewHolder, Shiur result) {
            if (result.getName().equals("null")) {
                viewHolder.name.setVisibility(View.GONE);
            } else {
                viewHolder.name.setVisibility(View.VISIBLE);
            }


            viewHolder.name.setText(result.getName());

            Typeface TrajanProRegular = FontCache.get("fonts/TrajanPro-Regular.ttf", ShiurimList.this);
            viewHolder.name.setTypeface(TrajanProRegular);
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


    private void downloadAudio(Uri shiur) {
        DownloadManager.Request request = new DownloadManager.Request(shiur);
        String nameOfFile = URLUtil.guessFileName(shiur.toString(), null, MimeTypeMap.getFileExtensionFromUrl(shiur.toString()));
        Uri destination = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/" + nameOfFile));
        request.setTitle(nameOfFile);
        request.setDescription("Shiur From Mipaz Rav");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(destination);

        DownloadManager manager = (DownloadManager) ShiurimList.this.getSystemService(ShiurimList.this.DOWNLOAD_SERVICE);

        manager.enqueue(request);

//        MediaScannerConnection.scanFile(this,
//                new String[]{nameOfFile}, null,
//                new MediaScannerConnection.OnScanCompletedListener() {
//                    public void onScanCompleted(String path, Uri uri) {
//                        Log.i("ExternalStorage", "Scanned " + path + ":");
//                        Log.i("ExternalStorage", "-> uri=" + uri);
//                    }
//                });

        File file = new File(shiur.getPath());
        Uri uri = Uri.fromFile(file);
        Intent scanFileIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, myUri);
        sendBroadcast(scanFileIntent);

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("WitePerm", "Permission is granted");
                return true;
            } else {

                Log.v("WitePerm", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("WitePerm", "Permission is granted");
            return true;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("writePerm", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            downloadAudio(myUri);
        }
    }
}



