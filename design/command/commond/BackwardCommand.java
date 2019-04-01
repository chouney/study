package design.command.commond;

import design.command.receiver.People;

/**
 * Created by chriszhang on 2018/1/2.
 */
public class BackwardCommand implements Command{
    @Override
    public void execute(People people) {
        people.backward();
    }

}
