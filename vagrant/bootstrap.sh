apt-get update
apt-get install -y openjdk-8-jdk
apt-get install -y curl
echo "deb http://debian.datastax.com/datastax-ddc 3.6 main" | sudo tee -a /etc/apt/sources.list.d/cassandra.sources.list
curl -L http://debian.datastax.com/debian/repo_key | sudo apt-key add -
apt-get update
apt-get install -y datastax-ddc
sed 's|rpc_address: localhost|rpc_address: 192.168.33.11|g' -i /etc/cassandra/cassandra.yaml
#/etc/init.d/cassandra restart
service cassandra restart
