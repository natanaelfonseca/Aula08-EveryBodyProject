package com.everybody.controller;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.everybody.R;
import com.everybody.model.Contact;
import com.everybody.model.DAOContact;
import com.everybody.view.CustomAdapter;
import com.everybody.view.DeleteDialog;

import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemLongClickListener {

    private DAOContact dao;
    private ListView listView;
    private ActionMode mActionMode;
    private CustomCallBack mActionModeCallback = new CustomCallBack();


    private int position;
    private View row;
    private List<Contact> contacts;
    private Contact selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listView = this.getListView();
        listView.setOnItemLongClickListener(this);

        dao = new DAOContact( this );
        contacts = dao.getAll();
        this.setListAdapter( new CustomAdapter( this, R.layout.main_contactrow, contacts ) );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (mActionMode != null) {
            return false;
        }


        position = i;
        row = view;
        selectedContact = contacts.get( i );

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = this.startActionMode( mActionModeCallback );
        view.setSelected(true);
        return true;
    }

    public void doDeleteSelectedContact() {

        dao.delete( selectedContact );
        removeRow( row, position );
    }

    private class CustomCallBack implements ActionMode.Callback{

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_delete:
                    deleteCurrentItem();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }

        }

        public void deleteCurrentItem(){

            DeleteDialog dDialog = new DeleteDialog();
            dDialog.show( getFragmentManager(), "deleteDialog" );
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }

    }

    private void removeRow(final View row, final int position) {
        final int initialHeight = row.getHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                int newHeight = (int) (initialHeight * (1 - interpolatedTime));
                if (newHeight > 0) {
                    row.getLayoutParams().height = newHeight;
                    row.requestLayout();
                }
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                row.getLayoutParams().height = initialHeight;
                row.requestLayout();
                contacts.remove(position);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            }
        });
        animation.setDuration(300);
        row.startAnimation(animation);
    }

}
