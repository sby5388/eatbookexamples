package com.eat.chapter14;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.eat.R;
import com.eat.chapter4.ChromeBookmarkAsyncHandler;

public class EditBookmarkDialog extends DialogFragment {


    ChromeBookmarkAsyncHandler mAsyncQueryHandler;
    private HostInterface mHostInterface;
    public EditBookmarkDialog() {
    }

    static EditBookmarkDialog newInstance() {
        return new EditBookmarkDialog();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof HostInterface) {
            mHostInterface = (HostInterface) activity;
            mAsyncQueryHandler = mHostInterface.getChromeBookmarkAsyncHandler();
        } else {
            throw new RuntimeException("activity must implements HostInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_edit_bookmark, container, false);
        final EditText editName = (EditText) v.findViewById(R.id.edit_name);
        final EditText editUrl = (EditText) v.findViewById(R.id.edit_url);
        Button buttonSave = (Button) v.findViewById(R.id.button_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String url = editUrl.getText().toString();
                mAsyncQueryHandler.insert(name, url);
                dismiss();
            }
        });

        return v;
    }

    public interface HostInterface {
        ChromeBookmarkAsyncHandler getChromeBookmarkAsyncHandler();
    }


}
