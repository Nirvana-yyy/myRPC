package com.rpc.server;

/**
 * @author YJL
 */
public interface RPCserver {

    void start(int port);

    void stop();


//    public static void main(String[] args) throws IOException {
//        UserService userService = new UserServiceImpl();
//
//        ServerSocket serverSocket = new ServerSocket(8899);
//        System.out.println("服务器启动");
//        while(true){
//            Socket socket =serverSocket.accept();
//            new Thread(()->{
//                try {
//                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                    Object o = ois.readObject();
//                    RPCRequest request = (RPCRequest) o;
//                    Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamsType());
//                    Object invoke = method.invoke(userService, request.getParams());
//                    oos.writeObject(RPCResponse.success(invoke));
//                    oos.flush();
//                } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//    }

}
