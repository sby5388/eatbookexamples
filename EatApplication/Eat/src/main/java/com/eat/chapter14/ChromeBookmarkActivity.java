package com.eat.chapter14;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.eat.R;
import com.eat.chapter4.ChromeBookmarkAsyncHandler;

public class ChromeBookmarkActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        EditBookmarkDialog.HostInterface {
    private static final String _ID = BaseColumns._ID;
    private static final String TITLE = "title";
    private static final String URL = "url";

    /**
     * Definition of bookmark access information.
     */
    public interface ChromeBookmark {
        int ID = 1;
        Uri URI = Uri.parse("content://com.android.chrome.browser/bookmarks");
        String[] PROJECTION = {
                _ID,
                TITLE,
                URL
        };
    }


    ListView mListBookmarks;
    SimpleCursorAdapter mAdapter;
    ChromeBookmarkAsyncHandler mChromeBookmarkAsyncHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        mListBookmarks = (ListView) findViewById(R.id.list_bookmarks);

        mChromeBookmarkAsyncHandler = new ChromeBookmarkAsyncHandler(getContentResolver());

        initAdapter();

        getLoaderManager().initLoader(ChromeBookmark.ID, null, this);
    }

    private void initAdapter() {
        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, null,
                new String[]{TITLE},
                new int[]{android.R.id.text1}, 0);
        mListBookmarks.setAdapter(mAdapter);

        mListBookmarks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Adapter adapter = adapterView.getAdapter();
                Cursor c = ((SimpleCursorAdapter) adapterView.getAdapter()).getCursor();
                c.moveToPosition(pos);
                int i = c.getColumnIndex(TITLE);
                mChromeBookmarkAsyncHandler.delete(c.getString(i));
                return true;
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, ChromeBookmark.URI,
                ChromeBookmark.PROJECTION, null, null,
                TITLE + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor newCursor) {
        mAdapter.swapCursor(newCursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }


    public void onAddBookmark(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        // Remove previous dialogs
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        DialogFragment newFragment = EditBookmarkDialog.newInstance();
        newFragment.show(ft, "dialog");
    }

    @Override
    public ChromeBookmarkAsyncHandler getChromeBookmarkAsyncHandler() {
        return mChromeBookmarkAsyncHandler;
    }
}