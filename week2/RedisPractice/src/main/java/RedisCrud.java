import redis.clients.jedis.Jedis;

public class RedisCrud {
    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {

            // Create
            jedis.set("user:1:name", "Diana");
            jedis.set("user:1:age", "22");

            // Read
            String name = jedis.get("user:1:name");
            String age = jedis.get("user:1:age");
            System.out.println("Read: " + name + ", age " + age);

            // Update
            jedis.set("user:1:age", "23");

            // Delete
            jedis.del("user:1:name", "user:1:age");
        }
    }
}