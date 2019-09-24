import java.util.List;

/**
 * @author ewu
 * @date 2019-09-23 下午 5:32
 **/
public class EncryptJarModel {

    /**
     * 类型, Spring Boot Jar, Normal Jar
     */
    private String jarType;

    /**
     *
     */
    private String srcJarName;

    /**
     *
     */
    private String destJarName;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private List<String> excludedResourceList;

    /**
     *
     */
    private Boolean isGenerateKey;

    public String getSrcJarName() {
        return srcJarName;
    }

    public void setSrcJarName(String srcJarName) {
        this.srcJarName = srcJarName;
    }

    public String getDestJarName() {
        return destJarName;
    }

    public void setDestJarName(String destJarName) {
        this.destJarName = destJarName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getExcludedResourceList() {
        return excludedResourceList;
    }

    public void setExcludedResourceList(List<String> excludedResourceList) {
        this.excludedResourceList = excludedResourceList;
    }

    public Boolean getGenerateKey() {
        return isGenerateKey;
    }

    public void setGenerateKey(Boolean generateKey) {
        isGenerateKey = generateKey;
    }


    public String getJarType() {
        return jarType;
    }

    public void setJarType(String jarType) {
        this.jarType = jarType;
    }
}
