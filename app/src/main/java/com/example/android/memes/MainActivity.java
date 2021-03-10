package com.example.android.memes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
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
     * Onclick method for  next button
     * @param view
     */
    public void next(View view){
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
    }

    /**
     * Onclick method for share button
     * @param view
     */
    public void share(View view){
        Drawable mDrawable = mImageView.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
        Uri uri = Uri.parse(path);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Image"));
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