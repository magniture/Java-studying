## Redis的一些基本操作就不写了
## String操作
常用于缓存对象，分布式锁，限流

```shell
public class Product {
    private String name;
    
    private double price;
    
    private String desc;
    
    // 省略getter/setter方法
    
    @Override
    public String toString() { #序列化
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
```
```shell
public class RedisTest {

    @Test
    public void testCacheProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Product product = new Product(); // 创建Product对象
        product.setName("杯子");
        product.setPrice(100d);
        product.setDesc("这是一个杯子");
        String json = objectMapper.writeValueAsString(product);

        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisAsyncCommands<String, String> asyncCommands = connection.async();
        asyncCommands.set("product", json).get(1, TimeUnit.SECONDS);
        
        // RedisCommands<String, String> syncCommands = connection.sync();
        // syncCommands.set("product", json);

        connection.close();
        redisClient.shutdown();
    }
```
```    
    //再通过反序列化获取对象
    @Test   
    public void testGetProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = asyncCommands.get("product").get(1, TimeUnit.SECONDS);
        Product product = objectMapper.readValue(json, new TypeReference<Product>() {
        });
        System.out.println(product);
    }
}
```


## Hash操作
在项目中会有购物车类似的功能，就可以用到Hash操作
```java
public class CartDao {

    private static final String CART_PREFIX = "cart_";

    public void add(long userId, String productId) throws Exception {
        Boolean result = asyncCommands.hset(CART_PREFIX + userId, //“1”是商品数量
                productId, "1").get(1, TimeUnit.SECONDS);
        if (result) {
            System.out.println("添加购物车成功,productId:" + productId);
        }
    }

    public void remove(long userId, String productId) throws Exception {
        Long result = asyncCommands.hdel(CART_PREFIX + userId,
                productId).get(1, TimeUnit.SECONDS);
        if (result == 1) {
            System.out.println("商品删除成功，productId:" + productId);
        }
    }
    
    public void submitOrder(long userId) throws Exception {
        Map<String, String> cartInfo = asyncCommands.hgetall(CART_PREFIX + userId).get(1, TimeUnit.SECONDS);
        System.out.println("用户:"+userId+", 提交订单:");
        for (Map.Entry<String, String> entry : cartInfo.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
```
```shell
@Test
public void testCartDao() throws Throwable {
    CartDao cartDao = new CartDao();
    cartDao.add(1024, "83694");
    cartDao.add(1024, "1273979");
    cartDao.add(1024, "123323");
    cartDao.submitOrder(1024);
    cartDao.remove(1024, "123323");
}

// 输出：
添加购物车成功,productId:83694
添加购物车成功,productId:1273979
添加购物车成功,productId:123323
用户:1024, 提交订单信息
商品信息
83694:1
1273979:1
123323:1
商品删除成功，productId:123323
用户:1024, 提交订单信息
商品信息
836941
12739791
```
如果是对某个商品进行追加或减少
```shell
public void incr(long userId, String productId) throws Exception {
    Long result = asyncCommands.hincrby(CART_PREFIX + userId,
            productId, 1).get(1, TimeUnit.SECONDS);
    System.out.println("商品数量加1成功，剩余数量为:" + result);
}

public void decr(long userId, String productId) throws Exception {
    String count = asyncCommands.hget(CART_PREFIX + userId,
            productId).get(1, TimeUnit.SECONDS);
    if (Long.valueOf(count) - 1 <= 0) { // 删除商品
        remove(userId, productId);
        return;
    }
    Long result = asyncCommands.hincrby(CART_PREFIX + userId,
            productId, -1).get(1, TimeUnit.SECONDS);
    System.out.println("商品数量减1成功，剩余数量为:" + result);
}
```
```shell
@Test
public void testCartDao() throws Throwable {
    CartDao cartDao = new CartDao();
    cartDao.add(1024, "83694");
    cartDao.add(1024, "1273979");
    cartDao.add(1024, "123323");
    cartDao.submitOrder(1024);
    cartDao.remove(1024, "123323");
    cartDao.submitOrder(1024);

    cartDao.incr(1024, "83694");
    cartDao.decr(1024, "1273979");
}
// 输出
商品数量加1成功，剩余数量为:2
商品删除成功，productId:1273979
用户:1024, 提交订单信息
商品数量
83694:2
```