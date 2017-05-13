package com.dekoraktiv.android.rsr.converters;

import android.net.UrlQuerySanitizer;
import android.text.TextUtils;

import com.dekoraktiv.android.rsr.models.Dictionary;
import com.dekoraktiv.android.rsr.models.Stem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class CustomResponseBodyConverter implements Converter<ResponseBody, Stem> {

    static final CustomResponseBodyConverter INSTANCE = new CustomResponseBodyConverter();

    private CustomResponseBodyConverter() {
    }

    @Override
    public Stem convert(ResponseBody responseBody) throws IOException {
        final Stem stem = new Stem();

        Document document = Jsoup.parse(responseBody.string());
        //document = new Cleaner(Whitelist.basic()).clean(document); // Sanitize

        final Element body = document.body();

        if (!isUnique(body)) {
            stem.setDictionaries(getDictionaries(body));
            return stem;
        }

        final ArrayList<Dictionary> dictionaries = new ArrayList<>();
        dictionaries.add(getDictionary(body));

        stem.setDictionaries(dictionaries);
        return stem;
    }

    private boolean isUnique(Element body) {
        final Elements elements = body.select(
                "p:containsOwn(Pronađeno je više natuknica koje odgovaraju zadanom uvjetu:)"
        );

        return elements.isEmpty();
    }

    private ArrayList<Dictionary> getDictionaries(Element body) {
        final ArrayList<Dictionary> dictionaries = new ArrayList<>();

        final Elements elements = body.select("p.libersina.md");

        if (elements != null) {
            for (final Element element : elements) {
                final String uri = element.select("a").attr("href");

                final UrlQuerySanitizer urlQuerySanitizer =
                        new UrlQuerySanitizer(uri);

                final String id = urlQuerySanitizer.getValue("id");

                element.select("p > a").remove(); // Remove link.

                final String grammar = element.select("p").html();

                final Dictionary dictionary = new Dictionary();
                dictionary.setId(id);
                dictionary.setGrammar(grammar);

                dictionaries.add(dictionary);
            }
        }

        return dictionaries;
    }

    private Dictionary getDictionary(Element body) {
        final Dictionary dictionary = new Dictionary();

        final String grammar = body.select("p.libersina.md").first().html();

        if (!grammar.isEmpty()) {
            dictionary.setGrammar(grammar);
        }

        final Elements definitions = body.select("#definicija:has(table) tr");

        if (definitions != null) {
            final ArrayList<String> arrayList = new ArrayList<>();

            for (Element definition : definitions) {
                final String html = definition.select("td").last().html();

                arrayList.add(html);
            }

            dictionary.setDefinition(TextUtils.join(System.getProperty("line.separator"),
                    arrayList));
        }

        final String definition = body.select("#definicija:not(:has(table) > div").html();

        if (!definition.isEmpty()) {
            dictionary.setDefinition(definition);
        }

        final String phrase = body.select("#sintagma > div").html();

        if (!phrase.isEmpty()) {
            dictionary.setPhrase(phrase);
        }

        final String phraseology = body.select("#frazeologija > div").html();

        if (!phraseology.isEmpty()) {
            dictionary.setPhraseology(phraseology);
        }

        final String onomastics = body.select("#onomastika > div").html();

        if (!onomastics.isEmpty()) {
            dictionary.setOnomastics(onomastics);
        }

        final String etymology = body.select("#etimologija > div").html();

        if (!etymology.isEmpty()) {
            dictionary.setEtymology(etymology);
        }

        return dictionary;
    }
}
