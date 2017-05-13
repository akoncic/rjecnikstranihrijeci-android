package com.dekoraktiv.android.rsr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dekoraktiv.android.rsr.adapters.DynamicFragmentPagerAdapter;
import com.dekoraktiv.android.rsr.constants.Extras;
import com.dekoraktiv.android.rsr.fragments.CommonFragment;
import com.dekoraktiv.android.rsr.fragments.DefinitionFragment;
import com.dekoraktiv.android.rsr.models.Dictionary;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private DynamicFragmentPagerAdapter mDynamicFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_layout);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDynamicFragmentPagerAdapter =
                new DynamicFragmentPagerAdapter(getSupportFragmentManager());

        final Dictionary results =
                (Dictionary) getIntent().getSerializableExtra(Extras.INTENT_EXTRA);

        addFragments(results);

        viewPager.setAdapter(mDynamicFragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();

            return true;
        } else if (id == R.id.action_abbreviations) {
            final Intent intent = new Intent(DetailActivity.this, AbbreviationActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragments(Dictionary result) {
        final Dictionary dictionary = result;

        mDynamicFragmentPagerAdapter
                .add(DefinitionFragment.newInstance(),
                        getString(R.string.tab_title_definition));

        if (dictionary.getPhrase() != null) {
            mDynamicFragmentPagerAdapter
                    .add(CommonFragment.newInstance(dictionary.getPhrase()),
                            getString(R.string.tab_title_phrase));
        }

        if (dictionary.getPhraseology() != null) {
            mDynamicFragmentPagerAdapter
                    .add(CommonFragment.newInstance(dictionary.getPhraseology()),
                            getString(R.string.tab_title_phraseology));
        }

        if (dictionary.getOnomastics() != null) {
            mDynamicFragmentPagerAdapter
                    .add(CommonFragment.newInstance(dictionary.getOnomastics()),
                            getString(R.string.tab_title_onomastics));
        }

        if (dictionary.getEtymology() != null) {
            mDynamicFragmentPagerAdapter
                    .add(CommonFragment.newInstance(dictionary.getEtymology()),
                            getString(R.string.tab_title_etymology));
        }
    }
}
