## 基本操作命令
### 读写
```shell
111:0>set name zq #写kv
"OK"
111:0>get name #读
"zq"
111:0>set age 18 EX 10 #设置过期时间（单位是秒），PX（单位变成毫秒）
"OK"
111:0>get age #还剩多久过期
"18"
111:0>TTL age #TTL -2是过期
"1"
111:0>TTL age
"-2"
111:0>get age
null
```
```shell
111:0>get age
null
111:0>set age 18 XX  # XX 如果不存在就没法写入，当有数据时可以写入
null
111:0>get age
null
111:0>set age 0
"OK"
111:0>get age
"0"
111:0>set age 19 XX
"OK"
111:0>get age
"19"
```
### 批量读写
```shell
111:0>mset name1 zq name2 lid
"OK"
111:0>get name1
"zq"
111:0>mget name1 name2
 1)  "zq"
 2)  "lid"
 
111:0>msetnx name11 zq name22 liduan
"1"
111:0>mget name11 name22
 1)  "zq"
 2)  "liduan"
```
### 递增操作
可以用 INCR 命令和 DECR 命令 对这个 age 进行加一和减一操作
```shell
111:0>set age 18
"OK"
111:0>incr age
"19"
111:0>decr age
"18"
```

### 操作部分字符串
首先是 APPEND 命令，它是往一个 Key 里面追加一个字符串,和 Java 字符串的 append() 方法一个意思。
```shell
111:0>set name 1111
"OK"
111:0>get name
"1111"
111:0>append name "eee"
"7"
111:0>get name
"1111eee"
```
GETRANGE 命令是取字符串的一部分值返回，和 Java 里面 substring() 方法的功能一样
```shell
111:0>getrange name 0 3
"1111"
```
SETRANGE 命令，功能是指定一个下标，然后用传进去的字符串，替换这个下标后的内容
```shell
111:0>setrange name 3 "zzz"
"7"
111:0>get name
"111zzze"
```
### 复合操作
ETSET 是 GET 和 SET 两个命令的组合,覆盖原来的value
```shell
111:0>getset name zq
"111zzze"
111:0>get name
"zq"
```
GETEX 命令的意思是，获取 Key 的值，同时给 Key 设置一个过期时间
```shell
111:0>getex name ex 10
"zq"
111:0>ttl name
"1"
111:0>ttl name
null
```
DEL 命令是删除一个 Key，GETDEL 命令 就是先获取一个 Key 的值，同时删除这个 Key 的值。
```shell
111:0>set name 18
"OK"
111:0>get name
"18"
111:0>del name
"1"
111:0>get name
null
```