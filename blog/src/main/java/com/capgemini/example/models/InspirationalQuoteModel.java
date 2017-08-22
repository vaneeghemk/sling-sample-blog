package com.capgemini.example.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class InspirationalQuoteModel {
    @Inject
    public String quote;

    @Inject
    public String author;

    @Inject
    public String hexColor;

    public String getQuote() {
        return quote;
    }

    public String getHexColor() {
        return hexColor;
    }

    public String getAuthor() {
        return author;
    }
}
