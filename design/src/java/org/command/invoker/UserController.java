package org.command.invoker;

import org.command.commond.BackwardCommand;
import org.command.commond.Command;
import org.command.commond.ForwardCommand;
import org.command.receiver.Man;
import org.command.receiver.People;
import org.command.receiver.Woman;

/**
 * 调用端，负责存储指令集以及唤醒指令
 * Created by chriszhang on 2018/1/2.
 */
public class UserController {
    People people;
    Command forwardCommand;
    Command backwardCommand;

    public UserController(People people, Command forwardCommand, Command backwardCommand) {
        this.people = people;
        this.forwardCommand = forwardCommand;
        this.backwardCommand = backwardCommand;
    }

    /**
     * 指令集
     * @param fNum
     * @param bNum
     */
    public void firstForwardAndThenBackward(int fNum,int bNum){
        for(int i = 0;i<fNum;i++){
            forwardCommand.execute(people);
        }
        for(int i = 0;i<bNum;i++){
            backwardCommand.execute(people);
        }
    }

    public static void main(String[] args){
        UserController userController = new UserController(new Man(),new ForwardCommand(),new BackwardCommand());
        UserController userController1 = new UserController(new Woman(),new ForwardCommand(),new BackwardCommand());
        userController.firstForwardAndThenBackward(3,1);
        userController1.firstForwardAndThenBackward(1,3);
    }
}
