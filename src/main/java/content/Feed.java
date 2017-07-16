package content;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by nicks189 on 7/11/17.
 */
public interface Feed {
    List<String> getKeywords();

    void setKeywords(List<String> kws);

    String getUrl();

    void setUrl(String u) throws MalformedURLException;

    String getFeedTitle();

    String getFeedAuthor();

    String getFeedDescription();

    String getFeedCopyright();

    List<Entry> getEntries();

    List<String> getEntryTitles();

    List<String> getEntryUrls();

    List<Date> getEntryDates();

    List<String> getEntryAuthors();
}
