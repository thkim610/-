package hello.itemservice.domain.file;

import hello.itemservice.domain.item.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 사용자로부터 업로드 된 파일을 서버에 저장하고 관리를 용이하게 하기 위해
 * 처리하는 파일 처리 기능 담당
 */
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir; // 파일을 저장할 경로

    //파일명을 받아서 해당 파일이 저장된 풀 경로를 반환.
    public String getFileDir(String fileName){
        return fileDir + fileName;
    }

    //여러개 파일 저장
    public List<UploadFile> storeFiles(List<MultipartFile> imageFiles){

    }

    //서버 내부에서 관리할 파일명으로 변환하는 메서드 (uuid 사용)
    //예: 51041c62-86e4-4274-801d-614a7d994edb.png




}
