package com.nt.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {

	public String genPwd() {
		return UUID.randomUUID().toString().replaceAll("_", " ").substring(0, 8);
	}
}
