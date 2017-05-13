package com.dekoraktiv.android.rsr.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dekoraktiv.android.rsr.R;
import com.dekoraktiv.android.rsr.constants.Extras;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonFragment extends Fragment {

    @BindView(R.id.text_view)
    TextView textView;

    public static CommonFragment newInstance(@NonNull String value) {
        final Bundle bundle = new Bundle();
        bundle.putString(Extras.INTENT_EXTRA, value);

        final CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_common, container, false);

        ButterKnife.bind(this, view);

        textView.setText(Html.fromHtml(getArguments().getString(Extras.INTENT_EXTRA)));

        return view;
    }
}
