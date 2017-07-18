package com.capgemini.example.services;

import com.capgemini.example.models.BlogPostModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BlogPostServiceImpl implements BlogPostService{
    private static final Logger LOG = LoggerFactory.getLogger(BlogPostServiceImpl.class);

    @Override
     public List<BlogPostModel> getAllBlogPosts(SlingHttpServletRequest request){
        LOG.info("Retrieving all blog posts...");
        List<BlogPostModel> posts = new ArrayList<>();

        Resource blogRoot = request.getResource();
        if(blogRoot.hasChildren()){
            for(Resource blogPost : blogRoot.getChildren()){
                BlogPostModel blogPostModel = blogPost.adaptTo(BlogPostModel.class);
                if(blogPostModel != null) posts.add(blogPostModel);
            }
        } else {
            LOG.error("No resources found under {}!", blogRoot.getPath());
        }

        LOG.info("Retrieved all blog posts!");
        return posts;
    }

    @Override
    public List<BlogPostModel> get3MostRecentPosts(SlingHttpServletRequest request) {
        LOG.info("Retrieving all blog posts...");
        List<BlogPostModel> posts = new ArrayList<>();

        Resource blogRoot = request.getResource();
        if(blogRoot.hasChildren()){
            Iterable<Resource> children = blogRoot.getChildren();
            List<Resource> childList = new ArrayList<>();
            for (Resource r : children) {
                childList.add(r);
            }
            childList.sort(Comparator.comparing(r -> r.getValueMap().get("jcr:created", Calendar.class)));
            Collections.reverse(childList);
            for(int i=0; i<3 && i < childList.size(); i++){
                BlogPostModel blogPostModel = childList.get(i).adaptTo(BlogPostModel.class);
                if(blogPostModel != null) posts.add(blogPostModel);
            }
        } else {
            LOG.error("No resources found under {}!", blogRoot.getPath());
        }

        LOG.info("Retrieved all blog posts!");
        return posts;
    }
}
