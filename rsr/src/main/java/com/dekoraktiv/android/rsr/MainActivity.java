package com.dekoraktiv.android.rsr;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.dekoraktiv.android.rsr.constants.Extras;
import com.dekoraktiv.android.rsr.converters.CustomConverterFactory;
import com.dekoraktiv.android.rsr.endpoints.IApiService;
import com.dekoraktiv.android.rsr.models.Stem;
import com.dekoraktiv.android.rsr.models.Suggestion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    @BindView(R.id.auto_complete_text_view)
    AutoCompleteTextView autoCompleteTextView;

    private static final String SUGGESTIONS_ADAPTER_COLUMN_SUGGESTION = "suggestion";

    private SimpleCursorAdapter mSuggestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //noinspection ConstantConditions
        getSupportActionBar().setElevation(0);

        bindSuggestionsAdapter();

        initAutoCompleteTextView();
    }

    private void bindSuggestionsAdapter() {
        mSuggestionsAdapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SUGGESTIONS_ADAPTER_COLUMN_SUGGESTION},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    private void initAutoCompleteTextView() {
        autoCompleteTextView.setAdapter(mSuggestionsAdapter);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 1 && s.toString().length() < 10) {
                    // TODO: Check if the task is already running?

                    suggest(s.toString().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Cursor cursor = (Cursor) autoCompleteTextView.getAdapter().getItem(position);

                // 1st Column = ID; 2nd Column = Suggestions
                final String suggestion = cursor.getString(1);

                load(suggestion);

                autoCompleteTextView.setText(null); // Prevent calling the async. task twice.
            }
        });
        autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    final String suggestion = autoCompleteTextView.getText().toString();

                    suggest(suggestion);
                    //load(suggestion);

                    autoCompleteTextView.setText(null); // Prevent calling the async. task twice.

                    return true;
                }

                return false;
            }
        });
    }

    private void load(String word) {
        showProgressDialog(R.string.dialog_progress_message);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hjp.znanje.hr/")
                .addConverterFactory(CustomConverterFactory.create())
                .build();

        final IApiService apiService = retrofit.create(IApiService.class);

        final Call<Stem> call = apiService.getStemByWord(word);
        call.enqueue(new Callback<Stem>() {
            @Override
            public void onResponse(Call<Stem> call, Response<Stem> response) {
                if (response.isSuccessful()) {
                    final Intent intent;

                    if (response.body().getDictionaries().size() > 1) {
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        intent.putExtra(Extras.INTENT_EXTRA, response.body());
                    } else {
                        intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(Extras.INTENT_EXTRA, response.body().getDictionaries().get(0));
                    }

                    startActivity(intent);

                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<Stem> call, Throwable t) {
                dismissProgressDialog();

                Toast.makeText(getApplicationContext(), R.string.api_message_network,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void suggest(final String query) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hjp.znanje.hr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final IApiService apiService = retrofit.create(IApiService.class);

        final Call<List<Suggestion>> call = apiService.getSuggestions(query);
        call.enqueue(new Callback<List<Suggestion>>() {
            @Override
            public void onResponse(Call<List<Suggestion>> call, Response<List<Suggestion>> response) {
                if (response.isSuccessful()) {
                    final MatrixCursor matrixCursor =
                            new MatrixCursor(new String[]{BaseColumns._ID,
                                    SUGGESTIONS_ADAPTER_COLUMN_SUGGESTION});

                    for (int i = 0; i < response.body().size(); i++) {
                        final String label = response.body().get(i).getLabel();

                        if (label.toLowerCase().startsWith(query.toLowerCase())) {
                            matrixCursor.addRow(new Object[]{i, label});
                        }
                    }

                    mSuggestionsAdapter.changeCursor(matrixCursor);
                }
            }

            @Override
            public void onFailure(Call<List<Suggestion>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.api_message_network,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
