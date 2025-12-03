package school.sptech.projetoMima.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public S3Client s3Client() {
        System.out.println("=== DEBUG AWS CONFIG ===");
        System.out.println("AWS Region: " + awsRegion);
        System.out.println("✅ Usando DefaultCredentialsProvider (AWS CLI + Variáveis de Ambiente)");
        System.out.println("📋 Ordem de busca de credenciais:");
        System.out.println("   1. Variáveis de ambiente (AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY)");
        System.out.println("   2. Arquivo de credenciais AWS CLI (~/.aws/credentials)");
        System.out.println("   3. Perfil AWS CLI (~/.aws/config)");
        System.out.println("   4. IAM Role (se executando em EC2)");
        System.out.println("=========================");
        
        return S3Client.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}