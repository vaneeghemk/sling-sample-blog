# A sample sling blog
## Introduction
This is a simple application built on [Apache Sling](https://sling.apache.org/) using a sling model and a servlet.

The application consists of two separate modules:
* `sling-blog`: This is the "core" of the application, consisting of a sling model and a servlet, returning the blog contents as JSON.
* `sling-blog-content`: This consists of some initial content for the repository, as well as the HTL template that uses the sling model.

With your launchpad running and both packages installed, you should be able to examine the following:
* Some content nodes were created:
  * __/content/blog__: a capgemini/blog node
  * __/content/blog/post1__: a capgemini/blogpost node
* When browsing to `localhost:8080/content/blog.json`, you will get an overview of all blogposts that are available under
this path. Using following selectors you can specify a selection:
  * __all__: shows all blogposts. _not to be used together with `3mostrecent` selector_
  * __3mostrecent__: shows the three most recent created blogposts. _not to be used together with `all` selector_
  * __showdetails__: shows additional details for all blogposts.
* When browsing to `localhost:8080/content/blog/post1.html` you will be able to see an article.

## Sling-Blog
### About
This is the "core" of the application, consisting of a sling model and a servlet, returning the blog contents as JSON.

This is a __maven based project__, based on the __sling-bundle-archetype__. Some extra dependencies were added, as well
as the location of the sling models.

### Deployment
Please make sure that __JDK 8 has been installed__! You can then deploy the application by performing a `mvn clean install`
with the `autoInstallBundle` profile selected.

## Sling-Blog-Content
### About
This consists of some initial content for the repository, as well as the HTL template that uses the sling model.

This is a __maven based project__, based on the __sling-initial-content-archetype__. It was configured in such a way, that 
the initial content will _not_ be overwritten if the bundle is uninstalled. This can be changed in the POM.

### Deployment
You can deploy the application by performing a `mvn clean install`
with the `autoInstallBundle` profile selected. The [HTL Maven Plugin](https://sling.apache.org/documentation/development/htl-maven-plugin.html) 
was installed and will block a successful build if there are any warnings or errors.
