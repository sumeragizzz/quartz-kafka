# quartz-kafka

## Docker

### kafka起動
```shell
# docker run -d --name kafka-server --hostname kafka-server \
docker run -d --name kafka-server --hostname kafka-server -p 9092:9092 \
--network 706dc43797304e3436a313fc3cf3141a80507bd2b4fd30f9e9499528fafe8e50 \
-e KAFKA_CFG_NODE_ID=0 \
-e KAFKA_CFG_PROCESS_ROLES=controller,broker \
-e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
-e KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT \
-e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka-server:9093 \
-e KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER \
bitnami/kafka:latest
```

### コンテナ接続
```shell
docker exec -it kafka-server bash
```
## Kafka

### トピック
```shell
# トピック一覧
kafka-topics.sh --list --bootstrap-server localhost:9092
# トピック作成
kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
# トピック削除
kafka-topics.sh --delete --topic test-topic --bootstrap-server localhost:9092
```

### プロデューサー
```shell
# プロデューサー コンソールで入力
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test-topic
# プロデューサー 引数でメッセージを指定
echo "aaa:100" | kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test-topic
```

### コンシューマー
```shell
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning
```

### 問題

#### ホストからKafkaにアクセスできず
server.propertiesの下記定義を編集することで対応した。  
* listeners
* advertised.listeners

コンテナ上のパスは下記の通り。  
/opt/bitnami/kafka/config/server.properties

bitnami/kafkaのコンテナにはviがないため、ホストで編集してコピーする。  
コピーなら上書き可能。  

```shell
# コンテナからローカルにファイルをコピー
docker cp a9b41902bc21:/opt/bitnami/kafka/config/server.properties .
# ローカルからコンテナにファイルをコピー
docker cp ./server.properties a9b41902bc21:/opt/bitnami/kafka/config/server.properties
```

編集内容は下記の通り。
```text
# before
listeners=PLAINTEXT://:9092,CONTROLLER://:9093
#advertised.listeners=PLAINTEXT://your.host.name:9092

# after
listeners=PLAINTEXT://0.0.0.0:9092,CONTROLLER://:9093
advertised.listeners=PLAINTEXT://localhost:9092
```

コンテナを再起動する。
```shell
# コンテナ再起動
docker restart a9b41902bc21
# Telnetで接続確認
curl -v telnet://localhost:9092
* Host localhost:9092 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:9092...
* Connected to localhost (::1) port 9092
# 上記のようにConnectedと表示されていればOK
```
