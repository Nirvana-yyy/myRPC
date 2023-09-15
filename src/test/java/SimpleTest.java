import com.extension.spi.extension.ExtensionLoader;
import com.rpc.service.pluginservice.SerializerService;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleTest {

    @Test
    public void test1(){
        System.out.println("FUCK".getBytes(StandardCharsets.UTF_8).length);
    }

    @Test
    public void test2(){
        AtomicInteger atomicInteger = new AtomicInteger(Integer.MAX_VALUE);
//        atomicInteger.incrementAndGet();
        System.out.println(atomicInteger.incrementAndGet());
    }

    @Test
    public void test3(){
        SerializerService serialize = ExtensionLoader.getExtensionLoader(SerializerService.class).getActivate("serialize");


    }
}
