package content;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Uses the Rome RSS parser to find and return desired output in a universal way
 * Created by nicks189 on 7/11/17.
 */
public class RomeFeed implements Feed {
    private URL url;
    private List<String> keywords;
    private SyndFeed feed;

    public RomeFeed(String u) throws IOException, FeedException {
        url = stringToUrl(u);
        feed = new SyndFeedInput().build(new XmlReader(url));
        keywords = new ArrayList<String>();
    }

    public RomeFeed(URL u) throws IOException, FeedException {
        url = u;
        feed = new SyndFeedInput().build(new XmlReader(url));
        keywords = new ArrayList<String>();
    }

    public RomeFeed(String u, List<String> kws) throws IOException, FeedException {
        url = stringToUrl(u);
        feed = new SyndFeedInput().build(new XmlReader(url));
        keywords = kws;
    }

    public RomeFeed(URL u, List<String> kws) throws IOException, FeedException {
        url = u;
        feed = new SyndFeedInput().build(new XmlReader(url));
        keywords = kws;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> kws) {
        keywords = kws;
    }

    public String getUrl() { return url.toExternalForm(); }

    public void setUrl(String u) throws MalformedURLException { url = new URL(u); }

    public String getFeedTitle() {
        return feed.getTitle();
    }

    public String getFeedAuthor() {
        return feed.getAuthor();
    }

    public String getFeedDescription() {
        return feed.getDescription();
    }

    public String getFeedCopyright() {
        return feed.getCopyright();
    }

    public List<Entry> getEntries() {
        List<SyndEntry> syndEntries = feed.getEntries();
        List<Entry> entries = new ArrayList<>();
        for(SyndEntry syndEntry : syndEntries) {
            if(validateEntry(syndEntry)) {
                entries.add(new Entry(syndEntry.getTitle(),
                        syndEntry.getAuthor(),
                        syndEntry.getDescription().getValue(),
                        syndEntry.getLink(),
                        syndEntry.getPublishedDate())
                );
            }
        }
        return entries;
    }

    public List<String> getEntryTitles() {
        List<SyndEntry> entries = feed.getEntries();
        List<String> titles = new ArrayList<String>();
        for(SyndEntry entry : entries) {
            if(validateEntry(entry)) {
                titles.add(entry.getTitle());
            }
        }
        return titles;
    }

    public List<String> getEntryUrls() {
        List<SyndEntry> entries = feed.getEntries();
        List<String> urls = new ArrayList<String>();
        for(SyndEntry entry : entries) {
            if(validateEntry(entry)) {
                urls.add(entry.getLink());
            }
        }
        return urls;
    }

    public List<Date> getEntryDates() {
        List<SyndEntry> entries = feed.getEntries();
        List<Date> dates = new ArrayList<Date>();
        for(SyndEntry entry : entries) {
            if(validateEntry(entry)) {
                dates.add(entry.getPublishedDate());
            }
        }
        return dates;
    }

    public List<String> getEntryAuthors() {
        List<SyndEntry> entries = feed.getEntries();
        List<String> auths = new ArrayList<String>();
        for(SyndEntry entry : entries) {
            if(validateEntry(entry)) {
                auths.add(entry.getAuthor());
            }
        }
        return auths;
    }

    protected boolean validateEntry(SyndEntry entry) {
        if(keywords.size() > 0) {
            for(String keyword : keywords) {
                // Check if the entry contains a keyword and if it is less than *roughly* one month old
                if(entry.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        && (entry.getPublishedDate() == null
                        || System.currentTimeMillis() - entry.getPublishedDate().getTime() > 2597244)) {
                    return true;
                }
            }
            return false; // Entry does not contain any keyword or is outdated
        } else {
            return true;  // If keywords is empty than every entry is acceptable
        }
    }

    protected URL stringToUrl(String s) throws MalformedURLException {
        return new URL(s.replace(" ", "%20"));
    }
}
