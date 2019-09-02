package com.allinpay.controller;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.core.constant.CommonConstant;
import com.allinpay.entity.*;
import com.allinpay.service.IOrgQueryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 机构信息、通行记录、用户发卡数据查询，机构冻结、解冻、注销处理
 * Controller
 *
 * @author xuming
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/manage/query")
@Slf4j
public class OrgQueryController {

    @Autowired
    private IOrgQueryService orgquery;


    /**
     * 机构信息查询
     *
     * @param orgque
     * @return
     */
    @GetMapping("/org/getList")
    public ResponseData queryOrgInfo(OrgQueryVo orgque) {
        PageVO<OrgQueryBack> querylist = orgquery.queryorginfo(orgque);
        return ResponseData.success().setData(querylist);
    }

    /**
     * 通行费记录查询
     *
     * @param passm
     * @return
     */
    @GetMapping("/passagemoney/getList")
    public ResponseData queryPassagemoney(PassageMoneyVo passm) {
        PageVO<PassageMoneyBack> querylist = orgquery.queryPassagemoney(passm);
        return ResponseData.success().setData(querylist);
    }

    /**
     * 用户发卡数据查询
     *
     * @param usrh
     * @return
     */
    @GetMapping("/userhairpin/getList")
    public ResponseData queryUserhairpin(UserhairpinVo usrh) {
        PageVO<UserhairpinBack> querylist = orgquery.queryUserhairpin(usrh);
        return ResponseData.success().setData(querylist);
    }

    /**
     * 冻结、解冻、注销机构
     *
     * @param orgque
     * @return
     */
    @PostMapping("/org/blockOrg")
    public ResponseData blockOrg(OrgQueryVo orgque) {
        TEtcSysUser user=(TEtcSysUser)SecurityUtils.getSubject().getPrincipal();
        orgque.setSysUser(user.getUsername());
        orgquery.blockOrg(orgque);
        return ResponseData.success().setData(null);
    }

    @GetMapping("/org/normalOrgList")
    public ResponseData normalOrgList() {
        List<PartnerInfo> partnerInfoList = orgquery.selectByNormalStatus();
        return ResponseData.success().setData(partnerInfoList);
    }

    /**
     *  获取照片
     * @param partnerId 机构编号
     * @param imgid 图片名称
     * @param response
     */
    @GetMapping("/getImg")
    public void getImage(String partnerId, String imgid, HttpServletResponse response) {
//        Image image = null;
//        String path = image.getPath();
        String path = CommonConstant.imagPath;
        if(path == null|| path == ""){
            log.info("照片原始路径为空");
        }
        if (path != null && path != "") {

            path = path + partnerId + "\\" + imgid;
            //设置ContentType的类型
//        String type = "image/" + image.getExName() + ";charset=utf-8";
            FileInputStream inputStream = null;
            OutputStream stream = null;
            try {
                File file = new File(path);
                inputStream = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                inputStream.read(data);
                //setContentType("text/plain; charset=utf-8"); 文本
                response.setContentType("image/*");
                stream = response.getOutputStream();
                stream.write(data);
                stream.flush();
            } catch (Exception e) {
                log.error("读取照片出错:",e);
            } finally {
                try {
                    inputStream.close();
                    stream.close();
                } catch (IOException e) {
                  log.error("关闭读取照片出错:",e);
                }
            }
        }
    }

}
