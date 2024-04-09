package hello.itemservice.domain.file;

import hello.itemservice.domain.item.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 사용자로부터 업로드 된 파일을 서버에 저장하고 관리를 용이하게 하기 위해
 * 처리하는 파일 처리 기능 담당
 */
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir; // 파일을 저장할 경로

    //파일명을 받아서 해당 파일이 저장된 풀 경로를 반환.
    public String getFullPath(String fileName){
        return fileDir + fileName;
    }

    //여러개 파일 저장
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        //업로드 파일이 계속 생성되면 그것들을 담을 리스트가 필요.
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(multipartFile));
            }
        }

        return storeFileResult;
    }

    //파일 저장
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        //파일이 없으면 null 반환.
        if(multipartFile.isEmpty()){
            return null;
        }

        //파일명을 고유의 파일명으로 변환하여 서버 내부에 저장.
        //1. 파일명 추출
        String originalFileName = multipartFile.getOriginalFilename();

        //2. 고유파일명으로 변환.
        String storeFileName = createStoreFileName(originalFileName);

        //3. 파일 저장.
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        //4. 엔터티 반환.
        return new UploadFile(originalFileName, storeFileName);


    }

    private String createStoreFileName(String originalFilename) {
        //2. 파일명에서 확장자 추출.
        String ext = extractExt(originalFilename);

        //3. 서버 내부에 저장할 파일명으로 변환. UUID 사용
        String uuid = UUID.randomUUID().toString();
        String storeFileName = uuid + "." + ext;

        return storeFileName;
    }

    private String extractExt(String originalFilename) {

        int position = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(position+1); //"."이후의 확장자 추출

        return ext;
    }

    //서버 내부에서 관리할 파일명으로 변환하는 메서드 (uuid 사용)
    //예: 51041c62-86e4-4274-801d-614a7d994edb.png




}
