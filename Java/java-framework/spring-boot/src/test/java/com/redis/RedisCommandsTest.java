package com.redis;

import com.google.common.collect.Lists;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.util.SafeEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Jedis操作Lua脚本
 *
 * @author nn_liu
 * @Created 2017-11-23-16:24
 */

public class RedisCommandsTest {

    private Jedis jedis;

    @Before public void init() {
        jedis = new Jedis("127.0.0.1", 6379, 400000);
    }

    @After public void destroy() {
        if (jedis != null){
            jedis.close();
        }
    }


    @Test public void testEvalMultiBulk() {

        Jedis redis = new Jedis("127.0.0.1", 6379, 400000);

        String script = "return { KEYS[1],KEYS[2],ARGV[1],ARGV[2],ARGV[3]}";
        List<String> keys = new ArrayList<>();
        keys.add("key1");
        keys.add("key2");

        List<String> args = new ArrayList<>();
        args.add("first");
        args.add("second");
        args.add("third");

        List<String> response = (List<String>) redis.eval(script, keys, args);

        assertEquals(5, response.size());
        assertEquals("key1", response.get(0));
        assertEquals("key2", response.get(1));
        assertEquals("first", response.get(2));
        assertEquals("second", response.get(3));
        assertEquals("third", response.get(4));

        redis.close();
    }

    @Test public void testEvalMultiBulkWithBinaryJedis() {
        String script = "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2],ARGV[3]}";
        List<byte[]> keys = new ArrayList<>();
        keys.add("key1".getBytes());
        keys.add("key2".getBytes());

        List<byte[]> args = new ArrayList<>();
        args.add("first".getBytes());
        args.add("second".getBytes());
        args.add("third".getBytes());

        BinaryJedis binaryJedis = new BinaryJedis("127.0.0.1", 6379, 400000);
        binaryJedis.connect();

        List<byte[]> responses = (List<byte[]>) binaryJedis.eval(script.getBytes(), keys, args);
        assertEquals(5, responses.size());
        assertEquals("key1", new String(responses.get(0)));
        assertEquals("key2", new String(responses.get(1)));
        assertEquals("first", new String(responses.get(2)));
        assertEquals("second", new String(responses.get(3)));
        assertEquals("third", new String(responses.get(4)));

        binaryJedis.close();
    }

    @Test public void testEvalBulk() {

        Jedis redis = new Jedis("127.0.0.1", 6379, 400000);

        String script = "return KEYS[1]";
        List<String> keys = new ArrayList<String>();
        keys.add("key1");

        List<String> args = new ArrayList<String>();
        args.add("first");

        String response = (String) redis.eval(script, keys, args);

        assertEquals("key1", response);

        redis.close();

    }

    @Test public void testEvalInt() {

        Jedis redis = new Jedis("127.0.0.1", 6379, 400000);

        String script = "return 2";
        List<String> keys = new ArrayList<String>();
        keys.add("key1");

        Long response = (Long) redis.eval(script, keys, new ArrayList<String>());

        assertEquals(new Long(2), response);

        redis.close();

    }

    @Test public void testEvalNestedLists() {

        Jedis redis = new Jedis("127.0.0.1", 6379, 400000);

        String script = "return {{ KEYS[1]} , {2}}";
        List<?> results = (List<?>) redis.eval(script, 1, "key1");

        assertThat((List<String>) results.get(0), listWithItem("key1"));
        assertThat((List<Long>) results.get(1), listWithItem(2L));

        redis.close();
    }

    @Test public void testEvalNoArgs() {

        String script = "return KEYS[1]";
        List<String> keys = new ArrayList<String>();
        keys.add("key1");
        String response = (String) jedis.eval(script, keys, new ArrayList<String>());

        assertEquals("key1", response);

    }

    @Test public void testEvalsha() {

        jedis.set("foo", "bar");
        // script load "return redis.call('get','foo')" 返回的sha校验码：6b1bf486c81ceb7edf3c093f4c48582e38c0e791
        jedis.eval("return redis.call('get','foo')");
        String result = (String) jedis.evalsha("6b1bf486c81ceb7edf3c093f4c48582e38c0e791");

        assertEquals("bar", result);

    }

