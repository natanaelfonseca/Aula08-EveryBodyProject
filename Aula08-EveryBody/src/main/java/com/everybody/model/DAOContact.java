package com.everybody.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natanael Fonseca on 27/06/13.
 */
public class DAOContact {

    private static final String[] projection = { ContactsContract.RawContacts.CONTACT_ID,
                                                 ContactsContract.Contacts.DISPLAY_NAME,
                                                 ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE,
                                                 ContactsContract.CommonDataKinds.Email.ADDRESS,
                                                 ContactsContract.CommonDataKinds.Phone.NUMBER,
                                                 ContactsContract.CommonDataKinds.Website.URL };

    private static final String selection = null;
    private static final String[] selectionArgs = null;
    private static final String sortOrder = null;

    private Context ctx;

    public DAOContact(Context ctx) {
        this.ctx = ctx;
    }


    public List<Contact> getAll(){

        List<Contact> contacts = new ArrayList<Contact>();

        // Queries the user dictionary and returns results
        Cursor  mCursor = ctx.getContentResolver().query(   ContactsContract.Data.CONTENT_URI,   // The content URI of the words table
                                                            projection,   // The columns to return for each row
                                                            selection,    // Selection criteria
                                                            selectionArgs,// Selection criteria
                                                            sortOrder );  // The sort order for the returned rows


        if( mCursor != null ){

            while ( mCursor.moveToNext() ){
               contacts.add(  new Contact( mCursor.getLong( 0 ), mCursor.getString( 1 ), mCursor.getString( 2 ), mCursor.getString( 3 ), mCursor.getString( 4 ), mCursor.getString(5) ) );
            }

        }

        return contacts;
    }

    public void addContact(Contact contact){

        /*
        ContentValues values = new ContentValues();
        values.put( ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put( ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values.put( Phon.Phone.NUMBER, "1-800-GOOG-411");
        values.put( ContactsContract.Phone.TYPE, Phone.TYPE_CUSTOM);
        values.put( ContactsContract.Phone.LABEL, "free directory assistance");

        Uri dataUri = ctx.getContentResolver().insert(Data.CONTENT_URI, values);
        */

    }

    public void delete(){

        /*
        // Defines selection criteria for the rows you want to delete
String mSelectionClause = UserDictionary.Words.APP_ID + " LIKE ?";
String[] mSelectionArgs = {"user"};

// Defines a variable to contain the number of rows deleted
int mRowsDeleted = 0;

...

// Deletes the words that match the selection criteria
mRowsDeleted = getContentResolver().delete(
    UserDictionary.Words.CONTENT_URI,   // the user dictionary content URI
    mSelectionClause                    // the column to select on
    mSelectionArgs                      // the value to compare to
);
         */

    }


}
