import io.xjar.XEntryFilter;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.jar.XJarAntEntryFilter;
import io.xjar.key.XKey;

import java.util.Collections;
import java.util.List;

/**
 * Xjar工具类
 *
 * @author ewu
 * @date 2019-09-23 下午 5:29
 **/
public class XjarUtil {

    /**
     * 加密Jar包
     *
     * @param param 参数
     * @return 返回操作结果
     * @throws Exception
     */
    public static Boolean encryptJar(EncryptJarModel param) throws Exception {
        String password = param.getPassword();
        String srcJar = param.getSrcJarName();
        String destJar = param.getDestJarName();


        XEntryFilter not;

        // 组装需要过滤的资源
        if (param.getExcludedResourceList() != null && param.getExcludedResourceList().size() > 0) {
            for (String item: param.getExcludedResourceList()) {
                XKit.not(
                    XKit.or().mix(new XJarAntEntryFilter(item))
                );
            }
        }
        XKey xKey = XKit.key(password);
        XBoot.encrypt(srcJar, destJar, xKey);


        return true;
    }


    private XEntryFilter populateEntryFilter(List<String> excludedFilters) {
        if ()
    }
}
