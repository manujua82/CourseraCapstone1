package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactList {

    private ArrayList<Contact> contacts;
    private String FILENAME = "contacts.sav";

    public ContactList() {  contacts = new ArrayList<Contact>(); }
    
    public void setContacts(ArrayList<Contact> contact_list) { contacts = contact_list; }

    public ArrayList<Contact> getContacts() { return contacts; }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<String>();
        for(int i=0; i< contacts.size()-1; i++){
            usernames.add(contacts.get(i).getUsername());
        }
        return usernames;
    }

    public void addContact(Contact contact){  contacts.add(contact); }

    public void deleteContact(Contact contact){ contacts.remove(contact); }

    public Contact getContact(int index){ return contacts.get(index); }

    public int getSize() { return contacts.size(); }

    public int getIndex(Contact contact) { return  contacts.indexOf(contact); }

    public boolean hasContact(Contact contact){
        if(contacts.indexOf(contact) >= 0 )
            return true;
        return false;
    }

    public Contact getContactByUsername(String username){
        for(Contact c: contacts){
            if(c.getUsername().equals(username))
                return c;
        }
        return null;
    }

    public void loadContact(Context context){
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }
    }

    public void saveItems(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String username){
        if(getContactByUsername(username) != null)
            return true;
        return false;
    }

}
