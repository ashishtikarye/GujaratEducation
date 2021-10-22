package com.gujeducation.gujaratedu.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ContactAdapter;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Contacts;
import com.gujeducation.gujaratedu.Model.DaySpecial;

import java.util.ArrayList;

public class GetAllContacts extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    RecyclerView recyclerViewContacts;
    LinearLayoutManager mLayoutManager;
    private ContactAdapter contactAdapter;
    Functions mFunctions;
    Intent intent;
    ArrayList<Contacts> listArrContacts = new ArrayList<Contacts>();

    Cursor phones;
    AppCompatButton btnDone;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mFunctions = new Functions(this);
        btnBack = findViewById(R.id.ivback);
        btnDone = findViewById(R.id.btndone);
        recyclerViewContacts = findViewById(R.id.recyclerview_contacts);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewContacts.setHasFixedSize(true);
        recyclerViewContacts.setLayoutManager(mLayoutManager);

        //selectUsers = new ArrayList<Contacts>();
        showContacts();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(GetAllContacts.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < contactAdapter.imageModelArrayList.size(); i++){
                    if(contactAdapter.imageModelArrayList.get(i).getSelected()) {
                        Toast.makeText(GetAllContacts.this, ""+contactAdapter.imageModelArrayList.get(i).getPhone(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(GetAllContacts.this, ""+contactAdapter.imageModelArrayList.get(i).getName(), Toast.LENGTH_SHORT).show();
                        //tv.setText(tv.getText() + " " + contactAdapter.imageModelArrayList.get(i).getPhone());
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(GetAllContacts.this, HomeScreen.class);
        startActivity(intent);
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            phones = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            LoadContact loadContact = new LoadContact();
            loadContact.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {

                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                  Log.e("id", "<---->------>" + id);
                    Log.e("name", "<---->------>" + name);
                    Log.e("phoneNumber", "<---->------>" + phoneNumber);
                 //   modelArrayList = getModel(false,name,phoneNumber);

                    listArrContacts.add(new Contacts(name, phoneNumber, false));
                   // Contacts selectUser = new Contacts(name, phoneNumber, false);
                   // selectUser.setName(name);
                   // selectUser.setPhone(phoneNumber);
                   // selectUser.setSelected(false);
                   // selectUsers.add(selectUser);


                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // sortContacts();
            int count = listArrContacts.size();
            ArrayList<Contacts> removed = new ArrayList<>();
            ArrayList<Contacts> contacts = new ArrayList<>();
            for (int i = 0; i < listArrContacts.size(); i++) {
                Contacts inviteFriendsProjo = listArrContacts.get(i);

                if (inviteFriendsProjo.getName().matches("\\d+(?:\\.\\d+)?") || inviteFriendsProjo.getName().trim().length() == 0) {
                    removed.add(inviteFriendsProjo);
                    Log.e("Removed Contact", new Gson().toJson(inviteFriendsProjo));
                } else {
                    contacts.add(inviteFriendsProjo);
                }
            }
            contacts.addAll(removed);
            listArrContacts = contacts;
            /*mContactsAdapter = new ContactsAdapter(GetAllContacts.this, selectUsers);
            recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerViewContacts.setAdapter(mContactsAdapter);*/

            contactAdapter = new ContactAdapter(GetAllContacts.this,listArrContacts);///modelArrayList
            recyclerViewContacts.setAdapter(contactAdapter);
            recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        }
    }
}
