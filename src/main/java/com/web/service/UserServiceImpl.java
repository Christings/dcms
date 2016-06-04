package com.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.core.service.impl.CommonServiceImpl;
import com.web.entity.User;

@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	public User getUserByName(String userName) {
		return this.findUniqueByProperty(User.class, "username", userName);
	}
}
