package com.eat.chapter4;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.provider.BaseColumns;

import com.eat.chapter14.ChromeBookmarkActivity;

/**
 * AsyncQueryHandler with convenience methods for insertion and deletion of bookmarks.
 *
 * @author Administrator  on 2019/5/5.
 */
public class ChromeBookmarkAsyncHandler extends AsyncQueryHandler {

    private static final String _ID = BaseColumns._ID;
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String BOOKMARK = "bookmark";

    public ChromeBookmarkAsyncHandler(ContentResolver cr) {
        super(cr);
    }

    public void insert(String name, String url) {
        ContentValues cv = new ContentValues();
        cv.put(BOOKMARK, 1);
        cv.put(TITLE, name);
        cv.put(URL, url);
        startInsert(0, null, ChromeBookmarkActivity.ChromeBookmark.URI, cv);
    }

    public void delete(String name) {
        String where = TITLE + "=?";
        String[] args = new String[]{name};
        startDelete(0, null, ChromeBookmarkActivity.ChromeBookmark.URI, where, args);
    }
}
