package com.dekoraktiv.android.rsr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dekoraktiv.android.rsr.R;
import com.dekoraktiv.android.rsr.constants.Extras;
import com.dekoraktiv.android.rsr.models.Dictionary;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefinitionFragment extends Fragment {

    @BindView(R.id.text_view_grammar)
    TextView textViewGrammar;
    @BindView(R.id.text_view_details)
    TextView textViewDetails;

    public static DefinitionFragment newInstance() {
        return new DefinitionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_definition, container, false);

        ButterKnife.bind(this, view);

        final Dictionary results =
                (Dictionary) getActivity().getIntent().getSerializableExtra(Extras.INTENT_EXTRA);

        if (results.getGrammar() != null) {
            textViewGrammar.setText(Html.fromHtml(results.getGrammar()));
        }

        if (results.getDefinition() != null) {
            textViewDetails.setText(Html.fromHtml(results.getDefinition()));
        }

        return view;
    }
}
