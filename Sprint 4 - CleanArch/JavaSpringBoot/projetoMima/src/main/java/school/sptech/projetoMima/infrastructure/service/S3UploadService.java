package school.sptech.projetoMima.infrastructure.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3UploadService {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final S3Client s3Client;

    public S3UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String fileName, byte[] fileBytes) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));

            // Retorna a URL do arquivo no S3
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, fileName);

        } catch (S3Exception e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo para S3: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao fazer upload: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(fileName));
        } catch (S3Exception e) {
            throw new RuntimeException("Erro ao deletar arquivo do S3: " + e.getMessage(), e);
        }
    }
}