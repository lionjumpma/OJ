package com.sprint.sandbox.security;


import java.security.Permission;

public class DefaultSecurityManager extends SecurityManager{
    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("security manager is not allowed to check permissions");
        super.checkPermission(perm);
    }
}
