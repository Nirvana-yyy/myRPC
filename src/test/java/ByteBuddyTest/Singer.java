package ByteBuddyTest;

public class Singer implements Singable{
    @Override
    public void sing() {
        System.out.println("zhoujielun sing");
    }

    @Override
    public void dance() {
        System.out.println("kun kun dance");
    }
}
