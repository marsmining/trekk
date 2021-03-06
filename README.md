trekk
=====

Tiny demo webapp with an elementary two entity domain model. Mostly boilerplate code for an
app with [Angular](http://angularjs.org/) client-side code and a Java server-side REST resource,
using [Spring](http://projects.spring.io/spring-framework/). I'm just three days into learning
Angular, so I'm sure the client-side code could be improved.

You can demo the app here: [http://trekk.ockhamsolutions.de](http://trekk.ockhamsolutions.de)

Some areas which could be improved are pre-fetching resources and including in initial HTML.
Client-side form validation. Server-side error handling. Features this does have are proper
caching of resources, with static assets served from a path derived from project version. A
simple Javascript minification script. Basic environment and properties handling for
different environments, eg production, development.

To view the client-side code you can start with [the Javascript](/src/main/webapp/WEB-INF/resources/js/app.js)
and [the HTML](/src/main/webapp/WEB-INF/view/home.jsp). For server-side code you can start
with the [REST customer resource](src/main/java/trekk/web/ctrl/CustomerResource.java)
and the [equivalent of web.xml](src/main/java/trekk/web/config/WebInitializer.java).

_tested only on Win7+ie10 and osx10.9+Chrome_
