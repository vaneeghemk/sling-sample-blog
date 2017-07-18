package com.capgemini.example.services;

import com.capgemini.example.models.BlogPostModel;
import org.apache.sling.api.SlingHttpServletRequest;

import java.util.List;

public interface BlogPostService {
    List<BlogPostModel> getAllBlogPosts(SlingHttpServletRequest request);
    List<BlogPostModel> get3MostRecentPosts(SlingHttpServletRequest request);
}
