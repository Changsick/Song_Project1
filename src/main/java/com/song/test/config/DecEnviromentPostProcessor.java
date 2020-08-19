package com.song.test.config;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import com.song.test.util.EncUtil;

@Component
public class DecEnviromentPostProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Properties props = new Properties();
		try {
			props.put("spring.mail.password", convertProperties(environment.getProperty("spring.mail.password")));
		}catch (Exception e) {
			e.printStackTrace();
		}
		environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
	}
	
	protected String convertProperties(String propertyValue) {
		String convertedValue = null;
		
		try {
			if (propertyValue.contains("ENC(")) {
				String val = propertyValue.substring(4, propertyValue.length() - 1);
				convertedValue = new EncUtil().setdec(val);
			} else {
				convertedValue = propertyValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertedValue;
	}

}
