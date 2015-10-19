# myservice

Example project allowing a ring application to run stand-alone in a specific context that matches
the default context the uberwar will run in when tossed into a Java servlet container.  This project
realizes the mutli-route technique described on http://stackoverflow.com/questions/33135667/can-i-make-lein-ring-server-headless-run-on-a-specific-servlet-context
by user http://stackoverflow.com/users/1860180/ez121sl

## Usage

To produce an uberwar that binds to the context /myservice

```
$ lein ring uberwar myservice.war
```

To run stand-alone through ```lein ring``` an bind to the context /myservice

```
$ lein ring server-headless
```

## License

Copyright © 2015

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
