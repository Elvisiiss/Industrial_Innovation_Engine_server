package com.IIE.Industrial_Innovation_Engine_server.controller;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;
import com.IIE.Industrial_Innovation_Engine_server.mapper.TokenMapper;
import com.IIE.Industrial_Innovation_Engine_server.service.GameService;
import com.IIE.Industrial_Innovation_Engine_server.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/tool")
@CrossOrigin(origins = "*") // 实际应用中应该限制跨域来源
public class ToolController {

    private final ToolService toolService;
    private final GameService gameService;
    private final TokenMapper tokenMapper;


    @Autowired
    public ToolController(ToolService toolService, GameService gameService, TokenMapper tokenMapper) {
        this.toolService = toolService;
        this.gameService = gameService;
        this.tokenMapper = tokenMapper;
    }

    /**
     * 工具 - 获取所有的我的标签
     */
    @GetMapping("/getAllMyTags")
    public BaseResponse getAllMyTags(@RequestHeader String Token) {
        Long id = tokenMapper.getIdByToken(Token);
        if (id == null) {
            return BaseResponse.error("Token错误");
        }
        return toolService.getAllMyTags(id);
    }

    @PostMapping("/uploadGamePicture")
    public BaseResponse uploadGamePicture(@RequestHeader String Token,
                                          @RequestParam("pictureFile") MultipartFile pictureFile) {
        // 1. 验证Token
        Long id = tokenMapper.getIdByToken(Token);
        if (id == null) {
            return BaseResponse.error("Token错误");
        }

        // 2. 验证文件是否为空
        if (pictureFile.isEmpty()) {
            return BaseResponse.error("上传文件不能为空");
        }

        try {
            // 3. 获取项目资源目录的绝对路径
            // 注意：此方式适用于开发环境，生产环境需使用外部目录
            String resourceDir = "src/main/resources/pictures/";
            // 构建绝对路径（基于项目根目录）
            File baseDir = new File(System.getProperty("user.dir"), resourceDir);

            if (!baseDir.exists()) {
                boolean created = baseDir.mkdirs(); // 递归创建目录
                if (!created) {
                    return BaseResponse.error("无法创建目录：" + baseDir.getAbsolutePath());
                }
            }

            // 4. 生成唯一文件名
            String originalFilename = pictureFile.getOriginalFilename();
            String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = System.currentTimeMillis() + "_" + id + fileExt;

            // 5. 保存文件
            File dest = new File(baseDir, fileName); // 安全拼接路径
            pictureFile.transferTo(dest);

            // 6. 构建访问路径（需配合静态资源映射配置）
            String fileUrl = "/pictures/" + fileName;

            return BaseResponse.success("上传成功", fileUrl);

        } catch (IOException e) {
            return BaseResponse.error("上传失败：" + e.getMessage());
        } catch (Exception e) {
            return BaseResponse.error("服务器异常：" + e.getMessage());
        }
    }

}
