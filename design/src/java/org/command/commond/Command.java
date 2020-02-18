package org.command.commond;


import org.command.receiver.People;

/**
 * 命令设计模式里面有3个领域：
 * Invoker——调用方，管理一组命令对象
 * Receiver——接收方，根据Command命令做出对应操作
 * Command——命令接口
 * Created by chriszhang on 2018/1/2.
 */
public interface Command {
    void execute(People people);
}
