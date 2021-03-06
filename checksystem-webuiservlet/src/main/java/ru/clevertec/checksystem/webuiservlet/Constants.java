package ru.clevertec.checksystem.webuiservlet;

public final class Constants {

    private Constants() {
    }

    public static final class Pages {
        public final static String AUTHENTICATION_PAGE = "/WEB-INF/jsp/page/authentication.jsp";
        public final static String HOME_PAGE = "/WEB-INF/jsp/page/home.jsp";
    }

    public static final class UrlPatterns {
        public final static String HOME_PATTERN = "";
        public final static String UPLOAD_PATTERN = "/upload";
        public final static String DOWNLOAD_PATTERN = "/download";
        public final static String AUTHENTICATION_PATTERN = "/authentication";
        public static final String LOGOUT_PATTERN = "/logout";
        public final static String ROOT_PATTERN = "/";
    }

    public static final class Parameters {
        public static final String ID = "id";
        public static final String TYPE = "type";
        public static final String FORMAT = "format";
        public final static String ANSWER_PARAMETER = "answer";
        public final static String SOURCE_PARAMETER = "source";
        public final static String RETURN_URL = "returnUrl";
    }

    public static final class Attributes {
        public final static String SOURCE_ATTRIBUTE = "source";
        public final static String CHECKS_ATTRIBUTE = "checks";
        public final static String QUESTION_ATTRIBUTE = "question";
        public final static String ANSWER_INCORRECT_ATTRIBUTE = "incorrectAnswer";
    }

    public static final class Sessions {
        public final static String AUTHENTICATED = "authenticated";
        public final static String CHECKS_SESSION = "fileChecks";
    }

    public static final class ServletNames {
        public final static String HOME_SERVLET = "Home";
        public final static String DOWNLOAD_SERVLET = "Download";
        public final static String UPLOAD_SERVLET = "Upload";
        public final static String AUTHENTICATION_SERVLET = "Authentication";
        public final static String LOGOUT_SERVLET = "LogOut";
    }

    public static final class Sources {
        public static final String FILE = "file";
        public static final String DATABASE = "database";
    }

    public static final class Types {
        public static final String PRINT = "print";
        public static final String SERIALIZE = "serialize";
    }

    public static final class Packages extends ru.clevertec.checksystem.core.Constants.Packages {
        public static final class WEB_UI_SERVLET {
            public static final String ROOT = Packages.ROOT + ".webuiservlet";
            public static final String SERVLET = ROOT + ".servlet";
        }
    }
}
