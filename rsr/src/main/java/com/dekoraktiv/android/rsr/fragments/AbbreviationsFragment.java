package com.dekoraktiv.android.rsr.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dekoraktiv.android.rsr.R;
import com.dekoraktiv.android.rsr.constants.Extras;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AbbreviationsFragment extends Fragment {

    @BindView(R.id.list_view)
    ListView listView;

    public static AbbreviationsFragment newInstance(@NonNull String value) {
        final Bundle bundle = new Bundle();
        bundle.putString(Extras.INTENT_EXTRA, value);

        final AbbreviationsFragment fragment = new AbbreviationsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_abbreviation, container, false);

        ButterKnife.bind(this, view);

        (new AsyncTasks()).execute(getArguments().getString(Extras.INTENT_EXTRA));

        return view;
    }

    private class AsyncTasks extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        private static final String JSON_ASSET = "abbreviations.json";
        private static final String ABBREVIATION_KEY = "key";
        private static final String ABBREVIATION_VALUE = "value";

        private ProgressDialog mProgressDialog = new ProgressDialog(getActivity());

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... strings) {
            final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

            try {
                final InputStream inputStream =
                        getActivity().getAssets().open(JSON_ASSET);

                final BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream));

                final StringBuilder stringBuilder = new StringBuilder();

                String buffer;

                while ((buffer = bufferedReader.readLine()) != null) {
                    stringBuilder.append(buffer);
                }

                final JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                final JSONArray jsonArray = jsonObject.getJSONArray(strings[0]);

                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject getJsonObject = jsonArray.getJSONObject(i);

                    final HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(ABBREVIATION_KEY, getJsonObject.getString(ABBREVIATION_KEY));
                    hashMap.put(ABBREVIATION_VALUE, getJsonObject.getString(ABBREVIATION_VALUE));

                    arrayList.add(hashMap);
                }

                inputStream.close();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.setMessage(getResources().getString(R.string.dialog_progress_message));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> results) {
            super.onPostExecute(results);

            final SimpleAdapter simpleAdapter = new SimpleAdapter(
                    getActivity(),
                    results,
                    android.R.layout.simple_list_item_2,
                    new String[]{ABBREVIATION_KEY, ABBREVIATION_VALUE},
                    new int[]{android.R.id.text1, android.R.id.text2});

            listView.setAdapter(simpleAdapter);

            mProgressDialog.dismiss();
        }
    }
}
