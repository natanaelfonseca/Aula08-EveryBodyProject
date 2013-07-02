package com.everybody.view;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.everybody.R;
import com.everybody.model.Contact;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Natanael Fonseca on 27/06/13.
 */
public class CustomAdapter extends ArrayAdapter<Contact>  {

    private List<Contact> contacts;
    private Context ctx;

    public CustomAdapter(Context context, int textViewResourceId, List<Contact> contacts) {
        super( context, textViewResourceId, contacts );
        this.ctx = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactsViewHolder viewHolder;

        if ( convertView == null) {

            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate( R.layout.main_contactrow, null, false );

            viewHolder = new ContactsViewHolder();
            viewHolder.txName = (TextView) convertView.findViewById(R.id.txtVNome );
            viewHolder.txPhone = (TextView) convertView.findViewById(R.id.txtPhon );
            viewHolder.imgPicture = (ImageView) convertView.findViewById( R.id.imageView );
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ContactsViewHolder) convertView.getTag();
        }

        Contact contact = contacts.get(position);

        if (contact != null) {
            viewHolder.txName.setText(contact.getName().toString());
            viewHolder.txPhone.setText(contact.getPhoneNumer().toString());

            InputStream is = openPhoto(contact.getId());

            if( is != null ){
                viewHolder.imgPicture.setImageDrawable(  LoadImage(is) );
            }else{
                viewHolder.imgPicture.setImageResource( R.drawable.user );
            }

        }

        return convertView;
    }


    static class ContactsViewHolder {
        TextView txName;
        TextView txPhone;
        ImageView imgPicture;
    }

    public InputStream openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContext().getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }


    private Drawable LoadImage(InputStream is){
        try{
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }

    }

}
