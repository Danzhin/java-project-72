package hexlet.code.controller;

import org.apache.commons.validator.routines.UrlValidator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

class UrlFormatter  {

    static final UrlValidator URL_VALIDATOR = new UrlValidator();

    static String normalize(String name) throws URISyntaxException, MalformedURLException {
        if (URL_VALIDATOR.isValid(name)) {
            var uri = new URI(name);
            var url = uri.toURL();
            var protocol = url.getProtocol() + "://";
            var host = url.getHost();
            var port = url.getPort() == -1 ? "" : ":" + url.getPort();
            return protocol + host + port;
        }
        throw new IllegalArgumentException();
    }

}
