package models;

import java.io.Serializable;

public class BattleAction implements Serializable {
    private static final long serialVersionUID = 6529685098267757044L;

    private String method;
    private String[] arguments;

    public BattleAction(String method, String... arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public Class[] getArgsClasses() {
        Class[] classes = new Class[arguments.length];
        for (int i = 0; i < classes.length; i++)
            classes[i] = String.class;
        return classes;
    }
}
