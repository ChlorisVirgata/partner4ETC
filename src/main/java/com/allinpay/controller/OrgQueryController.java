package com.allinpay.controller;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.*;
import com.allinpay.service.IOrgQueryService;
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
 * @author xuming
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/query")
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
//        String url=new ServerConfig().getUrl();
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
//        String user = SecurityUtils.getSubject().getPrincipal().toString();
//        orgque.setSysUser(user);
        orgquery.blockOrg(orgque);
        return ResponseData.success().setData(null);
    }

    @GetMapping("/org/normalOrgList")
    public ResponseData normalOrgList() {
        List<PartnerInfo> partnerInfoList = orgquery.selectByNormalStatus();
        return ResponseData.success().setData(partnerInfoList);
    }

    @PostMapping("/getImg")
    public void getImage(OrgQueryVo orgque, HttpServletResponse response) {
        /* 从数据库中获取图片保存的完整路径realPath */
//        Image image = null;
//        String path = image.getPath();
        String path = "D:\\d\\4\\121212.jpg";
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
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
