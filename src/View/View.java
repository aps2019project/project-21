package View;

public class View {

    public View (){

    }

    public void printError(ErrorMode error) {
        if(error == null)
            return;
        System.out.println(error.getMessage());
    }
}