    @Test(expected = JedisDataException.class) public void testEvalshaShaNotFound() {

        jedis.evalsha("ffffffffffffffffffffffffffffffffffffffff");

    }

    @Test public void scriptFlush() {

        jedis.set("foo", "bar");
        jedis.eval("return redis.call('get','foo')");
        jedis.scriptFlush();
        assertFalse(jedis.scriptExists("6b1bf486c81ceb7edf3c093f4c48582e38c0e791"));

    }

    @Test public void scriptExists() {

        jedis.scriptLoad("return redis.call('get','foo')");
        List<Boolean> exists = jedis.scriptExists("ffffffffffffffffffffffffffffffffffffffff",
                "6b1bf486c81ceb7edf3c093f4c48582e38c0e791");
        assertFalse(exists.get(0));
        assertTrue(exists.get(1));

    }

    @Test public void scriptExistsBinary() {

        jedis.scriptLoad(SafeEncoder.encode("return redis.call('get','foo')"));
        List<Long> exists =
                jedis.scriptExists(SafeEncoder.encode("ffffffffffffffffffffffffffffffffffffffff"),
                        SafeEncoder.encode("6b1bf486c81ceb7edf3c093f4c48582e38c0e791"));
        assertEquals(new Long(0), exists.get(0));
        assertEquals(new Long(1), exists.get(1));

    }

    @Test public void scriptLoad() {

        jedis.scriptLoad("return redis.call('get','foo')");
        assertTrue(jedis.scriptExists("6b1bf486c81ceb7edf3c093f4c48582e38c0e791"));

    }

    @Test public void scriptLoadBinary() {

        jedis.scriptLoad(SafeEncoder.encode("return redis.call('get','foo')"));
        Long exists =
                jedis.scriptExists(SafeEncoder.encode("6b1bf486c81ceb7edf3c093f4c48582e38c0e791"));
        assertEquals((Long) 1L, exists);

    }

    @Test public void scriptKill() {

        try {

            jedis.scriptKill();

        } catch (JedisDataException e) {

            assertTrue(e.getMessage().contains("No scripts in execution right now."));

        }

    }

    @Test public void scriptEvalReturnNullValues() {

        jedis.del("key1");
        jedis.del("key2");

        String script = "return { redis.call('hget', KEYS[1], ARGV[1]), redis.call('hget', KEYS[2], ARGV[2])}";
        List<String> results = (List<String>) jedis.eval(script, 2, "key1", "key2", "1", "2");

        assertEquals(2,results.size());

        assertNull(results.get(0));

        assertNull(results.get(1));

    }

    @Test public void scriptEvalShaReturnNullValues() {

        jedis.del("key1");
        jedis.del("key2");

        String script = "return { redis.call('hget', KEYS[1], ARGV[1]), redis.call('hget', KEYS[2], ARGV[2]) }"; String sha=jedis.scriptLoad(script);
        List<String> results=(List<String>)jedis.evalsha(sha,2,"key1","key2","1","2");
        assertEquals(2,results.size());
        assertNull(results.get(0));
        assertNull(results.get(1));

    }

    @Test public void scriptExistsWithBrokenConnection(){

        Jedis deadClient=new Jedis(jedis.getClient().getHost(),jedis.getClient().getPort());

        deadClient.clientSetname("DEAD");

        ClientKillerUtil.killClient(deadClient,"DEAD");

        // sure, script doesn't exist, but it's just for checking connection
        try{

            deadClient.scriptExists("abcdefg");

        }catch(JedisConnectionException e){

        // ignore it

        }

        assertEquals(true,deadClient.getClient().isBroken());

        deadClient.close();

    }

    private<T> Matcher<Iterable<? super T>> listWithItem(T expected){

        return CoreMatchers.<T> hasItem(equalTo(expected));

    }

}
