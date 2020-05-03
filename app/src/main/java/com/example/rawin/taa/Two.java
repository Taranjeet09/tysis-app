package com.example.rawin.taa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class Two extends Fragment {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String URL = "https://7b412b70.ngrok.io";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int INTENT_REQUEST_CODE = 100;
    public View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mBtImageSelect;
    private Button mBtImageShow;
    private ProgressBar mProgressBar;
    private String mImageUrl = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.fragment_two);
        final Fragment frag = this;
        //  frag.startActivityForResult(intent, 111);

        // Toolbar toolbar = getView().findViewById(R.id.toolbar);
        //getView().setSupportActionBar(toolbar);

        //initViews(frag);


    /*  mBtImageSelect.setOnClickListener((View view) -> {

            mBtImageShow.setVisibility(View.GONE);

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpeg");
                frag.startActivityForResult(intent, INTENT_REQUEST_CODE);


        });*/

       /* mBtImageShow.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mImageUrl));
            startActivity(intent);

        });*/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == INTENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                try {

                    InputStream is = getActivity().getApplicationContext().getContentResolver().openInputStream(data.getData());

                    uploadImage(getBytes(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }


    private void uploadImage(byte[] imageBytes) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        Call<Response> call = retrofitInterface.uploadImage(body);
        //mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                //mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    Response responseBody = response.body();
                    //mBtImageShow.setVisibility(View.VISIBLE);
                    mImageUrl = URL + responseBody.getPath();
                    Snackbar.make(getView().findViewById(R.id.content), responseBody.getMessage(), Snackbar.LENGTH_SHORT).show();

                } else {

                    ResponseBody errorBody = response.errorBody();

                    Gson gson = new Gson();

                    try {

                        Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                        Snackbar.make(getView().findViewById(R.id.content), errorResponse.getMessage(), Snackbar.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                //mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.content_two, container, false);
        final Fragment frag = this;
        Button mBtImageSelect = view.findViewById(R.id.btn_select_image);
        Button mBtImageShow = view.findViewById(R.id.btn_show_image);
        ProgressBar mProgressBar = view.findViewById(R.id.progress);
        mBtImageSelect.setOnClickListener((View view) -> {

            mBtImageShow.setVisibility(View.GONE);

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpeg");
            frag.startActivityForResult(intent, INTENT_REQUEST_CODE);


        });
        return view;

    }
}
