package com.capgemini.example.servlets;

import com.capgemini.example.models.BlogPostModel;
import com.capgemini.example.services.BlogPostService;
import com.capgemini.example.services.BlogPostServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SlingServlet(resourceTypes = "capgemini/blog", methods = "GET", extensions = "json")
public class GetAllPostsServlet extends SlingSafeMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(GetAllPostsServlet.class);
    private static final String SELECTOR_ALL = "all";
    private static final String SELECTOR_MOST_RECENT = "3mostrecent";
    private static final String SELECTOR_SHOW_DETAILS = "showdetails";
    private static final List<String> usedSelectors = new ArrayList<>(Arrays.asList(SELECTOR_ALL,SELECTOR_MOST_RECENT,SELECTOR_SHOW_DETAILS));
    private static final BlogPostService bps = new BlogPostServiceImpl();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        boolean showAll = false, showMostRecent = false, showDetails = false;
        LOG.info("Reading selectors from request...");
        if(request.getRequestPathInfo() != null && request.getRequestPathInfo().getSelectors().length > 0){
            String[] selectors = request.getRequestPathInfo().getSelectors();
            for (String selector : selectors) {
                if(usedSelectors.contains(selector)){
                    LOG.info("Read {} selector", selector);
                    switch(selector){
                        case SELECTOR_ALL:
                            showAll = true;
                            break;
                        case SELECTOR_MOST_RECENT:
                            showMostRecent = true;
                            break;
                        case SELECTOR_SHOW_DETAILS:
                            showDetails = true;
                            break;
                    }
                } else {
                    LOG.error("Unknown selector used: {}", selector);
                    response.setStatus(500);
                }
            }
        }
        LOG.info("Read selectors from request!");

        if(showAll && showMostRecent){
            LOG.error("Cannot show both all and only most recent posts");
            response.setStatus(500);
        } else if(!showAll && !showMostRecent) {
            LOG.info("No size selector given, defaulting to all");
            showAll = true;
        }

        List<BlogPostModel> posts;
        if(showAll){
            posts = bps.getAllBlogPosts(request);
        } else {
            posts = bps.get3MostRecentPosts(request);
        }

        Gson gson;
        if(showDetails){
            gson = new GsonBuilder().create();
        } else {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
        String output = gson.toJson(posts);

        LOG.info("Writing the output...");
        PrintWriter out = null;
        try {
            response.setContentType("application/json");
            out = response.getWriter();
            out.write(output);
        } catch (IOException e) {
            LOG.error("Could not write to Response output stream: ",e);
        } finally {
            if(out != null){
                out.flush();
                out.close();
            }
        }
        LOG.info("Wrote the output!");
    }
}
