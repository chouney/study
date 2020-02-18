package org.core.service;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.impl.identity.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/5/7
 */
@Service
public class DemoIdentifyService {

    private Logger logger = LoggerFactory.getLogger(DemoIdentifyService.class);

    @Resource
    private UserDetailsManager userDetailsManager;


    /**
     * 假登录，具体登录可以用其他模块，对于activiti，只需要使用UserDetails模块即可
     * @param name
     * @param password
     */
    public void login(String name,String password){
        UserDetails userDetails = userDetailsManager.loadUserByUsername(name);
        if(Objects.isNull(userDetails)){
            logger.error("登录失败，未找到用户");
            return;
        }
        if(!userDetails.getPassword().equals(password)){
            logger.error("登录失败，密码错误");
            return;
        }
        Authentication.setAuthenticatedUserId(name);

        SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.core.Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return userDetails.getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return userDetails.getPassword();
            }

            @Override
            public Object getDetails() {
                return userDetails;
            }

            @Override
            public Object getPrincipal() {
                return userDetails;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return userDetails.getUsername();
            }
        });
    }

    /**
     * 新增用户
     * @param name
     * @param password
     * @param group
     */
    public void addUser(String name,String password,String ... group){
        List<SimpleGrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"));
        auth.addAll(Arrays.stream(group).map(s -> new SimpleGrantedAuthority("GROUP_"+s)).collect(Collectors.toList()));
        userDetailsManager.createUser(new User(name,password,auth));
        logger.info("添加用户成功,name:{},password:{},group:{}", name,password,JSON.toJSONString(group));
    }

    /**
     * 修改用户
     * @param name
     * @param password
     * @param group
     */
    public void update(String name,String password,String ... group){
        List<SimpleGrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"));
        auth.addAll(Arrays.stream(group).map(s -> new SimpleGrantedAuthority("GROUP_"+s)).collect(Collectors.toList()));
        userDetailsManager.updateUser(new User(name,password,auth));
        logger.info("修改用户成功,name:{},password:{},group:{}", name,password,JSON.toJSONString(group));
    }

    /**
     * 删除用户
     * @param name
     */
    public void delete(String name){
        userDetailsManager.deleteUser(name);
        logger.info("删除用户成功,name:{}",name);
    }

    /**
     * 获取用户信息
     * @param name
     */
    public void getUserInfo(String name){
        UserDetails userDetails = userDetailsManager.loadUserByUsername(name);
        logger.info("获取用户信息成功,name:{},password:{},group:{}", userDetails.getUsername(),userDetails.getPassword(),
                JSON.toJSONString(userDetails.getAuthorities()));

    }

    /**
     * 获取登录用户信息
     */
    public void getLoginUserInfo(){
        String name = Authentication.getAuthenticatedUserId();
        getUserInfo(name);
    }




}
