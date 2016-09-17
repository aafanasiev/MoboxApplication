package com.aafanasiev.moboxapplication.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafanasiev.moboxapplication.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Aleksandr on 17.09.2016.
 */
public class NewsDialog extends Dialog {

    String dialogText;
    String dialogImage;

    TextView tv;
    ImageView iv;

    public NewsDialog(Context context, String text, String urlImage) {
        super(context);
        dialogText = text;
        dialogImage = urlImage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

        tv = (TextView)findViewById(R.id.dialog_tv);
        iv = (ImageView)findViewById(R.id.dialog_iv);

        tv.setText(dialogText);
        Picasso.with(getContext()).load(dialogImage).into(iv);

    }
}
