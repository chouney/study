package org.command.commond;


import org.command.receiver.People;

/**
 * Created by chriszhang on 2018/1/2.
 */
public class ForwardCommand implements Command{
    @Override
    public void execute(People people) {
        people.forward();
    }

}
