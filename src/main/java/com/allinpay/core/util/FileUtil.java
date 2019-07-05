package com.allinpay.core.util;

import com.allinpay.core.constant.enums.BizEnums;
import com.allinpay.core.exception.AllinpayException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
public class FileUtil {
    private FileUtil() {
    }

//    public static void fileUpload(MultipartHttpServletRequest request, String baseDir, Object object) {
//        if (object instanceof PartnerStorage) {
//            PartnerStorage storage = (PartnerStorage) object;
//            storage.setLicense(getFileName(request.getFile("licenseFile"), baseDir + "/license/"));
//            storage.setIdFront(getFileName(request.getFile("legalFront"), baseDir + "/front/"));
//            storage.setIdBack(getFileName(request.getFile("legalBack"), baseDir + "/back/"));
//            storage.setAgreement(getFileName(request.getFile("agreementFile"), baseDir + "/agreement/"));
//        } else if (object instanceof PartnerAudit) {
//            PartnerAudit audit = (PartnerAudit) object;
//            audit.setIdFront(getFileName(request.getFile("legalFront"), baseDir + "/front/"));
//            audit.setIdBack(getFileName(request.getFile("legalBack"), baseDir + "/back/"));
//            audit.setLicense(getFileName(request.getFile("licenseFile"), baseDir + "/license/"));
//            audit.setAgreement(getFileName(request.getFile("agreementFile"), baseDir + "/agreement/"));
//        } else {
//            throw new AllinpayException("实体类类型有误");
//        }
//    }

    /**
     * @Description: 文件上传，重新设置文件名，保证每个文件夹中只有一份机构图片
     * @Param: multipartFile, saveDir
     * @Return: String
     */
    public static String getFileName(MultipartFile multipartFile, String saveDir) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            if (StringUtils.isBlank(fileName)) {
                return "";
            }

            File file = new File(saveDir);

            if (FileUtils.sizeOf(file) > 0) {
                FileUtils.deleteDirectory(file);
            }

            file.mkdirs();

            //图片名称重命名
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            String fullName = UUID.randomUUID().toString().replace("-", "") + fileSuffix;

            multipartFile.transferTo(new File(saveDir + fullName));
            return fullName;
        } catch (Exception e) {
            log.info("文件上传失败！", e);
            throw new AllinpayException(BizEnums.FILE_UPLOAD_EXCEPTION.getCode(), BizEnums.FILE_UPLOAD_EXCEPTION.getMsg());
        }
    }

    /**
     * @Description: 机构编号生成规则:15位机构编号的生成规则 uuid前4 + 分秒7位 + uuid后4
     * @Param:
     * @Return: String
     */
    public static String generatePartnerId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String timeStr = Long.valueOf(System.currentTimeMillis()).toString();
        return (uuid.substring(0, 4) + timeStr.substring(timeStr.length() - 7)
                + uuid.substring(28, 32)).toUpperCase();
    }
}
