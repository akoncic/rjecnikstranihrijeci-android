package com.dekoraktiv.android.rsr.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dekoraktiv.android.rsr.R;
import com.dekoraktiv.android.rsr.models.Dictionary;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionaryArrayAdapter extends ArrayAdapter<Dictionary> {

    public DictionaryArrayAdapter(Context context, ArrayList<Dictionary> results) {
        super(context, R.layout.list_view_item, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Dictionary result = getItem(position);

        final ViewHolder viewHolder;

        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.list_view_item, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String grammar = result.getGrammar();

        viewHolder.textView.setText(Html.fromHtml(grammar));

        return convertView;
    }

    public static class ViewHolder {

        @BindView(R.id.text_view)
        TextView textView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
