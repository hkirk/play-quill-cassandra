# Play2 Scala Quill Cassandra Sample App

This Play Framework 2 sample application shows how to access Cassandra from Scala using the
[Quill](https://github.com/getquill/quill) and its asynchronous capabilities.. Pillar is used as evolution

## Usage

### Start Cassandra

At first you should start Cassandra, you can just use the provided Vagrantfile (using the ) for this.
If you haven't already installed Vagrant, head over to the [Vagrant Installation page](http://docs.vagrantup.com/v2/installation/index.html) and get Vagrant.

The Vagrantfile uses the `xenial64` image, which you have to download once:

```sh
$ vagrant init ubuntu/xenial64
```

After that you can boot the vagrant box that will install Cassandra on its first start.

```sh
$ cd vagrant
$ vagrant up
$ vagrant provision
```

### Start the Application

As with every Play 2 application, just use `play run`.

### How to Play

On the first start some data is inserted into Cassandra, in our case we're working with songs.
To retrieve the list of stored songs point your browser to [localhost:9000](http://localhost:9000).

To insert another song you have to POST some json, here's how to do this with curl:

```sh
$ curl -v -X POST -H "Content-Type: application/json" \
  -d '{"title":"The Unforgiven","album":"Black Album","artist":"Metallica"}' \
  http://localhost:9000
```

You should get 201/Created as response status and a `Location` header that tells you which resource was created for the new Song.

### Configuration

The hostname/IP address of the Cassandra cluster is configured in `conf/application.conf`. Right now only a single node is supported, this could easily be extended to take a list of nodes.
The configuration is evaluated in `app/Global.scala`, which creates the `SimpleClient` to connect to Cassandra.

## License

Apache 2.0

## Sample is inspired by
(https://github.com/magro/play2-scala-cassandra-sample)