package hello.itemservice.domain.item;

import lombok.Data;

@Data
public class UploadFile {

    //분리하는 이유: 다른 두 사용자가 같은 파일명을 쓰게 된다면 파일이 덮어질 경우가 생길 수 있기 때문.
    private String uploadFileName; //사용자가 업로드한 파일명 (화면에서 고객에게 보여줄 때 사용.)
    private String storeFileName; //서버 내부에서 관리하는 파일명(UUID를 통해 겹치지 않게 구현)

    public UploadFile(String originalFilename, String storeFileName) {
        this.uploadFileName = originalFilename;
        this.storeFileName = storeFileName;
    }

}
