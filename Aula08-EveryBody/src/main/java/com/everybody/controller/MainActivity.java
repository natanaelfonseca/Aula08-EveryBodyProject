package com.everybody.controller;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listView = this.getListView();
        listView.setOnItemLongClickListener(this);

        dao = new DAOContact( this );
        List<Contact> contacts = dao.getAll();
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

    public void doPositiveClick() {

        Contact contact = (Contact) listView.getSelectedItem();
        dao.delete( contact );

        this.getListAdapter().notifyAll();
    }

    public void doNegativeClick() {
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

}
