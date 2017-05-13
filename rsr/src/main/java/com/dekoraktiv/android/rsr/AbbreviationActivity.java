package com.dekoraktiv.android.rsr;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dekoraktiv.android.rsr.adapters.DynamicFragmentPagerAdapter;
import com.dekoraktiv.android.rsr.constants.Abbreviations;
import com.dekoraktiv.android.rsr.fragments.AbbreviationsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AbbreviationActivity extends AppCompatActivity {

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

        addFragments();

        viewPager.setAdapter(mDynamicFragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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

    private void addFragments() {
        mDynamicFragmentPagerAdapter
                .add(AbbreviationsFragment.newInstance(Abbreviations.GUIDELINES),
                        getString(R.string.tab_title_guidelines));

        mDynamicFragmentPagerAdapter
                .add(AbbreviationsFragment.newInstance(Abbreviations.LANGUAGES),
                        getString(R.string.tab_title_languages));
    }
}
