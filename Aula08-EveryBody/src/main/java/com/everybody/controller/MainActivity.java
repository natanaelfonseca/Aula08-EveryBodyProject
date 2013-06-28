package com.everybody.controller;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.everybody.R;
import com.everybody.model.Contact;
import com.everybody.model.DAOContact;
import com.everybody.view.CustomAdapter;

import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemLongClickListener {

    private ActionMode mActionMode;
    private CustomCallBack mActionModeCallback = new CustomCallBack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.getListView().setOnItemLongClickListener(this);

        List<Contact> contacts = new DAOContact( this ).getAll();
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

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = this.startActionMode( mActionModeCallback );
        view.setSelected(true);
        return true;
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
            /*
            switch (item.getItemId()) {
                case R.id.menu_share:
                    shareCurrentItem();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }*/
            return true;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }

    }

}
