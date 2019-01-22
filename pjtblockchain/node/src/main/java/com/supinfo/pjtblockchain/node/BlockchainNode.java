package com.supinfo.pjtblockchain.node;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("file:${application_home}/config/node.properties")
public class BlockchainNode {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BlockchainNode.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
