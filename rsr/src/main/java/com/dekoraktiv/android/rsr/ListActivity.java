package com.dekoraktiv.android.rsr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dekoraktiv.android.rsr.adapters.DictionaryArrayAdapter;
import com.dekoraktiv.android.rsr.constants.Extras;
import com.dekoraktiv.android.rsr.converters.CustomConverterFactory;
import com.dekoraktiv.android.rsr.endpoints.IApiService;
import com.dekoraktiv.android.rsr.models.Dictionary;
import com.dekoraktiv.android.rsr.models.Stem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

        //noinspection ConstantConditions
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Intent intent = this.getIntent();

        final Stem stem = (Stem) intent.getSerializableExtra(Extras.INTENT_EXTRA);

        final DictionaryArrayAdapter dictionaryArrayAdapter =
                new DictionaryArrayAdapter(this, new ArrayList<Dictionary>());
        dictionaryArrayAdapter.addAll(stem.getDictionaries());

        listView.setAdapter(dictionaryArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dictionary result = (Dictionary) parent.getItemAtPosition(position);

                load(result.getId());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void load(String id) {
        showProgressDialog(R.string.dialog_progress_message);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hjp.znanje.hr/")
                .addConverterFactory(CustomConverterFactory.create())
                .build();

        final IApiService apiService = retrofit.create(IApiService.class);

        final Call<Stem> call = apiService.getStemById(id);
        call.enqueue(new Callback<Stem>() {
            @Override
            public void onResponse(Call<Stem> call, Response<Stem> response) {
                if (response.isSuccessful()) {
                    final Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                    intent.putExtra(Extras.INTENT_EXTRA, response.body().getDictionaries().get(0));

                    startActivity(intent);
                }

                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<Stem> call, Throwable t) {
                dismissProgressDialog();

                Toast.makeText(getApplicationContext(), R.string.api_message_network,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
