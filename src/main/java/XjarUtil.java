import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import io.xjar.XEntryFilter;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.filter.XAnyEntryFilter;
import io.xjar.jar.XJar;
import io.xjar.jar.XJarAntEntryFilter;
import io.xjar.key.XKey;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Xjar工具类
 *
 * @author ewu
 * @date 2019-09-23 下午 5:29
 **/
public class XjarUtil {

    private static final String BOOT_JAR = "Spring Boot Jar";

    /**
     * 加密Jar包
     *
     * @param param 参数
     * @return 返回操作结果
     * @throws Exception
     */
    public static Boolean encryptJar(EncryptJarModel param) throws Exception {
        String jarType = param.getJarType();
        if (StrUtil.isBlank(jarType)) {
            jarType = BOOT_JAR;
        }

        XKey xKey = XKit.key(param.getPassword());

        XEntryFilter not = null;
        if (CollectionUtil.isNotEmpty(param.getExcludedResourceList())) {
            not  = populateEntryFilter(param.getExcludedResourceList());
        }
        if (BOOT_JAR.equalsIgnoreCase(jarType)) {
            // 执行加密
            XBoot.encrypt(param.getSrcJarName(), param.getDestJarName(), xKey, not);
            if (param.getGenerateKey()) {
                String keyFileName = param.getDestJarName().substring(0, param.getDestJarName().lastIndexOf(".")) +".key";
                writeKeyFile(param.getPassword(), keyFileName);
            }
        } else {
            XJar.encrypt(param.getSrcJarName(), param.getDestJarName(), xKey, not);
        }

        return true;
    }


    /**
     * 解密Jar包
     *
     * @param param 参数
     * @return 返回操作结果
     * @throws Exception
     */
    public static Boolean decryptJar(EncryptJarModel param) throws Exception {
        String jarType = param.getJarType();
        if (StrUtil.isBlank(jarType)) {
            jarType = BOOT_JAR;
        }

        XKey xKey = XKit.key(param.getPassword());
        XEntryFilter not = null;
        if (CollectionUtil.isNotEmpty(param.getExcludedResourceList())) {
            not  = populateEntryFilter(param.getExcludedResourceList());
        }

        if (BOOT_JAR.equalsIgnoreCase(jarType)) {
            // 执行加密
            XBoot.decrypt(param.getSrcJarName(), param.getDestJarName(), xKey, not);
        } else {
            // 执行加密
            XJar.decrypt(param.getSrcJarName(), param.getDestJarName(), xKey, not);
        }

        return true;
    }

    public static void writeKeyFile(String password, String keyFilePath) {
         List<String> keyFileList = new ArrayList<String>();
         keyFileList.add("password: " + password);
         keyFileList.add("hold: true");

         FileUtil.writeUtf8Lines(keyFileList, keyFilePath);
    }

    /**
     * 生成过滤条件
     *
     * @param excludedFilters
     * @return
     */
    private static XEntryFilter populateEntryFilter(List<String> excludedFilters) {
        XAnyEntryFilter orFilter = XKit.or();
        for (String item : excludedFilters) {
            orFilter = orFilter.mix(new XJarAntEntryFilter(item));
        }

        return XKit.not(orFilter);
    }

    public static void openFolder(String filePath) {
        try {
            Desktop.getDesktop().open(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
