import io.xjar.XEntryFilter;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.filter.XAnyEntryFilter;
import io.xjar.jar.XJarAntEntryFilter;
import io.xjar.key.XKey;

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
        XEntryFilter not =  XKit.not(populateEntryFilter(param.getExcludedResourceList()));
        XKey xKey = XKit.key(password);
        // 执行加密
        XBoot.encrypt(srcJar, destJar, xKey, not);

        return true;
    }


    private static XEntryFilter populateEntryFilter(List<String> excludedFilters) {
        XEntryFilter notFilter = XKit.or();

        for (String item : excludedFilters) {
            notFilter = ((XAnyEntryFilter) notFilter).mix(new XJarAntEntryFilter(item));
        }

        return notFilter;
    }
}
