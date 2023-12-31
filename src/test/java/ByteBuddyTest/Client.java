package ByteBuddyTest;

import com.rpc.client.ClientProxy;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Client {

    public static void main(String[] args) throws Exception {
//        Singable proxy = createByteBuddyDynamicProxy();
//        proxy.sing();
//        proxy.dance();
//        System.out.println(proxy.toString());
        ClientProxy clientProxy = new ClientProxy(null);
        Singable proxy = clientProxy.getProxy(Singable.class);
        proxy.sing();
        proxy.dance();
    }

    private static Singable createByteBuddyDynamicProxy() throws Exception {
        return (Singable) new ByteBuddy().subclass(Object.class)
                .implement(Singable.class)
                .method(ElementMatchers.anyOf(Singable.class.getMethods()))
                .intercept(InvocationHandlerAdapter.of(new SingerInvocationHandler(new Singer())))
                .make()
                .load(Client.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
    }

    public static class SingerInvocationHandler implements InvocationHandler {

        private Object delegate;

        public SingerInvocationHandler(Object delegate) {
            this.delegate = delegate;
        }

        /**
         * 动态代理调用方法
         *
         * @param proxy 生成的代理对象
         * @param method 代理的方法
         * @param args 方法参数
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("bytebuddy proxy before sing ");
            Object ret = method.invoke(delegate, args);
            System.out.println("bytebuddy proxy after sing ");
            return ret;
        }

    }
}

