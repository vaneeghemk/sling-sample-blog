package com.capgemini.example.models;

import com.google.gson.annotations.Expose;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;

@Model(adaptables = Resource.class)
public class BlogPostModel {
    @Inject @Expose
    public String title;

    @Inject
    public String body;

    @Inject @Expose
    public String author;

    @Inject @Optional
    public String cowrittenBy;

    @Inject @Optional
    public String originallyPublishedOn;

    @Inject @Named("jcr:created") @Expose
    public Calendar createdOn;

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public String getCowrittenBy() {
        return cowrittenBy;
    }

    public String getOriginallyPublishedOn() {
        return originallyPublishedOn;
    }

    public Calendar getCreatedOn() {
        return createdOn;
    }
}
