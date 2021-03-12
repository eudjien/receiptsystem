package ru.clevertec.checksystem.webuiservlet;

public final class Constants {

    private Constants() {
    }

    public static final class Pages {
        public final static String AUTHENTICATION_PAGE = "/WEB-INF/jsp/page/login.jsp";
        public final static String HOME_PAGE = "/WEB-INF/jsp/page/home.jsp";
    }

    public static final class UrlPatterns {
        public final static String HOME_PATTERN = "";
        public final static String UPLOAD_PATTERN = "/upload";
        public final static String DOWNLOAD_PATTERN = "/download";
        public final static String MAIL_PATTERN = "/mail";
        public final static String LOGIN_PATTERN = "/login";
        public static final String LOGOUT_PATTERN = "/logout";
        public final static String ROOT_PATTERN = "/";
    }

    public static final class Parameters {
        public static final String ID_PARAMETER = "id";
        public static final String TYPE_PARAMETER = "type";
        public static final String FORMAT_PARAMETER = "format";
        public final static String ANSWER_PARAMETER = "answer";
        public final static String SOURCE_PARAMETER = "source";
        public final static String RETURN_URL_PARAMETER = "returnUrl";
        public final static String SUBJECT_PARAMETER = "subject";
        public final static String ADDRESS_PARAMETER = "address";
    }

    public static final class Attributes {
        public final static String SOURCE_ATTRIBUTE = "source";
        public final static String RECEIPTS_ATTRIBUTE = "receipts";
        public final static String QUESTION_ATTRIBUTE = "question";
        public final static String ANSWER_INCORRECT_ATTRIBUTE = "incorrectAnswer";
    }

    public static final class Sessions {
        public final static String AUTHENTICATION_SESSION = "authentication";
        public final static String RECEIPTS_SESSION = "fileReceipts";
    }

    public static final class ServletNames {
        public final static String HOME_SERVLET = "HomeServlet";
        public final static String DOWNLOAD_SERVLET = "DownloadServlet";
        public final static String MAIL_SERVLET = "MailServlet";
        public final static String UPLOAD_SERVLET = "UploadServlet";
        public final static String LOGIN_SERVLET = "LogInServlet";
        public final static String LOGOUT_SERVLET = "LogOutServlet";
    }

    public static final class FilterNames {
        public final static String AUTHENTICATED_FILTER = "AuthenticationFilter";
        public final static String ANONYMOUS_FILTER = "AnonymousFilter";
    }

    public static final class Sources {
        public static final String FILE = "file";
        public static final String DATABASE = "database";
    }

    public static final class Packages extends ru.clevertec.checksystem.core.Constants.Packages {
        public static final class WEB_UI_SERVLET {
            public static final String ROOT = Packages.ROOT + ".webuiservlet";
            public static final String SERVLET = ROOT + ".servlet";
        }
    }
}
