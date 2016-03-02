# myservice

Example project allowing a ring application to run stand-alone in a specific context that matches
the default context the uberwar will run in when tossed into a Java servlet container.  This project
realizes the mutli-route technique described on <a href="http://stackoverflow.com/questions/33135667/can-i-make-lein-ring-server-headless-run-on-a-specific-servlet-context">
Can I make lein ring server-headless run on a specific servlet context?</a> by user <a href="http://stackoverflow.com/users/1860180/ez121sl">ez121sl</a>.

## Usage

To produce the myservice uberwar:

```
$ lein ring uberwar myservice.war
```

To run stand-alone through ```lein ring```:

```
$ lein ring server-headless
```

Curl it at:

```
$ curl -i -X GET http://localhost:3000/myservice/healthz
HTTP/1.1 200 OK
Date: Wed, 02 Mar 2016 20:47:50 GMT
Content-Type: text/html; charset=UTF-8
Content-Length: 3
Server: Jetty(9.2.10.v20150310)
```

## License

Copyright © 2015
