## 有序集合
### 使用场景
使用最多的就是排行榜功能 \
设置key并且按照点赞数量为score和文章id作为value的形式构建有序列表 \
当有新的文章增加的时候，则使用zadd添加\
当某篇文章点赞数新增的时候则使用zincrby增加分值\
若是有用户作弊刷赞，则可以使用zrem取消排行中的用户\
此时我们可以使用zrevrange显示点赞量最多的几个用户 \

#### 添加元素

在有序集合内添加元素除了元素本身外还要设置分数 \
zadd key score member \
以下例子向key为name的有序集合内添加了一个分数为1的元素luke
```shell
127.0.0.1:6379> zadd name 1 luke
(integer) 1
```
````
分数只能是数字 \
在3.2版本之后，给zadd命令添加了几个参数 \

nx 元素不存在的情况才能设置成功 \
xx 元素存在的情况下才能设置成功 \
ch 返回此次操作修改的分数和元素的个数 \
incr 增加score \
````
#### zcard key可以计算对应key内元素的的个数

#### zscore key member可以查询ky内元素member的分数

#### 查询排名有两个命令
zrank key member顺序，从小到大 \
zrevrank key member逆序，从大到小
```shell
127.0.0.1:6379> zrank name luke
(integer) 0
127.0.0.1:6379> zrevrank name luke
(integer) 3
```
#### zrem key member可以删除指定的元素

#### 当需要增加元素分数的时候，需要用到zincrby key score member
#### 返回指定排名范围的元素
返回指定排名的元素需要用到以下两个命令 \
zrange key start end [withscore]顺序，从低到高 \
zrevrange key start end [withscore]逆序，从高到低 
```shell
127.0.0.1:6379> zrange stu 0 2
1) "a"
2) "b"
3) "c"
```
#### 返回指定分数范围的元素
zrangebyscore key min max [withscores] [limit offset count]按照分数从高到底 \
zrevrangebyscore key min max [withscores] [limit offset count]按照分数从低到高
```shell
127.0.0.1:6379> zrangebyscore stu 2 4
1) "b"
2) "c"
3) "d"


127.0.0.1:6379> zrangebyscore stu 2 4 withscores
1) "b"
2) "2"
3) "c"
4) "3"
5) "d"
6) "4"

127.0.0.1:6379> zrangebyscore stu 2 4 withscores limit 1 2
1) "c"
2) "3"
3) "d"
4) "4"

127.0.0.1:6379> zrangebyscore stu (2 4
1) "c"
2) "d"
```

#### 返回指定分数范围的元素个数
```shell
127.0.0.1:6379> zcount stu 1 3
(integer) 3
```
####    删除指定排名内的升序元素
```shell
127.0.0.1:6379> zremrangebyrank stu 7 8
(integer) 1
```

####    删除指定分数范围的元素
```shell
127.0.0.1:6379> zremrangebyscore stu (5 +inf
(integer) 2
```
#### 集合操作
```shell
127.0.0.1:6379> zadd user1 1 a 2 b 3 c 4 d
(integer) 4
127.0.0.1:6379> zadd user2 5 b 6 c 7 d 8 e
(integer) 4
```
#### 交集
````shell
求交集的命令是
zinterstore destination numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM|MIN|MAX],这个参数比较多我们一一解释

destination  也就是newkey，求过交集之后的新集合会以这个key保存
numkeys  需要做交集计算键的个数
key [key ...]  需要做交集计算的键
[WEIGHTS weight] 每个键的权重，在交集计算的时候，每个键中的每个元素的分值会乘以这个权重，默认是1
[AGGREGATE SUM|MIN|MAX] 计算过交集后，相同元素的的分值可以按照sum(和)，min(最小值)，max(最大值)做汇总，默认是sum

````
```shell
127.0.0.1:6379> zinterstore  newkey 2 user1 user2
(integer) 3
127.0.0.1:6379> zrange newkey 0 -1 withscores
1) "b"
2) "7"
3) "c"
4) "9"
5) "d"
6) "11"
```

#### 并集
<font color=red>我是红色</font>
```shell
127.0.0.1:6379> zunionstore newkey2 2 user1 user2
(integer) 5
127.0.0.1:6379> zrange newkey2 0 -1 withscores
 1) "a"
 2) "1"
 3) "b"
 4) "7"
 5) "e"
 6) "8"
 7) "c"
 8) "9"
 9) "d"
10) "11"
```