package ch.unisg.tapas.auctionhouse.adapter.common;

public class UniformUrlStringFormatter {

    public static String cleanURL(String url) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        if (url.startsWith("http:")) {
            url = url.replaceFirst("http", "https");
        }
        return url;
    }
}
