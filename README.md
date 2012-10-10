## Sample App for Vert.x

This is a sample app using request routing from [Usergrid](http://git.io/usergrid). It demonstrates how you would build a dispatcher and an authentication mechanism (currently just stubbed out) using [vert.x](http://vertx.io)'s built-in http server.

You must have the vert.x binary installed in order for this to work. see <http://vertx.io/install.html> for details. 

Once installed, you can run this sample app by cloning this repository and running the following maven command:
`mvn vertx:run`

(Kudos to @rhart for a [vert.x maven plugin](https://github.com/rhart/vertx-maven-plugin) that just kind of worked.)

Once the sample app is running, the following are some sample URLs to try:

Permutations of org/app parsing from command line (we do a lot of this in [Usergrid](http://git.io/usergrid)):

- <http://localhost:8080/00000000-0000-0000-0000-000000000001/5e0bd86b-b4cf-11e1-8ef1-12313d1cf1ac>
- <http://localhost:8080/myorg/5e0bd86b-b4cf-11e1-8ef1-12313d1cf1ac>
- <http://localhost:8080/00000000-0000-0000-0000-000000000001/myapp>
- <http://localhost:8080/myorg/myapp>

Authentication failure:

- <http://localhost:8080/auth/fail>

What happens when you throw an uncaught exception from a handler:

- <http://localhost:8080/exception>

Poke around the source. There are a lot of notes via in-line and javadoc comments on my thoughts about how things would be built out (some of it specific to Usergrid, but most fairly general).

Overall this is a very intriguing framework with a bunch of potential and given its building blocks (Netty and Hazelcast) could be used in a production scenario right now as there is not very much code. However, after playing with this for a day, here are some things I'm *not* psyched about:

- I don't like anonymous inner classes and not doing such makes it difficult to get at vert.x configuration and context information
- Mocking connection plumbing is very difficult
- Classloader isolation is agressive (exercise: try to acquire a logger from within one of the Handler implementations)

Overall I can't wait to see some performance numbers of this crushing node.js and similar. I've felt for a long time that Java's NIO (when properly done - like Netty) is basically unriviled in it's multi-core performance. It has been my goto when I want to get stuff done correctly. I'm happy to see it become 'cool' again.