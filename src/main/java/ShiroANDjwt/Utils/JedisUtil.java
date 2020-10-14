package ShiroANDjwt.Utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtil {
    private static JedisPool jedisPool = null;

    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        JedisUtil.jedisPool = jedisPool;
    }

    public Jedis getJedis() {
        if(jedisPool == null)
            throw new NullPointerException();
        return jedisPool.getResource();
    }

    //public void set(byte[] ){}
}
