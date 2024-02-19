package com.carlos.springcloudfunction;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Greetings implements Function<String, String> {
	@Override
	public String apply(String s) {
		return "Hello world from function! your message: " + s;
	}
}
