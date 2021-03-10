package com.example.android.memes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    //URL for API Call
    private final String MEME_URL ="https://meme-api.herokuapp.com/gimme";

    //Imageview in which meme is loaded
    private ImageView mImageView;

    //Loader ID
    private final int LOADER_ID=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView=findViewById(R.id.meme);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    /**
     *Loader calls for  Background network calls
     */
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        MemeLoader memeLoader=new MemeLoader(this,MEME_URL);
        return memeLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Glide.with(this).load(data).into(mImageView);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}