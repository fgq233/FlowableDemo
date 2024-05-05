package com.fgq.demo.base;

import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 用户、用户组管理
 */
@SpringBootTest
public class IdentityTest {

    @Autowired
    private IdentityService identityService;

    /**
     * 创建用户
     */
    @Test
    void saveUser() {
        User user = identityService.newUser("user_1");
        user.setFirstName("刘");
        user.setLastName("亦非");
        user.setEmail("666@qq.com");
        identityService.saveUser(user);
    }

    /**
     * 删除用户
     */
    @Test
    void deleteUser() {
        identityService.deleteUser("user_1");
    }

    /**
     * 查询用户
     */
    @Test
    void userQuery() {
        List<User> list = identityService.createUserQuery().list();
        list.forEach(user -> System.out.println(user.getId()));
    }

    /**
     * 创建用户组
     */
    @Test
    void saveGroup() {
        Group group = identityService.newGroup("group_1");
        group.setName("开发组");
        identityService.saveGroup(group);
    }

    /**
     * 删除用户组
     */
    @Test
    void deleteUse2r() {
        identityService.deleteGroup("group_1");
    }

    /**
     * 查询用户组
     */
    @Test
    void userQue2ry() {
        // 查询所有
        List<Group> list = identityService.createGroupQuery().list();
        list.forEach(group -> System.out.println(group.getName()));
        // 通过用户组id查询
        Group group = identityService.createGroupQuery().groupId("group_1").singleResult();
        System.out.println(group.getName());
        // 通过用户id查询
        Group group2 = identityService.createGroupQuery().groupMember("1").singleResult();
        System.out.println(group2.getName());
    }


    /**
     * 关联用户、用户组
     */
    @Test
    void createMembership() {
        Group group = identityService.createGroupQuery().groupId("group_1").singleResult();
        List<User> list = identityService.createUserQuery().list();
        for (User user : list) {
            identityService.createMembership(user.getId(),group.getId());
        }
    }

}
