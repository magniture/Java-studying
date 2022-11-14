# Redis
全程：REmote Directionary Server，即远程字典服务。
## Redis特点
- 速度快：官方给出的读写性能 10w/秒
- 键值对：目前支持的有字符串，哈希，列表，集合，有序集合，位图(Bitmaps)，HyperLogLog。并在3.2版本中加入了有关地理信息定位的功能。
- 功能丰富：发布订阅、事务、Lua脚本、键值的过期时间、PipeLine功能 
- 简单稳定：源码少、单线程模型、不需要依赖操作系统中的类库
- 支持多种语言
- 持久化：Redis同时也支持将缓存的数据持久化到磁盘永久保存，当服务停止重启后，可以读取磁盘数据而重新读取数据支持服务。Redis支持两种持久化方式，RDB和AOF，之后我们会详细研究。
- 主从复制：为了保证Redis的高可用，Redis支持了主从复制功能，可以保存多个数据副本，这也是分布式的基础。
- 高可用和分布式：Redis在2.8版本之后提供了高可用实现Redis Sentinel，实现了单节点的故障发现和故障自动转移，保证了整个服务的高可用。在3.0版本之后，则是提供了分布式的实现Redis Cluster，提供了高可用，并支持读写和容量的扩展

## 使用场景
- 缓存
- 排行榜系统
- 计数器
- 社交
- 消息队列

## 字符串命令
### set key value
```shell
set name tony

# 加上过期时间
setex user 10 tony
```
- ex seconds 设置秒级的过期时间
- px milliseconds 设置毫秒级的过期时间
- nx 键不存在的时候，才可以设置成功
- xx 键存在的时候，才能设置成功

``敲重点，setnx可以用来实现分布式锁呦，当多个线程设置同一个key的时候，由于redis的单线程模型，只有第一个设置的可以成功，其余的将设置失败。``
### get 获取
```shell
get name
```
通过mset key value key value ...可以批量的设置值

```shell
127.0.0.1:6379> mset a 1 b 2 c 3
OK
```
批量获取值的命令就是mget key key ...

```shell
127.0.0.1:6379> mget a b c
1) "1"
2) "2"
3) "3"
127.0.0.1:6379> mget a b c d
1) "1"
2) "2"
3) "3"
4) (nil)
```
当mget后边有不存在的key的时候，将对应的位置将显示nil \
mget和mset命令的优点在于一次网络IO，批量获取或者设置数据。有助于提高业务处理效率，

### 计数
incr key 用于对值的自增操作，有三种返回结果
#### value值不是整数，将报错
```shell
127.0.0.1:6379> set num a
OK
127.0.0.1:6379> incr num
(error) ERR value is not an integer or out of range
```


#### value值是整数，自增返回
```shell
127.0.0.1:6379> set sum 1
OK
127.0.0.1:6379> incr sum
(integer) 2
```

#### value值不存在，将按照值为0自增，返回1
```shell
127.0.0.1:6379> exists number
(integer) 0
127.0.0.1:6379> incr number
(integer) 1
127.0.0.1:6379> get number
"1"
```
## 哈希命令
#### 设置值的命令是hset key field value

#### 获取值的命令则是hget key field
```shell
11:0>hset user:2 name 2
"1"
11:0>hset user:2 name 3
"0"
11:0>hget user:2 name
"3"
```
#### 我们可以删除一个或者多个field，使用的命令是hdel key field ...
```shell
11:0>hdel user:1 name
"1"
```
```shell
11:0>hset user1 name tony
"1"
11:0>hset user1 age 18
"1"
11:0>hget user1 name
"tony"
11:0>hdel user1 name age
"2"
```
``删除命令则是主动的使值失效，这个操作需要注意，不要同时删除过多的值，以免获取数据的时候获取不到，从而使大量的流量打到数据库。其实也很少使用，一般都给值设了过期时间，使其到时自动失效。``

#### 计算field个数
```shell
127.0.0.1:6379> hset user3 name luke
(integer) 1
127.0.0.1:6379> hset user3 age 18
(integer) 1
127.0.0.1:6379> hset user3 sex 1
(integer) 1
127.0.0.1:6379> hlen user3
(integer) 3
```
#### 批量设置是hmset key field value
#### 批量获取是hmget key field
```shell
127.0.0.1:6379> hmset user4 name josh age 19 city xian
OK
127.0.0.1:6379> hmget user4 name age city
1) "josh"
2) "19"
3) "xian"
```
####  判断field是否存在
```shell
127.0.0.1:6379> hexists user1 name
(integer) 0
127.0.0.1:6379> hexists user4 name
(integer) 1
```
#### 获取所有field
#### 获取所有value
#### 获取所有的field-value
```shell
127.0.0.1:6379> hkeys user4
1) "name"
2) "age"
3) "city"

127.0.0.1:6379> hvals user4
1) "josh"
2) "19"
3) "xian"

127.0.0.1:6379> hgetall user4
1) "name"
2) "josh"
3) "age"
4) "19"
5) "city"
6) "xian"
# 这个命令会轮询所有的，当对应的key内field比较多的时候，会很影响性能。

```
#### hincrby key field将field对应value的值加上指定的增量.
#### hincrbyfloat key field这个命令则要求field对应value的值可以解析为float类型


```shell
127.0.0.1:6379> hmset user1 name luke age 18
OK
127.0.0.1:6379> hincrby user1 age 2
(integer) 20
127.0.0.1:6379> hget user1 age
"20"
```
```shell
127.0.0.1:6379> hset user:2 height 175.5
(integer) 1
127.0.0.1:6379> hincrbyfloat user:2 height 3
"178.5"
```
#### 计算value的字符串长度
```shell
127.0.0.1:6379> hmset user1 name luke age 18
OK
127.0.0.1:6379> hstrlen user1 name
(integer) 4
```