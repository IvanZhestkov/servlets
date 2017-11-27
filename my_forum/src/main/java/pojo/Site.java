package pojo;

/**
 * Сущность сайта.
 * В ней хранятся все конфиги сайта
 */
public class Site {

    Site config = new Site();


    private String siteName;

    private String siteAuthor;

    private String siteDescription;

    private String siteKeywords;

    private Site(String siteName, String siteAuthor, String siteDescription, String siteKeywords) {
        this.siteName = siteName;
        this.siteAuthor = siteAuthor;
        this.siteDescription = siteDescription;
        this.siteKeywords = siteKeywords;
    }

    private Site() {

    }

    public Site getConfig() {
        return config;
    }

    public void setConfig(Site config) {
        this.config = config;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteAuthor() {
        return siteAuthor;
    }

    public void setSiteAuthor(String siteAuthor) {
        this.siteAuthor = siteAuthor;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteKeywords() {
        return siteKeywords;
    }

    public void setSiteKeywords(String siteKeywords) {
        this.siteKeywords = siteKeywords;
    }
}
