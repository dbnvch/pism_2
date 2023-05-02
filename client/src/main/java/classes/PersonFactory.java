package classes;

public class PersonFactory implements AbstractFactory {

    @Override
    public Person getType(String login){
        if("admin". equalsIgnoreCase(login)){
            return new Admin();
        } else {
            return new User();
        }
    }

}
