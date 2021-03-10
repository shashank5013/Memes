package com.example.android.memes;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;

public class MemeLoader extends AsyncTaskLoader<String> {

    //URL for API Call
    public String mURL;

    public MemeLoader(Context context,String URL){
        super(context);
        mURL=URL;
    }
    @Nullable
    @Override
    public String loadInBackground() {
        String image_URL=QueryUtilis.imageURL(mURL);
        return image_URL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
