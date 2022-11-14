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

## 列表命令
#### rpush key value可以向列表右侧添加元素
#### lpush key value可以向列表左侧添加元素

```shell
127.0.0.1:6379> rpush name a b c d
(integer) 4

127.0.0.1:6379> lrange name 0 -1
1) "a"
2) "b"
3) "c"
4) "d"

127.0.0.1:6379> lpush name:1 a b c d
(integer) 4
127.0.0.1:6379> lrange name:1 0 -1
1) "d"
2) "c"
3) "b"
4) "a"
```
#### linsert key before|after pivot value这个命令可以在元素pivot 前或后插入value值
```shell
127.0.0.1:6379> linsert name before b luke
(integer) 5
127.0.0.1:6379> lrange name 0 -1
1) "a"
2) "luke"
3) "b"
4) "c"
5) "d"
```
#### 我们也可以获取指定下标的元素：lindex key index
#### llen key可以获取列表内共有多少个元素，即列表的长度


```shell
127.0.0.1:6379> rpush name a b c d
(integer) 4
127.0.0.1:6379> lindex name 1
"b"

127.0.0.1:6379> llen name
(integer) 4
```
#### lpop key可以从列表左侧弹出一个元素，并且只能弹出第一个元素。

```shell
127.0.0.1:6379> lpop name
"a"

# 之前的name列表将变为

127.0.0.1:6379> lrange name 0 -1
1) "b"
2) "c"
3) "d"
```
#### lrem key count value会遍历整个列表，删除value值
根据count值的不同会有三种情况

- count > 0 从左到右删除count个value
- count < 0 从右到左删除count绝对值个value
- count = 0 删除所有与value值相同的元素

```shell
127.0.0.1:6379> lpush name c c
(integer) 5
127.0.0.1:6379> lrange name 0 -1
1) "c"
2) "c"
3) "b"
4) "c"
5) "d"
# 然后删除右边两个c
127.0.0.1:6379> lrem name -2 c
(integer) 2
127.0.0.1:6379> lrange name 0 -1
1) "c"
2) "b"
3) "d"

```
#### 按照索引范围修剪列表 ltrim key start end
```shell
127.0.0.1:6379> rpush name a b c d
(integer) 4
127.0.0.1:6379> ltrim name 1 2
OK
127.0.0.1:6379> lrange name 0 -1
1) "b"
2) "c"
```
#### lset key index value,意思是修改index位置的值为value
```shell
127.0.0.1:6379> rpush name a b c d
(integer) 4
127.0.0.1:6379> lset name 1 luke
OK
127.0.0.1:6379> lrange name 0 -1
1) "a"
2) "luke"
3) "c"
4) "d"
```
### 阻塞
阻塞主要是阻塞式弹出，阻塞的意义在于，当没有获取到值的时候，会阻塞等待，无法执行其他命令 \
blpop key timeout从左侧阻塞式弹出 \
brpop key timeout从右侧阻塞式弹出 \
也就是说，客户端会等待timeout的时间获取结果，若是没有值，会等待timeout的时间，若是在这个时间内有值添加进来，则直接返回。若是没有值，则等待timeout的时间，然后返回nil。当timeout的值为0的时候，会一直阻塞等待值的添加。
```shell
127.0.0.1:6379> rpush name a b c d
(integer) 4
127.0.0.1:6379> brpop name 3
1) "name"
2) "d"
```
### 常用场景
```shell
3.1 消息队列
消息队列遵循先入先出
lpush + brpop 则可以实现消息队列
从阻塞弹出就可以看出这个可以实现消息队列，消费者阻塞获取值，当生产者插入元素之后，立即返回并消费消息。
3.2 数据分页
因为列表不仅是有序的，而且还可以获取指定范围内的元素，这就很完美的契合了分页查询。
3.3 栈
lpush + lpop则可以实现栈
栈则和消息队列相反，是先入后出。

```

## 集合命令
### 使用场景
#### sadd key value命令可以向集合中添加元素。
#### srem key member可以移除集合内的指定元素，返回移除成功的个数
#### scard key可以返回key内集合的元素个数，也就是集合的大小
#### sismember key member当返回1的时候，是当前member存在集合中，返回0则是不存在。
#### srandmember key count可以随机返回key内count个元素
#### spop key可以从集合中弹出元素
#### 获取所有的命令是smembers key
#### 取交集的命令是sinter key ...，它可以传入一个或者多个key
#### sunion key ...可以求多个集合的并集
#### sdiff key ...可以求多个集合的差集

在我们求到集合的交集，并集，差集的时候，可以将他们保存到新的key里边
命令对应的是：
sinterstore newkey key ...
sunionstore newkey key ...
sdiffstore newkey key ...
这三个基本就相同了，都是求值然后保存到新的key里边，我们举一个例子看看
```shell
127.0.0.1:6379> sinterstore name3 name1 name2
(integer) 2
127.0.0.1:6379> smembers name3
1) "a"
2) "d"
```
集合比较典型的使用场景是标签，

比如用一个集合保存用户的爱好，求出两个用户的爱好交集就是共同爱好了。

又因为集合内元素不重复，可以使用集合统计网站的访问用户量。可以看出当天有多少活跃用户。