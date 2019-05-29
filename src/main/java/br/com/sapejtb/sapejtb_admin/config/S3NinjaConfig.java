package br.com.sapejtb.sapejtb_admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;

//@Profile("!prod")
@Profile("s3-ninja")
@Configuration
public class S3NinjaConfig {


	@Value("${sistema.foto-storage.bucket.access}")
	String ACCESS_KEY;
	
	@Value("${sistema.foto-storage.bucket.secret}")
	String SECRET_KEY;
	
	@Value("${sistema.foto-storage.s3ninja}")
	private String NINJA_URL;

	@Bean
	public AmazonS3 s3NinjaClient() {
		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY,
				SECRET_KEY);
		AmazonS3 s3Client = new AmazonS3Client(credentials, new ClientConfiguration());
		s3Client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).disableChunkedEncoding().build());
		s3Client.setEndpoint(NINJA_URL+"/s3");
		return s3Client;
	}
	
	
}
