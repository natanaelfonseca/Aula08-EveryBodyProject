package com.everybody.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natanael Fonseca on 27/06/13.
 */
public class DAOContact {

    private static final String[] projection = {ContactsContract.RawContacts.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE,
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Website.URL};

    private static final String selection = null;
    private static final String[] selectionArgs = null;
    private static final String sortOrder = null;

    private Context ctx;

    public DAOContact(Context ctx) {
        this.ctx = ctx;
    }


    public List<Contact> getAll() {

        List<Contact> contacts = new ArrayList<Contact>();

        // Queries the user dictionary and returns results
        Cursor mCursor = ctx.getContentResolver().query(ContactsContract.Data.CONTENT_URI,   // The content URI of the words table
                projection,   // The columns to return for each row
                selection,    // Selection criteria
                selectionArgs,// Selection criteria
                sortOrder);  // The sort order for the returned rows


        if (mCursor != null) {

            while (mCursor.moveToNext()) {
                contacts.add(new Contact(mCursor.getLong(0), mCursor.getString(1), mCursor.getString(2), mCursor.getString(3), mCursor.getString(4), mCursor.getString(5)));
            }

        }

        return contacts;
    }

    public void addContact(Contact contact) {

        ContentValues values = new ContentValues();

        String account_type = null;
        String account_name = null;
        values.put( ContactsContract.RawContacts.ACCOUNT_TYPE, account_type );
        values.put( ContactsContract.RawContacts.ACCOUNT_NAME, account_name );

        Uri rawContactUri = ctx.getContentResolver().insert( ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);

        values.clear();
        values.put( ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put( ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.getName() );
        values.put( ContactsContract.CommonDataKinds.Phone.LABEL, contact.getName()  );
        values.put( ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getPhoneNumer() );

        //values.put(ContactsContract.Data.DATA3, "View Contact");
        ctx.getContentResolver().insert( ContactsContract.Data.CONTENT_URI, values);
   }

    public boolean delete(Contact contact) {

        // Defines selection criteria for the rows you want to delete
        String mSelectionClause = ContactsContract.RawContacts.CONTACT_ID + " == ?";

        String[] mSelectionArgs = {contact.getId().toString()};

        // Defines a variable to contain the number of rows deleted
        int mRowsDeleted = 0;

        // Deletes the words that match the selection criteria
        mRowsDeleted = ctx.getContentResolver().delete(ContactsContract.Data.CONTENT_URI,   // the user dictionary content URI
                mSelectionClause,                    // the column to select on
                mSelectionArgs);

        return mRowsDeleted > 0;
    }

}
