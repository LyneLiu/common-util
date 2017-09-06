import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nn_liu on 2016/8/25.
 */
public class JVMDebuggerTest {

	/**
	 * 测试IDEA的JVM Debugger Memory View插件功能，好玩且很强大！
	 */
    @Test
    public void testJVMDebugger(){
        Map<String,String> map = new HashMap<String, String>();

        map.put("name","lyne");
        map.put("address","SH");

        System.out.println(map);
    }

}
